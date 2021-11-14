package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendAdapter extends RecyclerView.Adapter<AttendAdapter.AttendViewHolder> {

    private Context nContext;
    private List<attenditem> attenditemList;

    public AttendAdapter(Context nContext, List<attenditem> attenditemList)
    {
        this.attenditemList = attenditemList;
        this.nContext = nContext;
    }
    @Override
    public AttendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(nContext);
        View view = inflater.inflate(R.layout.activity_tablenotice, null);
        return new AttendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendViewHolder holder, int position) {
        attenditem attenditem = attenditemList.get(position);

        holder.nclassname.setText(attenditem.getCourseID());
        holder.ndate.setText(attenditem.getDate());
        holder.nrate.setText(attenditem.getRate());
    }

    @Override
    public int getItemCount() {
        return attenditemList.size();
    }

    class AttendViewHolder extends RecyclerView.ViewHolder {

        TextView nclassname, ndate, nrate;

        public AttendViewHolder(View itemView) {
            super(itemView);

            nclassname = itemView.findViewById(R.id.nclassname);
            ndate = itemView.findViewById(R.id.ndate);
            nrate = itemView.findViewById(R.id.nrate);
        }
    }
}
