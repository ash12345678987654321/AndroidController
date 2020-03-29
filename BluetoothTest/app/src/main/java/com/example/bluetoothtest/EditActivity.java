package com.example.bluetoothtest;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bluetoothtest.controllerData.Btn;
import com.example.bluetoothtest.controllerData.Dpad;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EditActivity extends AppCompatActivity {
    private static final Set<String> keycodes=new HashSet<String>();


    private RelativeLayout layout;

    private View decorView;

    private LinearLayout add_pane;
    private LinearLayout edit_pane;

    private PopupWindow popupWindow; //so we can access it easily

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        layout=findViewById(R.id.layout_controller_tag);
        add_pane=findViewById(R.id.add_pane);
        edit_pane=findViewById(R.id.edit_pane);

        String preset=getIntent().getStringExtra("preset");
        try{
            File file=new File(getFilesDir()+"/"+preset);
            Scanner scanner=new Scanner(file);

            while (scanner.hasNext()){
                switch(scanner.next()){
                    case "Btn":
                        Btn(scanner.next(),scanner.next(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                        break;

                    case "Dpad":
                        Dpad(scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                        break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"File corrupted >.<",Toast.LENGTH_SHORT).show();
            finish();
        }

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

        //add all valid keycodes
        keycodes.addAll(Arrays.asList(new String[]{
                "!", "\'", "#", "$", "%", "&", "\"", "(",
                ")", "*", "+", ",", "-", ".", "/", "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`",
                "a", "b", "c", "d", "e","f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
                "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~",
                "up", "down", "left", "right" ,
                "add", "subtract", "multiply","divide",
                "home", "end", "insert", "delete", "pagedown", "pageup", "pgdn","pgup", "printscreen", "prntscrn",
                "fn", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10",
                "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20",
                "f21", "f22", "f23", "f24",
                "num0", "num1", "num2", "num3", "num4", "num5", "num6", "num7", "num8", "num9", "numlock",
                "prtsc", "prtscr", "return", "scrolllock",
                "alt", "backspace", "capslock","ctrl", "enter", "esc", "shift", "space",  "tab", "volumedown", "volumemute", "volumeup", "win",
                "m1","m2","m3"
        }));
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
    public void onBackPressed(){
        Log.d("ZZZ","Back is pressed");

        String preset=getIntent().getStringExtra("preset");
        try{
            File file=new File(getFilesDir()+"/"+preset);
            PrintWriter pw=new PrintWriter(file);

            for (int x=0;x<layout.getChildCount();x++) {
                Button v = (Button) layout.getChildAt(x);
                Object args = v.getTag();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                if (args instanceof Btn) {
                    pw.write("Btn " +v.getText().toString()+" "+((Btn) args).getOutput()  + " " + v.getHeight() + " " + v.getWidth() + " " + layoutParams.topMargin + " " + layoutParams.leftMargin + "\n");
                } else if (args instanceof Dpad) {
                    pw.write("Dpad " + ((Dpad) args).getDir() + " " + v.getHeight() + " " + layoutParams.topMargin + " " + layoutParams.leftMargin + "\n");
                }
            }

            pw.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"File corrupted >.<",Toast.LENGTH_SHORT).show();
        }

        super.onBackPressed();
    }


    //editing interface
    public void expand_add_pane(View view){
        ObjectAnimator animation,animation2;

        if (view.getTranslationX()!=0){
            animation = ObjectAnimator.ofFloat(add_pane, "translationX", 0);
            animation2 = ObjectAnimator.ofFloat(view, "translationX", 0);
        }
        else{
            animation = ObjectAnimator.ofFloat(add_pane, "translationX", add_pane.getWidth());
            animation2 = ObjectAnimator.ofFloat(view, "translationX", add_pane.getWidth());
        }

        animation.setDuration(300);
        animation.start();
        animation2.setDuration(300);
        animation2.start();
    }

    public void add_btn(View view){
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.add_btn, null);


        popupWindow = new PopupWindow(
                popupView,
                (int)(Resources.getSystem().getDisplayMetrics().widthPixels/3),
                (int)(Resources.getSystem().getDisplayMetrics().heightPixels/2.5));

        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAsDropDown(view,50,-100);
    }

    public void add_btn_cfm(View view){
        String label=((EditText)popupWindow.getContentView().findViewById(R.id.label)).getText().toString();
        String key=((EditText)popupWindow.getContentView().findViewById(R.id.key)).getText().toString();

        if (!keycodes.contains(key)){
            Toast.makeText(this,key+" is not a valid key code",Toast.LENGTH_SHORT).show();
            return;
        }

        Btn(label,key,300,300,0,0);
        popupWindow.dismiss();
    }

    public void add_dpad(View view){
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.add_dpad, null);


        popupWindow = new PopupWindow(
                popupView,
                (int)(Resources.getSystem().getDisplayMetrics().widthPixels/3),
                (int)(Resources.getSystem().getDisplayMetrics().heightPixels/1.6));

        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAsDropDown(view,50,-100);
    }

    public void add_dpad_cfm(View view){
        String up=((EditText)popupWindow.getContentView().findViewById(R.id.up)).getText().toString();
        String down=((EditText)popupWindow.getContentView().findViewById(R.id.down)).getText().toString();
        String left=((EditText)popupWindow.getContentView().findViewById(R.id.left)).getText().toString();
        String right=((EditText)popupWindow.getContentView().findViewById(R.id.right)).getText().toString();

        if (!keycodes.contains(up)){
            Toast.makeText(this,up+" is not a valid key code",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!keycodes.contains(down)){
            Toast.makeText(this,down+" is not a valid key code",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!keycodes.contains(left)){
            Toast.makeText(this,left+" is not a valid key code",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!keycodes.contains(right)){
            Toast.makeText(this,right+" is not a valid key code",Toast.LENGTH_SHORT).show();
            return;
        }

        Dpad(up,down,left,right,300,0,0);
        popupWindow.dismiss();
    }



    public void expand_edit_pane(View view){
        ObjectAnimator animation,animation2;

        if (view.getTranslationX()!=0){
            animation = ObjectAnimator.ofFloat(edit_pane, "translationX", 0);
            animation2 = ObjectAnimator.ofFloat(view, "translationX", 0);
        }
        else{
            animation = ObjectAnimator.ofFloat(edit_pane, "translationX", -edit_pane.getWidth());
            animation2 = ObjectAnimator.ofFloat(view, "translationX", -edit_pane.getWidth());
        }

        animation.setDuration(300);
        animation.start();
        animation2.setDuration(300);
        animation2.start();
    }



    //controller setups (adding them programmically)
    private void Btn (String label, String output, int height, int width, int marginTop, int marginLeft){
        Button btn=new Button(this);

        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);
        btn.setText(label);

        btn.setTag(new Btn(output));

        btn.setBackgroundResource(R.drawable.button_up);

        layout.addView(btn);

        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin=marginLeft;
        layoutParams.topMargin=marginTop;
        btn.setLayoutParams(layoutParams);


        final ScaleGestureDetector scaleDetector=new ScaleGestureDetector(this,new ScaleListener(btn,height,width));
        final double prev_pos[]=new double[2];
        final int active_pointer[]={-1};
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleDetector.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) v.getLayoutParams();

                    int index=event.findPointerIndex(event.getPointerId(0));
                    double x=layoutParams.leftMargin+event.getX(index),y=layoutParams.topMargin+event.getY(index);

                    Log.d("ZZZ","active poitner: "+index+";pos: "+x+" "+y);

                    if (event.getPointerId(0)==active_pointer[0]){
                        layoutParams.leftMargin+=x-prev_pos[0];
                        layoutParams.topMargin+=y-prev_pos[1];
                    }

                    active_pointer[0]=event.getPointerId(0);
                    prev_pos[0]=x;
                    prev_pos[1]=y;

                    //make sure view stays inside
                    layoutParams.leftMargin=Math.max(0,Math.min(Resources.getSystem().getDisplayMetrics().widthPixels-v.getWidth(),layoutParams.leftMargin));
                    layoutParams.topMargin=Math.max(0,Math.min(Resources.getSystem().getDisplayMetrics().heightPixels-v.getHeight(),layoutParams.topMargin));
                    v.setLayoutParams(layoutParams);
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){
                    active_pointer[0]=-1;
                }
                return true;
            }
        });
    }


    private void Dpad(String up,String down,String left,String right,int diameter,int marginTop,int marginLeft){
        Button btn=new Button(this);

        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setTag(new Dpad(up,down,left,right));

        btn.setBackgroundResource(R.drawable.dpad);

        layout.addView(btn);

        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin=marginLeft;
        layoutParams.topMargin=marginTop;
        btn.setLayoutParams(layoutParams);

        final ScaleGestureDetector scaleDetector=new ScaleGestureDetector(this,new ScaleListener(btn,diameter,diameter));
        final double prev_pos[]=new double[2];
        final int active_pointer[]={-1};
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleDetector.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) v.getLayoutParams();

                    int index=event.findPointerIndex(event.getPointerId(0));
                    double x=layoutParams.leftMargin+event.getX(index),y=layoutParams.topMargin+event.getY(index);

                    Log.d("ZZZ","active poitner: "+index+";pos: "+x+" "+y);

                    if (event.getPointerId(0)==active_pointer[0]){
                        layoutParams.leftMargin+=x-prev_pos[0];
                        layoutParams.topMargin+=y-prev_pos[1];
                    }

                    active_pointer[0]=event.getPointerId(0);
                    prev_pos[0]=x;
                    prev_pos[1]=y;

                    //make sure view stays inside
                    layoutParams.leftMargin=Math.max(0,Math.min(Resources.getSystem().getDisplayMetrics().widthPixels-v.getWidth(),layoutParams.leftMargin));
                    layoutParams.topMargin=Math.max(0,Math.min(Resources.getSystem().getDisplayMetrics().heightPixels-v.getHeight(),layoutParams.topMargin));
                    v.setLayoutParams(layoutParams);
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){
                    active_pointer[0]=-1;
                }
                return true;
            }
        });
    }

    //magic code to allow for resizing stuff
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private double scaleFactor=1;
        private int height,width;
        private Button btn;

        public ScaleListener(Button btn,int height,int width){
            this.btn=btn;
            this.height=height;
            this.width=width;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) btn.getLayoutParams();
            //Log.d("ZZZ","extra space on x-axis: "+layoutParams.leftMargin+" "+(Resources.getSystem().getDisplayMetrics().widthPixels-layoutParams.leftMargin-btn.getWidth()));
            //Log.d("ZZZ","extra space on x-axis: "+layoutParams.topMargin+" "+(Resources.getSystem().getDisplayMetrics().heightPixels-layoutParams.topMargin-btn.getHeight()));

            // Don't let the object get too large for the curr screen size
            scaleFactor*=Math.min(
                    Math.min(
                            (double)Math.min(layoutParams.leftMargin,Resources.getSystem().getDisplayMetrics().widthPixels-layoutParams.leftMargin-btn.getWidth())/btn.getWidth()*2+1,//test for the x-axis maximum
                            (double)Math.min(layoutParams.topMargin,Resources.getSystem().getDisplayMetrics().heightPixels-layoutParams.topMargin-btn.getHeight())/btn.getHeight()*2+1 //test for the y-axis maximum
                    ),
                    detector.getScaleFactor()
            );

            //Log.d("ZZZ","Current scale: "+scaleFactor);
            btn.setHeight((int)(height*scaleFactor));
            btn.setWidth((int)(width*scaleFactor));
            return true;
        }
    }
}
