package com.example.bluetoothtest.activities;

import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.dataStructures.Vector;
import com.example.bluetoothtest.controllerData.Command;
import com.example.bluetoothtest.controllerData.Delay;
import com.example.bluetoothtest.controllerData.KeyStroke;
import com.example.bluetoothtest.controllerData.Loop;
import com.example.bluetoothtest.controllerData.Text;

import java.util.HashMap;
import java.util.UUID;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>{

    private Vector<Command> macro;
    private int selected=-1;
    private HashMap<String, Vector<Command>> table=new HashMap<>(); //this is funny lookup table to quickly update stuff of same type

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView icon1,icon2;
        View rowView;

        public CustomViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;
            mTitle = itemView.findViewById(R.id.txtTitle);
            icon1 = itemView.findViewById(R.id.icon1);
            icon2 = itemView.findViewById(R.id.icon2);

            final GestureDetector doubleDetector = new GestureDetector(itemView.getContext(), new GestureListener(this));
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    doubleDetector.onTouchEvent(event);
                    return false; //dont consume the event
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(getAdapterPosition());
                }
            });

        }
    }

    public RecyclerViewAdapter(Vector<Command> macro) {
        this.macro = macro;
        selected=-1;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (macro.get(position) instanceof KeyStroke){
            holder.icon1.setImageResource(R.drawable.ic_keyboard);

            if (macro.get(position).isStart()) holder.icon2.setImageResource(R.drawable.ic_keydown);
            else holder.icon2.setImageResource(R.drawable.ic_keyup);
        }
        else if (macro.get(position) instanceof Text){
            holder.icon1.setImageResource(R.drawable.ic_text);

            holder.icon2.setImageResource(0);
        }
        else if (macro.get(position) instanceof Delay){
            holder.icon1.setImageResource(R.drawable.ic_stopwatch);

            holder.icon2.setImageResource(0);
        }
        else{
            holder.icon1.setImageResource(R.drawable.ic_loop);

            holder.icon2.setImageResource(0);
        }

        holder.mTitle.setText(macro.get(position).getPreview());

        if (position==selected) holder.rowView.setBackgroundColor(Color.parseColor("#272727"));
        else holder.rowView.setBackgroundColor(Color.parseColor("#00000000"));

        if (macro.get(position).getChildren().isEmpty()) holder.mTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        else holder.mTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_dropdown,0); //this has collapsed shit
    }


    @Override
    public int getItemCount() {
        return macro.size();
    }

    public void add_keystroke(){
        int start;
        if (selected==-1) start=macro.size();
        else start=selected+1;

        String id=randomIdName();

        macro.add(start,new KeyStroke("",true,false,id));
        macro.add(start+1,new KeyStroke("",false,true,id));

        notifyItemRangeInserted(start,2);
    }

    public void add_text(){
        int start;
        if (selected==-1) start=macro.size();
        else start=selected+1;

        String id=randomIdName();

        macro.add(start,new Text("",false,false,id));

        notifyItemInserted(start);
    }

    public void add_delay(){
        int start;
        if (selected==-1) start=macro.size();
        else start=selected+1;

        String id=randomIdName();

        macro.add(start,new Delay(0,false,false,id));

        notifyItemInserted(start);
    }

    public void add_loop(){
        int start;
        if (selected==-1) start=macro.size();
        else start=selected+1;

        String id=randomIdName();

        macro.add(start,new Loop(1,true,false,id));
        macro.add(start+1,new Loop(1,false,true,id));

        notifyItemRangeInserted(start,2);
    }

    public void up(){
        if (selected==-1 || selected==0) return;

        if (macro.get(selected).isSwappable() || macro.get(selected-1).isSwappable()) {
            selected--;
            macro.swap(selected, selected + 1);
            notifyItemChanged(selected);
            notifyItemChanged(selected + 1);
        }
    }

    public void down(){
        if (selected==-1 || selected==macro.size()-1) return;

        if (macro.get(selected).isSwappable() || macro.get(selected+1).isSwappable()) {
            selected++;
            macro.swap(selected, selected - 1);
            notifyItemChanged(selected);
            notifyItemChanged(selected - 1);
        }
    }

    public void setSelected(int index){
        Log.d("ZZZ","selected: "+index);
        //fancy code for swapping 2 numbers
        index^=selected;
        selected^=index;
        index^=selected;

        if (index!=-1) notifyItemChanged(index);
        notifyItemChanged(selected);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private CustomViewHolder holder;

        private GestureListener(CustomViewHolder holder) {
            this.holder = holder;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            int index=holder.getAdapterPosition();
            if (macro.get(index) instanceof Loop && macro.get(index).isStart()){
                if (macro.get(index).getChildren().isEmpty()){ //collapse this
                    int start=index+1,end=index+1;

                    while (!macro.get(end).getId().equals(macro.get(index).getId())) end++;
                    end++; //exclusive

                    macro.get(index).setChildren(macro.splice(start,end));

                    notifyItemRangeRemoved(start,end-start);
                    notifyItemChanged(index);
                }
                else{ //uncollapse this
                    Log.d("ZZZ",macro.get(index).getChildren().size()+"");

                    macro.addAll(index+1,macro.get(index).getChildren());
                    notifyItemRangeInserted(index+1,macro.get(index).getChildren().size());

                    macro.get(index).getChildren().clear();
                    notifyItemChanged(index);
                }
            }
            return true;
        }

    }

    public Vector<Command> getMacros(){
        return macro;
    }

    private String randomIdName(){
        while (true) {
            String id = UUID.randomUUID().toString();
            if (!table.containsKey(id)) return id;
        }
    }
}
