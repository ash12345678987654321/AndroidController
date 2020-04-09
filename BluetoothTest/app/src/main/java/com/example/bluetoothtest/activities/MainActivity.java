package com.example.bluetoothtest.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.controllerData.Dpad;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

//TODO allow for no selection available
public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private String oldName;
    private ImageButton rename_btn;

    private RelativeLayout relativeLayout;

    private Vector<String> layouts;

    private View decorView;

    private PopupWindow popupWindow = new PopupWindow(); //so we can access it easily

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        rename_btn = findViewById(R.id.rename_btn);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");

        File path;
        path = new File(getFilesDir() + "/macros/");

        if (!path.exists()) {
            path.mkdir();
        }

        if (path.listFiles().length==0) createMacros();

        path = new File(getFilesDir() + "/layouts/");
        layouts = new Vector<>();
        //Log.d("ZZZ",path.toString());

        if (!path.exists()) {
            path.mkdir();
        }

        if (path.listFiles().length==0) createLayouts();

        for (File i : path.listFiles()) {
            //Log.d("ZZZ","File found: "+i.getName());
            layouts.add(i.getName());
        }

        Collections.sort(layouts);

        //add them to edittext
        editText.setText(layouts.get(0));

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        relativeLayout = findViewById(R.id.relative_layout);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.height = Resources.getSystem().getDisplayMetrics().heightPixels / 2 + 40;
        layoutParams.width = Resources.getSystem().getDisplayMetrics().widthPixels / 2 + 40;
        relativeLayout.setLayoutParams(layoutParams);

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

    @Override
    public void onResume() {
        super.onResume();
        fillPreview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsBtn:
                startActivity(new Intent(this.getApplicationContext(), SettingsActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void start(View view) {
        if (editText.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editText.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ControllerActivity.class);
        intent.putExtra("preset", editText.getText().toString());
        startActivity(intent);
    }

    public void edit(View view) {
        if (editText.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("preset", editText.getText().toString());
        startActivity(intent);
    }

    public void macro(View view) {
        if (editText.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MacroActivity.class);
        startActivity(intent);
    }

    public void inflate(View view) {
        if (editText.isFocusable()) return; //dont open if user is trying to rename a file

        if (popupWindow.isShowing()) {
            LinearLayout linearLayout = popupWindow.getContentView().findViewById(R.id.linear_layout);
            linearLayout.removeAllViews();
            for (int i = 0; i < layouts.size(); i++) {
                TextView textView = new TextView(this);
                textView.setMinHeight(0);
                textView.setMinWidth(0);
                textView.setText(layouts.get(i));
                textView.setTextSize(20);

                linearLayout.addView(textView, 0);
            }
        } else {
            LayoutInflater layoutInflater
                    = (LayoutInflater) getBaseContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.popup_choose, null);


            LinearLayout linearLayout = popupView.findViewById(R.id.linear_layout);
            linearLayout.setMinimumWidth(editText.getWidth());
            for (int i = 0; i < layouts.size(); i++) {
                TextView textView = new TextView(this);
                textView.setMinHeight(0);
                textView.setMinWidth(0);
                textView.setText(layouts.get(i));
                textView.setTextSize(20);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText(((TextView) v).getText().toString());
                        fillPreview();
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
        if (editText.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        File file;
        int index = 1;

        while (true) {
            file = new File(getFilesDir() + "/layouts/Layout " + index);
            if (!file.exists()) break;
            index++;
        }

        try {
            file.createNewFile();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        layouts.add(file.getName());
        Collections.sort(layouts);

        editText.setText(file.getName());
        fillPreview();
    }

    public void del(View view) {
        if (editText.isFocusable()) {
            Toast.makeText(this, "Finish editing first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (layouts.size() == 1) {
            Toast.makeText(this, "There must always be at least 1 layout!", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(getFilesDir() + "/layouts/" + editText.getText());

        try {
            file.delete();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < layouts.size(); i++) {
            if (layouts.get(i).equals(file.getName())) {
                layouts.remove(i);
                editText.setText(layouts.get(Math.min(i, layouts.size() - 1)));
                break;
            }
        }
        fillPreview();
    }

    public void rename(View view) {
        if (editText.isFocusable()) { //check if renaming is valid
            String newName = editText.getText().toString();

            if (!newName.equals(oldName)) {
                File file = new File(getFilesDir() + "/layouts/" + newName);
                if (file.exists()) {
                    Toast.makeText(this, "Invalid file name", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < layouts.size(); i++) {
                    if (layouts.get(i).equals(oldName)) layouts.set(i, newName);
                }

                File file2 = new File(getFilesDir() + "/layouts/" + oldName);
                file2.renameTo(file);

                Collections.sort(layouts);
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

    //wow we are gonna show a preview bcas thats cool
    private void fillPreview() {
        relativeLayout.removeAllViews();
        try {
            File file = new File(getFilesDir() + "/layouts/" + editText.getText().toString());
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] args = scanner.nextLine().split("\0");

                switch (args[0]) {
                    case "Btn":
                        Btn(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
                        break;

                    case "Dpad":
                        Dpad(Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
                        break;

                    case "Macro":
                        Macro(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
                        break;
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "File corrupted >.<", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void Btn(int height, int width, int marginTop, int marginLeft) {
        height /= 2;
        width /= 2;
        marginTop /= 2;
        marginLeft /= 2;

        Button btn = new Button(this);

        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setBackgroundResource(R.drawable.button_up);

        relativeLayout.addView(btn);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);
    }

    private void Dpad(int diameter, int marginTop, int marginLeft) {
        diameter /= 2;
        marginTop /= 2;
        marginLeft /= 2;

        Button btn = new Button(this);

        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setBackgroundResource(R.drawable.dpad);

        relativeLayout.addView(btn);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);
    }

    private void Macro(int height, int width, int marginTop, int marginLeft) {
        height /= 2;
        width /= 2;
        marginTop /= 2;
        marginLeft /= 2;

        ImageView btn = new ImageView(this);

        btn.setMaxHeight(height);
        btn.setMaxWidth(width);
        btn.setMinimumHeight(height);
        btn.setMinimumWidth(width);

        btn.setBackgroundResource(R.drawable.button_macro);

        btn.setImageResource(R.drawable.ic_m);
        btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        btn.setAdjustViewBounds(false);

        relativeLayout.addView(btn);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);
    }

    //someone suggested that i create presets for users
    private void createLayouts(){
        try {
            File file = new File(getFilesDir() + "/layouts/" + "Layout");
            PrintWriter pw=new PrintWriter(file);

            pw.println("Dpad"+"\0"+"w"+"\0"+"s"+"\0"+"a"+"\0"+"d"+"\0"+getResources().getDisplayMetrics().heightPixels+"\0"+"0"+"\0"+"0");
            pw.println("Btn"+"\0"+"Button"+"\0"+"space"+"\0"+getResources().getDisplayMetrics().heightPixels/2+"\0"+getResources().getDisplayMetrics().heightPixels/2+"\0"+getResources().getDisplayMetrics().heightPixels/2+"\0"+getResources().getDisplayMetrics().heightPixels);
            pw.println("Macro"+"\0"+"Macro"+"\0"+getFilesDir()+"/macros/"+"25268206-7a75-11ea-bc55-0242ac130003"+"\0"+false+"\0"+getResources().getDisplayMetrics().heightPixels/2+"\0"+getResources().getDisplayMetrics().heightPixels/2+"\0"+"0"+"\0"+getResources().getDisplayMetrics().heightPixels);

            pw.flush();
            pw.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createMacros(){
        try {
            File file = new File(getFilesDir() + "/macros/" + "25268206-7a75-11ea-bc55-0242ac130003");
            PrintWriter pw=new PrintWriter(file);

            pw.println("Jitter click");
            pw.println("KeyStroke"+"\0"+"m1"+"\0"+true+"\0"+false+"\0"+"367ccb0c-d582-42e0-b004-da3d9d2159e1");
            pw.println("KeyStroke"+"\0"+"m1"+"\0"+false+"\0"+true+"\0"+"367ccb0c-d582-42e0-b004-da3d9d2159e1");
            pw.println("Delay"+"\0"+"200"+"\0"+false+"\0"+false+"\0"+"71089e51-d764-4e28-b25c-2f2d8a8f4800");

            pw.flush();
            pw.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

