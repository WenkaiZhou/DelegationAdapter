package com.kevin.delegationadapter.sample.multitype.news.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.News;

/**
 * MorePicAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-26 17:06:15
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MorePicAdapterDelegate extends AdapterDelegate<News, MorePicAdapterDelegate.MorePicViewHolder> {

    @Override
    public boolean isForViewType(News news, int position) {
        // 我能处理多张图片
        return news.type == 2;
    }

    @NonNull
    @Override
    public MorePicViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_more_pic, parent, false);
        MorePicViewHolder holder = new MorePicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MorePicViewHolder holder, int position, News news) {
        holder.tvContent.setText(news.content);
        holder.tvSource.setText(news.source);
        holder.tvTime.setText(news.time);
        holder.tvCount.setText(news.count + " 图");
        Glide.with(holder.itemView.getContext()).load(news.imgUrls.get(0)).into(holder.ivPic);
    }

    static class MorePicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvContent;
        TextView tvCount;
        TextView tvSource;
        TextView tvTime;

        public MorePicViewHolder(View view) {
            super(view);
            ivPic = view.findViewById(R.id.iv_pic);
            tvContent = view.findViewById(R.id.tv_content);
            tvCount = view.findViewById(R.id.tv_count);
            tvSource = view.findViewById(R.id.tv_source);
            tvTime = view.findViewById(R.id.tv_time);
        }
    }

}
