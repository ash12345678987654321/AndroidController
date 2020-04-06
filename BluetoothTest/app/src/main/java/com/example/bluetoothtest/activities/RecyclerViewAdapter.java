package com.example.bluetoothtest.activities;

import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.controllerData.Command;
import com.example.bluetoothtest.controllerData.Delay;
import com.example.bluetoothtest.controllerData.KeyStroke;
import com.example.bluetoothtest.controllerData.Loop;
import com.example.bluetoothtest.controllerData.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Command> macro;
    private int selected=-1;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView icon1,icon2;
        View rowView;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;
            mTitle = itemView.findViewById(R.id.txtTitle);
            icon1 = itemView.findViewById(R.id.icon1);
            icon2 = itemView.findViewById(R.id.icon2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(getAdapterPosition());
                }
            });
        }
    }

    public RecyclerViewAdapter(ArrayList<Command> macro) {
        this.macro = macro;
        selected=-1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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
    }


    @Override
    public int getItemCount() {
        return macro.size();
    }

    public void add_keystroke(){
        int start;
        if (selected==-1) start=macro.size();
        else start=selected+1;

        String id= UUID.randomUUID().toString();

        macro.add(start,new KeyStroke("",true,false,id));
        macro.add(start+1,new KeyStroke("",false,true,id));

        notifyItemRangeInserted(start,2);
    }

    public void add_text(){
        int start;
        if (selected==-1) start=macro.size();
        else start=selected+1;

        String id=UUID.randomUUID().toString();

        macro.add(start,new Text("",false,false,id));

        notifyItemInserted(start);
    }

    public void add_delay(){
        int start;
        if (selected==-1) start=macro.size();
        else start=selected+1;

        String id=UUID.randomUUID().toString();

        macro.add(start,new Delay(0,false,false,id));

        notifyItemInserted(start);
    }

    public void add_loop(){
        int start;
        if (selected==-1) start=macro.size();
        else start=selected+1;

        String id=UUID.randomUUID().toString();

        macro.add(start,new Loop(1,true,false,id));
        macro.add(start+1,new Loop(1,false,true,id));

        notifyItemRangeInserted(start,2);
    }

    public void up(){

    }

    public void down(){

    }

    public void setSelected(int index){
        //fancy code for swapping 2 numbers
        index^=selected;
        selected^=index;
        index^=selected;

        if (index!=-1) notifyItemChanged(index);
        notifyItemChanged(selected);
    }
}
