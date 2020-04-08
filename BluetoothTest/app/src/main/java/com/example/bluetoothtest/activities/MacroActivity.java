package com.example.bluetoothtest.activities;

import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.controllerData.Command;
import com.example.bluetoothtest.controllerData.Delay;
import com.example.bluetoothtest.controllerData.KeyStroke;
import com.example.bluetoothtest.controllerData.Macro;
import com.example.bluetoothtest.controllerData.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;

public class MacroActivity extends AppCompatActivity {
    private EditText editText;
    private String oldName;
    private ImageButton rename_btn;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;

    private Vector<String> macros;
    private HashMap<String, String> fileName = new HashMap<>();

    private TextView param_title;
    private EditText param;
    private Button btn1, btn2, btn3, btn4;

    private Command command;

    private View decorView;

    private PopupWindow popupWindow = new PopupWindow(); //so we can access it easily

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macro);

        editText = findViewById(R.id.editText);
        rename_btn = findViewById(R.id.rename_btn);
        recyclerView = findViewById(R.id.recycler_view);

        param_title = findViewById(R.id.param_title);
        param = findViewById(R.id.param);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        File path = new File(getFilesDir() + "/macros/");
        macros = new Vector<>();
        //Log.d("ZZZ",path.toString());

        for (File i : path.listFiles()) {
            //Log.d("ZZZ","File found: "+i.getName());
            try {
                Scanner scanner = new Scanner(i);
                String temp = scanner.nextLine();
                fileName.put(temp, i.toString());
                macros.add(temp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (macros.isEmpty()) {
            try {
                File file = new File(randomFileName());
                file.createNewFile();

                PrintWriter pw = new PrintWriter(file);
                pw.println("Macro 1");
                pw.flush();
                pw.close();

                fileName.put("Macro 1", file.toString());
                macros.add("Macro 1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(macros);

        //add them to edittext
        editText.setText(macros.get(0));

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        updateMacro();

        //code to make app bigger
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    setHighVisibility();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        saveMacro(fileName.get(editText.getText().toString()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setHighVisibility();
        }
    }

    private void setHighVisibility() { //hide nav bar and make app fullscreen
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }

    public void inflate(View view) {
        if (editText.isFocusable()) return; //dont open if user is trying to rename a file

        if (param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (popupWindow.isShowing()) {
            LinearLayout linearLayout = popupWindow.getContentView().findViewById(R.id.linear_layout);
            linearLayout.removeAllViews();
            for (int i = 0; i < macros.size(); i++) {
                TextView textView = new TextView(this);
                textView.setMinHeight(0);
                textView.setMinWidth(0);
                textView.setText(macros.get(i));
                textView.setTextSize(20);

                linearLayout.addView(textView, 0);
            }
        } else {
            saveMacro(fileName.get(editText.getText().toString()));

            LayoutInflater layoutInflater
                    = (LayoutInflater) getBaseContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.popup_choose, null);


            LinearLayout linearLayout = popupView.findViewById(R.id.linear_layout);
            linearLayout.setMinimumWidth(editText.getWidth());
            for (int i = 0; i < macros.size(); i++) {
                TextView textView = new TextView(this);
                textView.setMinHeight(0);
                textView.setMinWidth(0);
                textView.setText(macros.get(i));
                textView.setTextSize(20);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText(((TextView) v).getText().toString());
                        updateMacro();
                        popupWindow.dismiss();
                    }
                });

                linearLayout.addView(textView, i);
            }

            popupWindow = new PopupWindow(
                    popupView,
                    editText.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            popupWindow.setFocusable(true);
            popupWindow.update();
            popupWindow.showAsDropDown(view);
        }
    }

    public void add(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        String name;
        int index = 1;

        while (true) {
            name = "Macro " + index;
            if (!macros.contains(name)) break;
            index++;
        }

        File file = new File(randomFileName());

        try {
            file.createNewFile();
            PrintWriter pw = new PrintWriter(file);
            pw.println(name);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        fileName.put(name, file.toString());
        macros.add(name);
        Collections.sort(macros);

        saveMacro(fileName.get(editText.getText().toString()));
        editText.setText(name);
        updateMacro();
    }

    public void del(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (macros.size() == 1) {
            Toast.makeText(this, "There must always be at least 1 macro!", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = editText.getText().toString();
        File file = new File(fileName.get(name));

        try {
            file.delete();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        fileName.remove(name);

        for (int i = 0; i < macros.size(); i++) {
            if (macros.get(i).equals(name)) {
                macros.remove(i);
                editText.setText(macros.get(Math.min(i, macros.size() - 1)));
                break;
            }
        }
        updateMacro();
    }

    public void rename(View view) {
        if (param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editText.isFocusable()) { //check if renaming is valid
            String newName = editText.getText().toString();

            if (!newName.equals(oldName)) {
                if (macros.contains(newName)) {
                    Toast.makeText(this, "Invalid file name", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < macros.size(); i++) {
                    if (macros.get(i).equals(oldName)) macros.set(i, newName);
                }

                fileName.put(newName, fileName.get(oldName));
                fileName.remove(oldName);

                Collections.sort(macros);

                try {
                    Vector<String> temp = new Vector<>();
                    File file = new File(fileName.get(newName));
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        temp.add(scanner.nextLine());
                    }
                    scanner.close();

                    temp.set(0, newName);

                    PrintWriter pw = new PrintWriter(file);
                    for (String i : temp) {
                        pw.println(i);
                    }
                    pw.flush();
                    pw.close();
                } catch (Exception e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            rename_btn.setImageResource(R.drawable.ic_rename);
        } else { //allow user to edit
            oldName = editText.getText().toString();

            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);

            rename_btn.setImageResource(R.drawable.ic_tick);
        }
    }

    private void updateMacro() {
        try {
            mAdapter = new RecyclerViewAdapter(Macro.getMacros(fileName.get(editText.getText().toString())), this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "File corrupted >.<", Toast.LENGTH_SHORT).show();
            return;
        }

        recyclerView.setAdapter(mAdapter);
        updateSelected(null);
    }

    private void saveMacro(String fileName) {
        try {
            Macro.save(fileName, mAdapter.getMacros());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "File corrupted >.<", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void add_keystroke(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAdapter.add_keystroke();
    }

    public void add_text(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter.add_text();
    }

    public void add_delay(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAdapter.add_delay();
    }

    public void add_loop(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter.add_loop();
    }

    public void up(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter.up();
    }

    public void down(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter.down();
    }

    public boolean updateSelected(Command command) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return false;
        }

        this.command = command;

        if (command == null) {
            param_title.setVisibility(View.INVISIBLE);
            param.setVisibility(View.INVISIBLE);
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
        } else {
            param_title.setVisibility(View.VISIBLE);
            param.setVisibility(View.VISIBLE);
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            param.setText(command.getArg());

            if (command instanceof KeyStroke) {
                param_title.setText("Key Code");
                param.setInputType(InputType.TYPE_CLASS_TEXT);
                param.setMinLines(1);
                param.setMaxLines(1);
            } else if (command instanceof Text) {
                param_title.setText("Text");
                param.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                param.setMinLines(3);
                param.setMaxLines(3);
            } else if (command instanceof Delay) {
                param_title.setText("Delay Time (ms)");
                param.setInputType(InputType.TYPE_CLASS_NUMBER);
                param.setMinLines(1);
                param.setMaxLines(1);
            } else {
                param_title.setText("Loop Times");
                param.setInputType(InputType.TYPE_CLASS_NUMBER);
                param.setMinLines(1);
                param.setMaxLines(1);
            }
        }

        return true;
    }

    public void edit(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);

        param.setFocusable(true);
        param.setFocusableInTouchMode(true);
    }

    public void delete(View view) {
        if (editText.isFocusable() || param.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAdapter.delete(command.getId());

        updateSelected(null);
    }

    public void save(View view) {
        if (editText.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Pair<Boolean, String> res = mAdapter.update(command.getId(), param.getText().toString());
        if (res.first) {
            Toast.makeText(getApplicationContext(), res.second, Toast.LENGTH_SHORT).show();
            return;
        }

        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        param.setFocusable(false);
        param.setFocusableInTouchMode(false);
    }

    public void cancel(View view) {
        if (editText.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        editText.setText(command.getArg());

        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        param.setFocusable(false);
        param.setFocusableInTouchMode(false);
    }

    private String randomFileName() {
        while (true) {
            String name = getFilesDir() + "/macros/" + UUID.randomUUID().toString();
            if (!new File(name).exists()) return name;
        }
    }
}
