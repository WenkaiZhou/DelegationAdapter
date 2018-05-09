package com.kevin.delegationadapter.sample.multitype.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.News;

/**
 * VideoAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-26 17:06:15
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class VideoAdapterDelegate extends AdapterDelegate<News, VideoAdapterDelegate.VideoViewHolder> {

    @Override
    protected boolean isForViewType(News news, int position) {
        // 我能处理视频类型
        return news.type == 3;
    }

    @Override
    protected VideoViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_video, parent, false);
        VideoViewHolder holder = new VideoViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindViewHolder(VideoViewHolder holder, int position, News news) {
        holder.tvContent.setText(news.content);
        holder.tvSource.setText(news.source);
        holder.tvTime.setText(news.time);
        holder.tvDuration.setText(news.duration);
        Glide.with(holder.itemView.getContext()).load(news.imgUrls.get(0)).into(holder.ivPic);
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvContent;
        TextView tvDuration;
        TextView tvSource;
        TextView tvTime;

        public VideoViewHolder(View view) {
            super(view);
            ivPic = view.findViewById(R.id.iv_pic);
            tvContent = view.findViewById(R.id.tv_content);
            tvDuration = view.findViewById(R.id.tv_duration);
            tvSource = view.findViewById(R.id.tv_source);
            tvTime = view.findViewById(R.id.tv_time);
        }
    }

}
