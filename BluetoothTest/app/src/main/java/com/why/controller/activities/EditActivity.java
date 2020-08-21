package com.why.controller.activities;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.why.controller.R;
import com.why.controller.controllerData.Btn;
import com.why.controller.controllerData.Dpad;
import com.why.controller.controllerData.JoyStick;
import com.why.controller.controllerData.KeyCode;
import com.why.controller.controllerData.Macro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class EditActivity extends AppCompatActivity {
    private RelativeLayout layout;

    private View decorView;

    private ScrollView add_pane;

    private Vector<String> macros = new Vector<>();
    private HashMap<String, String> fileName = new HashMap<>();
    private HashMap<String, String> macroName = new HashMap<>();

    private PopupWindow popupWindow = new PopupWindow(); //so we can access it easily

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        layout = findViewById(R.id.layout_controller_tag);
        add_pane = findViewById(R.id.add_pane);

        String preset = getIntent().getStringExtra("preset");
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
                        JoyStick(new JoyStick(Double.parseDouble(args[1])), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                        break;

                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "File corrupted >.<", Toast.LENGTH_SHORT).show();
            finish();
        }

        File path = new File(getFilesDir() + "/macros/");
        //Log.d("ZZZ",path.toString());

        for (File i : path.listFiles()) {
            //Log.d("ZZZ","File found: "+i.getName());
            try {
                Scanner scanner = new Scanner(i);
                String temp = scanner.nextLine();
                fileName.put(temp, i.toString());
                macroName.put(i.toString(), temp);
                macros.add(temp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        macroName.put("", ""); //random cornercase

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
    public void onPause() {
        super.onPause();

        String preset = getIntent().getStringExtra("preset");
        try {
            File file = new File(getFilesDir() + "/layouts/" + preset);
            PrintWriter pw = new PrintWriter(file);

            for (int x = 0; x < layout.getChildCount(); x++) {
                Button v = (Button) layout.getChildAt(x);
                Object args = v.getTag();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                if (args instanceof Btn) {
                    pw.println("Btn\0" + v.getText().toString() + "\0" + ((Btn) args).getOutput() + "\0" + v.getHeight() + "\0" + v.getWidth() + "\0" + layoutParams.topMargin + "\0" + layoutParams.leftMargin);
                } else if (args instanceof Dpad) {
                    pw.println("Dpad\0" + ((Dpad) args).getOutput() + "\0" + v.getHeight() + "\0" + layoutParams.topMargin + "\0" + layoutParams.leftMargin);
                } else if (args instanceof Macro) {
                    pw.println("Macro\0" + v.getText().toString() + "\0" + ((Macro) args).getOutput() + "\0" + v.getHeight() + "\0" + v.getWidth() + "\0" + layoutParams.topMargin + "\0" + layoutParams.leftMargin);
                } else if (args instanceof JoyStick){
                    pw.println("JoyStick\0" + ((JoyStick) args).getOutput() + "\0" + v.getHeight() + "\0" + layoutParams.topMargin + "\0" + layoutParams.leftMargin);
                }
            }

            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "File corrupted >.<", Toast.LENGTH_SHORT).show();
        }
    }


    //editing interface
    public void expand_add_pane(View view) {
        ObjectAnimator animation, animation2;

        if (view.getTranslationX() != 0) {
            animation = ObjectAnimator.ofFloat(add_pane, "translationX", 0);
            animation2 = ObjectAnimator.ofFloat(view, "translationX", 0);
        } else {
            animation = ObjectAnimator.ofFloat(add_pane, "translationX", add_pane.getWidth());
            animation2 = ObjectAnimator.ofFloat(view, "translationX", add_pane.getWidth());
        }

        animation.setDuration(300);
        animation.start();
        animation2.setDuration(300);
        animation2.start();
    }

    public void add_btn(View view) {
        Btn("", new Btn(""), 300, 300, Resources.getSystem().getDisplayMetrics().heightPixels / 2 - 150, Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 150);
    }

    public void add_dpad(View view) {
        Dpad(new Dpad("", "", "", ""), 300, Resources.getSystem().getDisplayMetrics().heightPixels / 2 - 150, Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 150);
    }

    public void add_macro(View view) {
        Macro("", new Macro("", true), 300, 300, Resources.getSystem().getDisplayMetrics().heightPixels / 2 - 150, Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 150);
    }

    public void add_joystick(View view) {
        JoyStick(new JoyStick(50.0), 300, Resources.getSystem().getDisplayMetrics().heightPixels / 2 - 150, Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 150);
    }

    //controller setups (adding them programmically)
    private void Btn(String label, Btn tag, int height, int width, int marginTop, int marginLeft) {
        Button btn = new Button(this);

        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);
        btn.setText(label);

        btn.setTag(tag);

        btn.setBackgroundResource(R.drawable.button_up);

        layout.addView(btn);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);

        final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(this, new ScaleListener(btn, height, width));
        final GestureDetector doubleDetector = new GestureDetector(this, new GestureListener(btn));
        final double[] prev_pos = new double[2];
        final int[] active_pointer = {-1};
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleDetector.onTouchEvent(event);
                doubleDetector.onTouchEvent(event);

                if (popupWindow.isShowing()) return true;

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                    int index = event.findPointerIndex(event.getPointerId(0));
                    double x = layoutParams.leftMargin + event.getX(index), y = layoutParams.topMargin + event.getY(index);

                    //Log.d("ZZZ","active poitner: "+index+";pos: "+x+" "+y);

                    if (event.getPointerId(0) == active_pointer[0]) {
                        layoutParams.leftMargin += x - prev_pos[0];
                        layoutParams.topMargin += y - prev_pos[1];
                    }

                    active_pointer[0] = event.getPointerId(0);
                    prev_pos[0] = x;
                    prev_pos[1] = y;

                    //make sure view stays inside
                    layoutParams.leftMargin = Math.max(0, Math.min(Resources.getSystem().getDisplayMetrics().widthPixels - v.getWidth(), layoutParams.leftMargin));
                    layoutParams.topMargin = Math.max(0, Math.min(Resources.getSystem().getDisplayMetrics().heightPixels - v.getHeight(), layoutParams.topMargin));
                    v.setLayoutParams(layoutParams);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    active_pointer[0] = -1;
                }
                return true;
            }
        });
    }

    private void Dpad(Dpad tag, int diameter, int marginTop, int marginLeft) {
        Button btn = new Button(this);

        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setTag(tag);

        btn.setBackgroundResource(R.drawable.dpad);

        layout.addView(btn);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);

        final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(this, new ScaleListener(btn, diameter, diameter));
        final GestureDetector doubleDetector = new GestureDetector(this, new GestureListener(btn));
        final double[] prev_pos = new double[2];
        final int[] active_pointer = {-1};
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleDetector.onTouchEvent(event);
                doubleDetector.onTouchEvent(event);

                if (popupWindow.isShowing()) return true;

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                    int index = event.findPointerIndex(event.getPointerId(0));
                    double x = layoutParams.leftMargin + event.getX(index), y = layoutParams.topMargin + event.getY(index);

                    //Log.d("ZZZ","active poitner: "+index+";pos: "+x+" "+y);

                    if (event.getPointerId(0) == active_pointer[0]) {
                        layoutParams.leftMargin += x - prev_pos[0];
                        layoutParams.topMargin += y - prev_pos[1];
                    }

                    active_pointer[0] = event.getPointerId(0);
                    prev_pos[0] = x;
                    prev_pos[1] = y;

                    //make sure view stays inside
                    layoutParams.leftMargin = Math.max(0, Math.min(Resources.getSystem().getDisplayMetrics().widthPixels - v.getWidth(), layoutParams.leftMargin));
                    layoutParams.topMargin = Math.max(0, Math.min(Resources.getSystem().getDisplayMetrics().heightPixels - v.getHeight(), layoutParams.topMargin));
                    v.setLayoutParams(layoutParams);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    active_pointer[0] = -1;
                }
                return true;
            }
        });
    }

    private void Macro(String label, Macro tag, int height, int width, int marginTop, int marginLeft) {
        Button btn = new Button(this);

        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);
        btn.setText(label);

        btn.setTag(tag);

        btn.setBackgroundResource(R.drawable.button_up);

        layout.addView(btn);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);

        final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(this, new ScaleListener(btn, height, width));
        final GestureDetector doubleDetector = new GestureDetector(this, new GestureListener(btn));
        final double[] prev_pos = new double[2];
        final int[] active_pointer = {-1};
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleDetector.onTouchEvent(event);
                doubleDetector.onTouchEvent(event);

                if (popupWindow.isShowing()) return true;

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                    int index = event.findPointerIndex(event.getPointerId(0));
                    double x = layoutParams.leftMargin + event.getX(index), y = layoutParams.topMargin + event.getY(index);

                    //Log.d("ZZZ","active poitner: "+index+";pos: "+x+" "+y);

                    if (event.getPointerId(0) == active_pointer[0]) {
                        layoutParams.leftMargin += x - prev_pos[0];
                        layoutParams.topMargin += y - prev_pos[1];
                    }

                    active_pointer[0] = event.getPointerId(0);
                    prev_pos[0] = x;
                    prev_pos[1] = y;

                    //make sure view stays inside
                    layoutParams.leftMargin = Math.max(0, Math.min(Resources.getSystem().getDisplayMetrics().widthPixels - v.getWidth(), layoutParams.leftMargin));
                    layoutParams.topMargin = Math.max(0, Math.min(Resources.getSystem().getDisplayMetrics().heightPixels - v.getHeight(), layoutParams.topMargin));
                    v.setLayoutParams(layoutParams);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    active_pointer[0] = -1;
                }
                return true;
            }
        });
    }

    private void JoyStick(JoyStick tag, int diameter, int marginTop, int marginLeft) {
        Button btn = new Button(this);

        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setTag(tag);

        btn.setBackgroundResource(R.drawable.dpad); //TODO: for now it will share the same texture as dpad, dpad texture change later

        layout.addView(btn);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        btn.setLayoutParams(layoutParams);

        final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(this, new ScaleListener(btn, diameter, diameter));
        final GestureDetector doubleDetector = new GestureDetector(this, new GestureListener(btn));
        final double[] prev_pos = new double[2];
        final int[] active_pointer = {-1};
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleDetector.onTouchEvent(event);
                doubleDetector.onTouchEvent(event);

                if (popupWindow.isShowing()) return true;

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                    int index = event.findPointerIndex(event.getPointerId(0));
                    double x = layoutParams.leftMargin + event.getX(index), y = layoutParams.topMargin + event.getY(index);

                    //Log.d("ZZZ","active poitner: "+index+";pos: "+x+" "+y);

                    if (event.getPointerId(0) == active_pointer[0]) {
                        layoutParams.leftMargin += x - prev_pos[0];
                        layoutParams.topMargin += y - prev_pos[1];
                    }

                    active_pointer[0] = event.getPointerId(0);
                    prev_pos[0] = x;
                    prev_pos[1] = y;

                    //make sure view stays inside
                    layoutParams.leftMargin = Math.max(0, Math.min(Resources.getSystem().getDisplayMetrics().widthPixels - v.getWidth(), layoutParams.leftMargin));
                    layoutParams.topMargin = Math.max(0, Math.min(Resources.getSystem().getDisplayMetrics().heightPixels - v.getHeight(), layoutParams.topMargin));
                    v.setLayoutParams(layoutParams);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    active_pointer[0] = -1;
                }
                return true;
            }
        });
    }

    //magic code to allow for resizing stuff
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private double scaleFactor = 1;
        private int height, width;
        private Button btn;

        private ScaleListener(Button btn, int height, int width) {
            this.btn = btn;
            this.height = height;
            this.width = width;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (popupWindow.isShowing()) return true;

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn.getLayoutParams();
            //Log.d("ZZZ","extra space on x-axis: "+layoutParams.leftMargin+" "+(Resources.getSystem().getDisplayMetrics().widthPixels-layoutParams.leftMargin-btn.getWidth()));
            //Log.d("ZZZ","extra space on x-axis: "+layoutParams.topMargin+" "+(Resources.getSystem().getDisplayMetrics().heightPixels-layoutParams.topMargin-btn.getHeight()));

            // Don't let the object get too large for the curr screen size
            scaleFactor *= Math.min(
                    Math.min(
                            (double) Math.min(layoutParams.leftMargin, Resources.getSystem().getDisplayMetrics().widthPixels - layoutParams.leftMargin - btn.getWidth()) / btn.getWidth() * 2 + 1,//test for the x-axis maximum
                            (double) Math.min(layoutParams.topMargin, Resources.getSystem().getDisplayMetrics().heightPixels - layoutParams.topMargin - btn.getHeight()) / btn.getHeight() * 2 + 1 //test for the y-axis maximum
                    ),
                    detector.getScaleFactor()
            );

            //Log.d("ZZZ","Current scale: "+scaleFactor);
            int height = (int) (this.height * scaleFactor), width = (int) (this.width * scaleFactor);
            layoutParams.topMargin += (btn.getHeight() - height) / 2;
            layoutParams.leftMargin += (btn.getWidth() - width) / 2;
            btn.setHeight(height);
            btn.setWidth(width);
            btn.setLayoutParams(layoutParams);
            return true;
        }
    }

    //show edit menu when double click happens
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {//TODO add listener for joysticks
        private Button btn;

        private GestureListener(Button btn) {
            this.btn = btn;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {

            if (popupWindow.isShowing()) return true; //dont allow popupwindow to show 2 times

            if (btn.getTag() instanceof Btn) {
                final Btn args = (Btn) btn.getTag();

                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup_btn, null);


                popupWindow = new PopupWindow(
                        popupView,
                        Resources.getSystem().getDisplayMetrics().widthPixels / 3,
                        (int) (Resources.getSystem().getDisplayMetrics().heightPixels / 2.5));

                popupWindow.setFocusable(true);
                popupWindow.update();
                popupWindow.showAsDropDown(btn, 50, -100);

                ((EditText) popupView.findViewById(R.id.label)).setText(btn.getText());
                ((EditText) popupView.findViewById(R.id.key)).setText(args.getOutput());

                popupView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String label = ((EditText) popupWindow.getContentView().findViewById(R.id.label)).getText().toString();
                        String key = ((EditText) popupWindow.getContentView().findViewById(R.id.key)).getText().toString();

                        Pair<Boolean, String> res = KeyCode.invalid(key);
                        if (res.first) {
                            Toast.makeText(getApplicationContext(), res.second, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        args.setOutput(key);
                        btn.setText(label);
                        popupWindow.dismiss();
                    }
                });

                popupView.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.removeView(btn);
                        popupWindow.dismiss();
                    }
                });
            } else if (btn.getTag() instanceof Dpad) {
                final Dpad args = (Dpad) btn.getTag();

                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup_dpad, null);


                popupWindow = new PopupWindow(
                        popupView,
                        Resources.getSystem().getDisplayMetrics().widthPixels / 3,
                        (int) (Resources.getSystem().getDisplayMetrics().heightPixels / 1.6));

                popupWindow.setFocusable(true);
                popupWindow.update();
                popupWindow.showAsDropDown(btn, 50, -100);

                ((EditText) popupView.findViewById(R.id.up)).setText(args.getUp());
                ((EditText) popupView.findViewById(R.id.down)).setText(args.getDown());
                ((EditText) popupView.findViewById(R.id.left)).setText(args.getLeft());
                ((EditText) popupView.findViewById(R.id.right)).setText(args.getRight());

                popupView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String up = ((EditText) popupWindow.getContentView().findViewById(R.id.up)).getText().toString();
                        String down = ((EditText) popupWindow.getContentView().findViewById(R.id.down)).getText().toString();
                        String left = ((EditText) popupWindow.getContentView().findViewById(R.id.left)).getText().toString();
                        String right = ((EditText) popupWindow.getContentView().findViewById(R.id.right)).getText().toString();

                        for (String i : new String[]{up, down, left, right}) {
                            Pair<Boolean, String> res = KeyCode.invalid(i);
                            if (res.first) {
                                Toast.makeText(getApplicationContext(), res.second, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        args.setDir(up, down, left, right);
                        popupWindow.dismiss();
                    }
                });

                popupView.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.removeView(btn);
                        popupWindow.dismiss();
                    }
                });
            } else if (btn.getTag() instanceof Macro) {
                final Macro args = (Macro) btn.getTag();

                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.popup_macro, null);

                final TextView txtView = popupView.findViewById(R.id.textView);

                ((EditText) popupView.findViewById(R.id.label)).setText(btn.getText());
                txtView.setText(macroName.get(args.getFileName()));
                ((CheckBox) popupView.findViewById(R.id.checkbox)).setChecked(args.getOnce());

                popupWindow = new PopupWindow(
                        popupView,
                        Resources.getSystem().getDisplayMetrics().widthPixels / 3,
                        (int) (Resources.getSystem().getDisplayMetrics().heightPixels / 1.7));

                popupWindow.setFocusable(true);
                popupWindow.update();
                popupWindow.showAsDropDown(btn, 50, -100);

                txtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final PopupWindow[] popupWindow2 = new PopupWindow[1]; //so we can access it easily

                        LayoutInflater layoutInflater
                                = (LayoutInflater) getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = layoutInflater.inflate(R.layout.popup_choose, null);


                        LinearLayout linearLayout = popupView.findViewById(R.id.linear_layout);
                        linearLayout.setMinimumWidth(txtView.getWidth());
                        for (int i = 0; i < macros.size(); i++) {
                            TextView textView = new TextView(popupView.getContext());
                            textView.setMinHeight(0);
                            textView.setMinWidth(0);
                            textView.setText(macros.get(i));
                            textView.setTextSize(20);

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    txtView.setText(((TextView) v).getText().toString());
                                    popupWindow2[0].dismiss();
                                }
                            });

                            linearLayout.addView(textView, i);
                        }

                        popupWindow2[0] = new PopupWindow(
                                popupView,
                                txtView.getWidth(),
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                        popupWindow2[0].setFocusable(true);
                        popupWindow2[0].update();
                        popupWindow2[0].showAsDropDown(v);
                    }
                });

                popupView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setText(((EditText) popupWindow.getContentView().findViewById(R.id.label)).getText().toString());
                        args.setFileName(fileName.get(txtView.getText().toString()));
                        args.setOnce(((CheckBox) popupView.findViewById(R.id.checkbox)).isChecked());
                        popupWindow.dismiss();
                    }
                });

                popupView.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.removeView(btn);
                        popupWindow.dismiss();
                    }
                });
            }

            return true;
        }
    }
}
