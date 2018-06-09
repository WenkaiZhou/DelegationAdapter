package com.kevin.delegationadapter.sample.multitype.news.common.adapter;

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
 * ThreePicAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-26 17:06:15
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ThreePicAdapterDelegate extends AdapterDelegate<News, ThreePicAdapterDelegate.ThreePicViewHolder> {

    @Override
    protected boolean isForViewType(News item, int position) {
        // 我能处理三张图片
        return item.type == 1;
    }

    @Override
    protected ThreePicViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_three_pic, parent, false);
        ThreePicViewHolder holder = new ThreePicViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindViewHolder(ThreePicViewHolder holder, int position, News news) {
        holder.tvContent.setText(news.content);
        holder.tvSource.setText(news.source);
        holder.tvTime.setText(news.time);
        Glide.with(holder.itemView.getContext()).load(news.imgUrls.get(0)).into(holder.ivPic1);
        Glide.with(holder.itemView.getContext()).load(news.imgUrls.get(1)).into(holder.ivPic2);
        Glide.with(holder.itemView.getContext()).load(news.imgUrls.get(2)).into(holder.ivPic3);
    }

    static class ThreePicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic1;
        ImageView ivPic2;
        ImageView ivPic3;
        TextView tvContent;
        TextView tvSource;
        TextView tvTime;

        public ThreePicViewHolder(View view) {
            super(view);
            ivPic1 = view.findViewById(R.id.iv_pic1);
            ivPic2 = view.findViewById(R.id.iv_pic2);
            ivPic3 = view.findViewById(R.id.iv_pic3);
            tvContent = view.findViewById(R.id.tv_content);
            tvSource = view.findViewById(R.id.tv_source);
            tvTime = view.findViewById(R.id.tv_time);
        }
    }

}
