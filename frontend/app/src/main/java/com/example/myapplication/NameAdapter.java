package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameViewHolder> {

    private Context mContext;
    private List<Nameitem> NameitemList;

    public NameAdapter(Context mContext, List<Nameitem> NameitemList)
    {
        this.NameitemList = NameitemList;
        this.mContext = mContext;
    }
    @Override
    public NameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_tablename, null);
        return new NameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NameViewHolder holder, int position) {
        Nameitem Nameitem = NameitemList.get(position);

        holder.mclassname.setText(Nameitem.getCourseID());
        holder.mcreated_at.setText(Nameitem.getcreated_at());
        holder.mtotal.setText(Nameitem.getTotal());
    }

    @Override
    public int getItemCount() {
        return NameitemList.size();
    }

    class NameViewHolder extends RecyclerView.ViewHolder {

        TextView mclassname, mcreated_at, mtotal;

        public NameViewHolder(View itemView) {
            super(itemView);

            mclassname = itemView.findViewById(R.id.mclassname);
            mcreated_at = itemView.findViewById(R.id.mcreated_at);
            mtotal = itemView.findViewById(R.id.mtotal);
        }
    }
}

