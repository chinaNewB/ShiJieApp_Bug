package com.example.shijieapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shijieapp.R;
import com.example.shijieapp.bean.LocalVideoItem;
import com.example.shijieapp.utils.NetUtils;
import com.example.shijieapp.utils.OnOneClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/5/3 20:49
 * author:renzhipeng
 *
 * @author 1278324384@qq.com
 */

public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.InnerHolder> {

    private List<LocalVideoItem> localVideos = new ArrayList<>();
    private NetUtils mNetUtils;
    private OnLocalItemClickListener listener = null;
    private OnLocalItemLongClickListener longListener = null;

    public LocalVideoAdapter() {
        mNetUtils = new NetUtils();
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_local_video, viewGroup, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder innerHolder, int i) {

        final LocalVideoItem localVideoItem = localVideos.get(i);

        if (localVideoItem != null) {
            innerHolder.setData(localVideoItem);
            innerHolder.itemView.setOnClickListener(new OnOneClickListener() {
                @Override
                public void oneClick(View v) {
                    listener.onLocalItemClick(localVideoItem);
                }
            });

            innerHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longListener.onLocalItemLongClick(localVideoItem);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return localVideos.size();
    }

    public void setData(List<LocalVideoItem> localVideos) {
        this.localVideos.clear();
        this.localVideos.addAll(localVideos);
        notifyDataSetChanged();
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView time;
        private TextView size;
        private String totaltime;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.video_image);
            title = itemView.findViewById(R.id.video_title);
            time = itemView.findViewById(R.id.video_time);
            size = itemView.findViewById(R.id.video_size);
        }

        public void setData(LocalVideoItem localVideoItem) {
            totaltime = mNetUtils.stringForTime(localVideoItem.getDuration(), false);
            title.setText(localVideoItem.getDisplayName());
            time.setText(totaltime);
            size.setText(mNetUtils.byteToMB(localVideoItem.getSize()));
            Glide.with(img.getContext()).load(localVideoItem.getImgPath()).into(img);
        }
    }


    public void setOnLocalItemClickListener(OnLocalItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnLocalItemClickListener {
        void onLocalItemClick(LocalVideoItem localVideos);
    }


    public interface OnLocalItemLongClickListener {
        void onLocalItemLongClick(LocalVideoItem localVideos);
    }

    public void setOnLocalItemLongClickListener(OnLocalItemLongClickListener listener) {
        this.longListener = listener;
    }
}
