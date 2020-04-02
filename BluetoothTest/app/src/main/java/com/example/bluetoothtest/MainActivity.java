package com.example.bluetoothtest;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private ArrayList<String> layouts;

    private View decorView;

    private PopupWindow popupWindow; //so we can access it easily

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");

        spinner = findViewById(R.id.preset);

        File path = new File(getFilesDir() + "/layouts/");
        layouts = new ArrayList<>();
        //Log.d("ZZZ",path.toString());
        for (File i : path.listFiles()) {
            //Log.d("ZZZ","File found: "+i.getName());
            layouts.add(i.getName());
        }

        if (layouts.isEmpty()) {
            try {
                File file = new File(getFilesDir() + "/layouts/" + "New layout 1");
                file.createNewFile();
                layouts.add(file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(layouts);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, layouts);
        spinner.setAdapter(adapter);

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
        Intent intent = new Intent(this, ControllerActivity.class);
        intent.putExtra("preset", layouts.get(spinner.getSelectedItemPosition()));
        startActivity(intent);
    }

    public void edit(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("preset", layouts.get(spinner.getSelectedItemPosition()));
        startActivity(intent);
    }

    public void macro(View view) {
        Intent intent = new Intent(this, MacroActivity.class);
        startActivity(intent);
    }


    public void add(View view) {
        String path = this.getFilesDir().toString() + "/";
        int index = 1;
        while (true) {
            File file = new File(path + "New layout " + index);

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                layouts.add(file.getName());
                Collections.sort(layouts);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, layouts);
                spinner.setAdapter(adapter);
                for (int i = 0; i < layouts.size(); i++) {
                    if (layouts.get(i).equals(file.getName())) spinner.setSelection(i);
                }
                return;
            }

            index++;
        }
    }

    public void del(View view) {
        if (layouts.size() == 1) {
            Toast.makeText(this, "Must always have at least 1 layout!", Toast.LENGTH_SHORT).show();
            return;
        }

        int index = spinner.getSelectedItemPosition();

        try {
            File file = new File(getFilesDir() + "/" + layouts.get(index));
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        layouts.remove(index);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, layouts);
        spinner.setAdapter(adapter);

        spinner.setSelection(Math.max(0, index - 1));
    }

    public void rename(View view) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_rename, null);


        popupWindow = new PopupWindow(
                popupView,
                Resources.getSystem().getDisplayMetrics().widthPixels / 3,
                Resources.getSystem().getDisplayMetrics().heightPixels / 3);

        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAsDropDown(view, 50, -100);
    }

    public void rename_cfm(View view) {
        String filename = ((EditText) popupWindow.getContentView().findViewById(R.id.filename)).getText().toString();

        File file = new File(getFilesDir() + "/" + filename);

        if (file.exists()) {
            Toast.makeText(this, "Invalid file name", Toast.LENGTH_SHORT).show();
            return;
        }

        File file2 = new File(getFilesDir() + "/" + layouts.get(spinner.getSelectedItemPosition()));

        file2.renameTo(file);

        for (int i = 0; i < layouts.size(); i++) {
            if (layouts.get(i).equals(file2.getName())) layouts.set(i, file.getName());
        }

        Collections.sort(layouts);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, layouts);
        spinner.setAdapter(adapter);

        for (int i = 0; i < layouts.size(); i++) {
            if (layouts.get(i).equals(file.getName())) spinner.setSelection(i);
        }
        popupWindow.dismiss();
    }
}

