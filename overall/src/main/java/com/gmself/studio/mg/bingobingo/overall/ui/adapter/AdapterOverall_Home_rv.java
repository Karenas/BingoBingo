package com.gmself.studio.mg.bingobingo.overall.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmself.studio.mg.bingobingo.overall.R;
import com.gmself.studio.mg.bingobingo.overall.entity.EntityHomeRvItem;
import com.gmself.studio.mg.bingobingo.overall.ui.customView.LinearLayout_homeRv;
import com.gmself.studio.mg.bingobingo.overall.ui.listener.OnViewGestureListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterOverall_Home_rv extends RecyclerView.Adapter<AdapterOverall_Home_rv.MyViewHolder> implements View.OnClickListener,View.OnLongClickListener
{
    private List<EntityHomeRvItem> mList;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
//    private LinearLayout_homeRv[] customLLs;

    public AdapterOverall_Home_rv(Context mContext, List<EntityHomeRvItem> list){
        this.mContext=mContext;
        this.mList=list;
//        customLLs = new LinearLayout_homeRv[list.size()];
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.mOnItemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_overall_rv_item_home, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.itemView.setTag(position);

        EntityHomeRvItem item = mList.get(position);
        holder.name.setText(item.getName());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击到了文字", Toast.LENGTH_LONG).show();
            }
        });

        holder.itemView.setBackgroundColor(item.getBackGroundColor());
        holder.itemView.setMinimumHeight(item.getHeight());
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
//        LinearLayout_homeRv layout;

        public MyViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.overall_home_rv_item_name_tv);
//            layout = view.findViewById(R.id.overall_home_rv_item_ll);
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemLongClick(view,(int)view.getTag());
        }
        return true;
    }

}