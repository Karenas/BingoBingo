package com.gmself.studio.mg.bingobingo.overall.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmself.studio.mg.bingobingo.overall.R;
import com.gmself.studio.mg.bingobingo.overall.entity.EntityHomeRvItem;

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
    public int getItemViewType(int position) {
        return mList.get(position).getHolderType().ordinal();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        MyViewHolder holder;

        if (viewType == EntityHomeRvItem.TYPE.VIDEO.ordinal()){
            view= LayoutInflater.from(mContext).inflate(R.layout.adapter_overall_rv_item_video_home, parent,
                    false);

        }else {
            view= LayoutInflater.from(mContext).inflate(R.layout.adapter_overall_rv_item_home, parent,
                    false);
        }

        holder = new MyViewHolder(view, viewType);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.itemView.setTag(position);

        EntityHomeRvItem item = mList.get(position);

        switch (item.getHolderType()){
            case VIDEO:
                createVideoView(holder, item);
                break;
            default:
                createNormalView(holder, item);
                break;
        }

    }

    private void createNormalView(MyViewHolder holder, EntityHomeRvItem item){
        holder.name_normal.setText(item.getName());

        holder.name_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击到了文字", Toast.LENGTH_LONG).show();
            }
        });

        holder.itemView.setBackgroundColor(item.getBackGroundColor());
        holder.itemView.setMinimumHeight(item.getHeight());
    }

    private void createVideoView(MyViewHolder holder, EntityHomeRvItem item){

//        String videoPath = "/storage/emulated/0/DCIM/Camera/VID_20190411_153300.mp4";
//        String videoPath = "/storage/emulated/0/tencent/MicroMsg/WeiXin/1555238565811.mp4";
//        String videoPath = "/storage/emulated/0/tencent/MicroMsg/Download/Night2.avi";
//        String videoPath = "http://www.jmzsjy.com/UploadFile/%E5%BE%AE%E8%AF%BE/%E5%9C%B0%E6%96%B9%E9%A3%8E%E5%91%B3%E5%B0%8F%E5%90%83%E2%80%94%E2%80%94%E5%AE%AB%E5%BB%B7%E9%A6%99%E9%85%A5%E7%89%9B%E8%82%89%E9%A5%BC.mp4";

//        holder.cv_smallVideoView_video.setVideoPath(videoPath);
//        holder.cv_smallVideoView_video.startVideo();


//            Glide.with(mContext).load("file:///android_asset/Cloudy.gif").asGif().diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.iv_video);

    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        EntityHomeRvItem.TYPE type;

        TextView name_normal;
//        CV_smallVideoView cv_smallVideoView_video;
        ImageView iv_video;

        public MyViewHolder(View view, int viewType)
        {
            super(view);
            type = EntityHomeRvItem.TYPE.values()[viewType];
            switch (type){
                case VIDEO:
//                    cv_smallVideoView_video = view.findViewById(R.id.cv_video);
                    iv_video = view.findViewById(R.id.iv_video);
                    break;
                default:
                    name_normal = view.findViewById(R.id.overall_home_rv_item_name_tv);
                    break;
            }
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