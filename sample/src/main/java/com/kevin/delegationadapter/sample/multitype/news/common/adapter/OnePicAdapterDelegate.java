package com.kevin.delegationadapter.sample.multitype.news.common.adapter;

import android.support.annotation.NonNull;
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
 * OnePicAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-26 17:06:15
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class OnePicAdapterDelegate extends AdapterDelegate<News, OnePicAdapterDelegate.OnePicViewHolder> {

    @Override
    public boolean isForViewType(News news, int position) {
        // 我能处理一张图片
        return news.type == 0;
    }

    @NonNull
    @Override
    public OnePicViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_one_pic, parent, false);
        OnePicViewHolder holder = new OnePicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnePicViewHolder holder, int position, News news) {
        holder.tvContent.setText(news.content);
        holder.tvSource.setText(news.source);
        holder.tvTime.setText(news.time);
        Glide.with(holder.itemView.getContext()).load(news.imgUrls.get(0)).into(holder.ivPic);
    }

    static class OnePicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvContent;
        TextView tvSource;
        TextView tvTime;

        public OnePicViewHolder(View view) {
            super(view);
            ivPic = view.findViewById(R.id.iv_pic);
            tvContent = view.findViewById(R.id.tv_content);
            tvSource = view.findViewById(R.id.tv_source);
            tvTime = view.findViewById(R.id.tv_time);
        }
    }
}
