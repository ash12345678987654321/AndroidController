package com.why.controller.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.why.controller.R;
import com.why.controller.bluetooth.DataSender;
import com.why.controller.bluetooth.Main;
import com.why.controller.controllerData.Btn;
import com.why.controller.controllerData.Dpad;
import com.why.controller.controllerData.JoyStick;
import com.why.controller.controllerData.Macro;

import java.io.File;
import java.util.Scanner;

import static java.lang.Math.min;

public class ControllerActivity extends AppCompatActivity {
    public static StringBuilder cmd = new StringBuilder();

    //for networking to tell other threads what the client is
    public static String ip;
    public static int port;

    private RelativeLayout layout;
    private RelativeLayout.LayoutParams layoutParams;
    private View decorView;
    private DataSender ds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        if (!Main.connected) {
            Toast.makeText(this, "Not connected to bluetooth yet", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        layout = findViewById(R.id.layout_controller_tag);

        String preset = getIntent().getStringExtra("preset");
        Log.d("ZZZ", "Current layout: " + preset);
        try {
            File file = new File(getFilesDir() + "/layouts/" + preset);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] args = scanner.nextLine().split("\0");

                switch (args[0]) {
                    case "Btn":
                        Btn(args[1], new Btn(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
                        break;

                    case "Dpad":
                        Dpad(new Dpad(args[1], args[2], args[3], args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
                        break;

                    case "Macro":
                        Macro(args[1], new Macro(args[2], Boolean.parseBoolean(args[3])), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
                        break;

                    case "JoyStick":
                        JoyStick(new JoyStick(Integer.parseInt(args[1])), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                        break;
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "File corrupted >.<", Toast.LENGTH_SHORT).show();
            finish();
        }

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

        cmd.setLength(0);

        ds = new DataSender();
        ds.start();

        Log.d("ZZZ", "Data being sent");
    }

    @Override
    protected void onPause() {
        super.onPause();

        for (int i = 0; i < layout.getChildCount(); i++) {
            if (layout.getChildAt(i).getTag() instanceof Macro) {
                Log.d("ZZZ", "Macro at " + i);
                ((Macro) layout.getChildAt(i).getTag()).kill();
            }
        }

        ds.interrupt(); //stop the app from sending anything when not running
        Log.d("ZZZ", "Data stoppped being sent");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    cmd.append("D volumeup\0");
                } else if (action == KeyEvent.ACTION_UP) {
                    cmd.append("U volumeup\0");
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    cmd.append("D volumedown\0");
                } else if (action == KeyEvent.ACTION_UP) {
                    cmd.append("U volumedown\0");
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


    //controller setups (adding them programmically)
    @SuppressLint("ClickableViewAccessibility")
    private void Btn(String label, final Btn output, int height, int width, int marginTop, int marginLeft) {
        Button btn = new Button(this);

        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);
        btn.setText(label);

        btn.setTag(output);

        btn.setBackgroundResource(R.drawable.button_up);

        layout.addView(btn);

        layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);


        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd.append(output.down());
                    v.setBackgroundResource(R.drawable.button_down);
                    ((Button) v).setTextColor(getResources().getColor(R.color.background));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd.append(output.up());
                    v.setBackgroundResource(R.drawable.button_up);
                    ((Button) v).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                return true;
            }
        });


    }

    @SuppressLint("ClickableViewAccessibility")
    private void Dpad(final Dpad output, final int diameter, final int marginTop, final int marginLeft) {
        Button btn = new Button(this);
        final Button pointer = new Button(this);

        pointer.setHeight(diameter / 4);
        pointer.setWidth(diameter / 4);
        pointer.setMinimumHeight(0);
        pointer.setMinimumWidth(0);

        btn.setTag(output);

        pointer.setBackgroundResource(R.drawable.dpad_pointer);

        layout.addView(pointer);

        layoutParams = (RelativeLayout.LayoutParams) pointer.getLayoutParams();
        layoutParams.leftMargin = marginLeft + (diameter - diameter / 4) / 2;
        layoutParams.topMargin = marginTop + (diameter - diameter / 4) / 2;
        pointer.setLayoutParams(layoutParams);

        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setBackgroundResource(R.drawable.dpad);

        layout.addView(btn);

        layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    double x = event.getX() - (double) diameter / 2, y = event.getY() - (double) diameter / 2;
                    double dist = Math.sqrt(x * x + y * y);
                    x /= dist;
                    y /= dist;

                    dist = min(dist, (double) diameter / 3);

                    layoutParams = (RelativeLayout.LayoutParams) pointer.getLayoutParams();
                    layoutParams.leftMargin = (int) (marginLeft + x * dist) + (diameter - diameter / 4) / 2;
                    layoutParams.topMargin = (int) (marginTop + y * dist) + (diameter - diameter / 4) / 2;
                    pointer.setLayoutParams(layoutParams);


                    if (dist < (double) diameter / 10) cmd.append(output.setPos(0, 0));
                    else cmd.append(output.setPos(x, y));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd.append(output.setPos(0, 0));

                    layoutParams = (RelativeLayout.LayoutParams) pointer.getLayoutParams();
                    layoutParams.leftMargin = marginLeft + (diameter - diameter / 4) / 2;
                    layoutParams.topMargin = marginTop + (diameter - diameter / 4) / 2;
                    pointer.setLayoutParams(layoutParams);
                }
                return true;
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void Macro(String label, final Macro output, int height, int width, int marginTop, int marginLeft) {
        Button btn = new Button(this);

        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);
        btn.setText(label);

        btn.setTag(output);

        btn.setBackgroundResource(R.drawable.button_up);

        layout.addView(btn);

        layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    output.setToggle(true);
                    v.setBackgroundResource(R.drawable.button_down);
                    ((Button) v).setTextColor(getResources().getColor(R.color.background));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    output.setToggle(false);
                    v.setBackgroundResource(R.drawable.button_up);
                    ((Button) v).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void JoyStick(final JoyStick output, final int diameter, final int marginTop, final int marginLeft) { //TODO shall i put another fucking todo here
        Button btn = new Button(this);
        final Button pointer = new Button(this);

        pointer.setHeight(diameter / 4);
        pointer.setWidth(diameter / 4);
        pointer.setMinimumHeight(0);
        pointer.setMinimumWidth(0);

        btn.setTag(output);

        pointer.setBackgroundResource(R.drawable.dpad_pointer);

        layout.addView(pointer);

        layoutParams = (RelativeLayout.LayoutParams) pointer.getLayoutParams();
        layoutParams.leftMargin = marginLeft + (diameter - diameter / 4) / 2;
        layoutParams.topMargin = marginTop + (diameter - diameter / 4) / 2;
        pointer.setLayoutParams(layoutParams);

        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setBackgroundResource(R.drawable.dpad);

        layout.addView(btn);

        layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    double x = event.getX() - (double) diameter / 2, y = event.getY() - (double) diameter / 2;
                    double dist = Math.sqrt(x * x + y * y);
                    x /= dist;
                    y /= dist;

                    dist = min(dist, (double) diameter / 3);

                    layoutParams = (RelativeLayout.LayoutParams) pointer.getLayoutParams();
                    layoutParams.leftMargin = (int) (marginLeft + x * dist) + (diameter - diameter / 4) / 2;
                    layoutParams.topMargin = (int) (marginTop + y * dist) + (diameter - diameter / 4) / 2;
                    pointer.setLayoutParams(layoutParams);

                    cmd.append(output.setPos(x, y));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("ZZZ","released");
                    layoutParams = (RelativeLayout.LayoutParams) pointer.getLayoutParams();
                    layoutParams.leftMargin = marginLeft + (diameter - diameter / 4) / 2;
                    layoutParams.topMargin = marginTop + (diameter - diameter / 4) / 2;
                    pointer.setLayoutParams(layoutParams);

                    cmd.append(output.setPos(0, 0));
                    Log.d("ZZZ",cmd.toString());
                }
                return true;
            }
        });
    }
}
