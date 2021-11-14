package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mOne;
    private ArrayList<String> mMon;
    private ArrayList<String> mTue;
    private ArrayList<String> mWed;
    private ArrayList<String> mThu;
    private ArrayList<String> mFri;
    private Context mContext;

    //讓RecyclerViewAdapter能在Activity中呼叫
    public RecyclerViewAdapter(Context mContext, ArrayList<String> mOne, ArrayList<String> mMon, ArrayList<String> mTue, ArrayList<String> mWed, ArrayList<String> mThu, ArrayList<String> mFri)
    {
        this.mOne = mOne;
        this.mMon = mMon;
        this.mTue = mTue;
        this.mWed = mWed;
        this.mThu = mThu;
        this.mFri = mFri;
        this.mContext = mContext;
    }

    //將layout_schedule.xml中的TextView配值
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView one, monOne, tueOne, wedOne, thuOne, friOne;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            one = itemView.findViewById(R.id.one);
            monOne = itemView.findViewById(R.id.monOne);
            tueOne = itemView.findViewById(R.id.tueOne);
            wedOne = itemView.findViewById(R.id.wedOne);
            thuOne = itemView.findViewById(R.id.thuOne);
            friOne = itemView.findViewById(R.id.friOne);
        }
    }

    @NonNull
    @Override

    //建立holder 傳遞context
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext) .inflate(R.layout.layout_schedule, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override

    //做需要跟textview互動的功能
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.one.setText(mOne.get(position));

        holder.monOne.setText(mMon.get(position));
        if(!mMon.get(position).equals("")){
            holder.monOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Attendant", Toast.LENGTH_SHORT).show();
                }
            });
        }

        holder.tueOne.setText(mTue.get(position));
        if(!mTue.get(position).equals("")){
            holder.tueOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Attendant", Toast.LENGTH_SHORT).show();
                }
            });
        }

        holder.wedOne.setText(mWed.get(position));
        if(!mWed.get(position).equals("")){
            holder.wedOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Attendant", Toast.LENGTH_SHORT).show();
                }
            });
        }

        holder.thuOne.setText(mThu.get(position));
        if(!mThu.get(position).equals("")){
            holder.thuOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Attendant", Toast.LENGTH_SHORT).show();
                }
            });
        }

        holder.friOne.setText(mFri.get(position));
        if(!mFri.get(position).equals("")){
            holder.friOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Attendant", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mOne.size();
    }

}
