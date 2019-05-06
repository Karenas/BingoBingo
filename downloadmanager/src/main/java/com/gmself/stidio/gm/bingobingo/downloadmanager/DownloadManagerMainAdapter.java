package com.gmself.stidio.gm.bingobingo.downloadmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gmself.stidio.gm.bingobingo.downloadmanager.entity.DownloadManagerItemEntity;
import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadTask;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerDownload;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by guomeng on 5/1.
 */

public class DownloadManagerMainAdapter extends RecyclerView.Adapter<DownloadManagerMainAdapter.MyViewHolder> implements View.OnClickListener,View.OnLongClickListener {
    private LinkedList<DownloadTask> executePool;
    private LinkedList<DownloadTask> waitPool;

    private int count = 0;

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public DownloadManagerMainAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmList(LinkedList<DownloadTask> executePool, LinkedList<DownloadTask> waitPool) {
        this.executePool = executePool;
        this.waitPool = waitPool;
        count = this.executePool.size() + this.waitPool.size();
        Logger.log(Logger.Type.DEBUG, "task adapterSetList   executePool = "+ this.executePool.hashCode() +" | waitPool= "+ this.waitPool.hashCode());
        Logger.log(Logger.Type.DEBUG, "task adapterSetList   executePool size = "+ this.executePool.size() +" | waitPool size = "+ this.waitPool.size());
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        MyViewHolder holder;

        view = LayoutInflater.from(mContext).inflate(R.layout.adapter_downloadmanager_rv_item_main, parent,
                false);

        holder = new MyViewHolder(view, viewType);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Logger.log(Logger.Type.DEBUG, "task adapterSetList   onBindViewHolder in");
        holder.itemView.setTag(position);
        DownloadTask item;
        if (position < executePool.size()){
            int i = executePool.size() - 1 -position;
            item = executePool.get(i);
            Logger.log(Logger.Type.DEBUG, "task adapterSetList   onBindViewHolder in execute taskName="+item.getTaskName());

        }else {
            int i = (count - executePool.size()) - 1  - (position - executePool.size());
            item = waitPool.get(i);
            Logger.log(Logger.Type.DEBUG, "task adapterSetList   onBindViewHolder in wait taskName="+item.getTaskName());

        }

        if (holder.task_name_tv !=null )
            holder.task_name_tv.setText(item.getTaskName());
        if (holder.task_pb !=null )
            holder.task_pb.setProgress((int) item.getLeash().getPercent());
        if (holder.speed_tv !=null )
            holder.speed_tv.setText(String.valueOf(item.getLeash().getStatus()));
        item.getLeash().setListener(new OKHttpListenerDownload() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onProgress(float percent, long completionSize) {
                if (holder!=null && holder.task_pb != null){
                    holder.task_pb.setProgress((int) percent);
                }
            }

            @Override
            public void onError(BingoNetWorkException resultCode) {

            }

            @Override
            public void onFinally() {

            }
        });
    }


    @Override
    public int getItemCount() {
        return count;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView task_name_tv;
        TextView speed_tv;
        ProgressBar task_pb;

        public MyViewHolder(View view, int viewType) {
            super(view);
            task_name_tv = view.findViewById(R.id.task_name_tv);
            speed_tv = view.findViewById(R.id.speed_tv);
            task_pb = view.findViewById(R.id.task_pb);
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemLongClick(view, (int) view.getTag());
        }
        return true;
    }
}
