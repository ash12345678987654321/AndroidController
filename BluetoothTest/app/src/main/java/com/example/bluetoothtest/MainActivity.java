package com.example.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private ArrayList<String> presets;

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");

        spinner=findViewById(R.id.preset);

        File path=this.getFilesDir();
        presets=new ArrayList<>();
        Log.d("ZZZ",path.toString());
        for (File i:path.listFiles()){
            Log.d("ZZZ","File found: "+i.getName());
            presets.add(i.getName().substring(0,i.getName().length()-4));
        }

        Collections.sort(presets);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, presets);
        spinner.setAdapter(adapter);

        //code to make app bigger
        decorView=getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0){
                    setHighVisibility();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            setHighVisibility();
        }
    }

    private void setHighVisibility(){ //hide nav bar and make app fullscreen
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                View.SYSTEM_UI_FLAG_FULLSCREEN|
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        try{
            String ip = sharedPreferences.getString("ip", "");
            int port= Integer.parseInt(sharedPreferences.getString("port", "2764"));

            Log.d("ZZZ",ip+" "+port);

            DataSender.setIP(ip);
            DataSender.setPort(port);
        }
        catch (Exception e){
            e.printStackTrace();
            DataSender.setInvalid();
            Toast.makeText(this,"Could not parse port",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_nav_menu,menu);
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

    public void start(View view){
        Intent intent=new Intent(this,ControllerActivity.class);
        intent.putExtra("preset",presets.get(spinner.getSelectedItemPosition()));
        startActivity(intent);
    }

    public void edit(View view) {
        Intent intent=new Intent(this, EditActivity.class);
        intent.putExtra("preset",presets.get(spinner.getSelectedItemPosition()));
        startActivity(intent);
    }
}
