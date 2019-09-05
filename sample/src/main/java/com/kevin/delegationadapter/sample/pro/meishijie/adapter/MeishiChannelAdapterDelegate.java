package com.kevin.delegationadapter.sample.pro.meishijie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.extras.span.SpanAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.pro.meishijie.bean.Meishi;

/**
 * MeishiChannelAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-13 11:07:06
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MeishiChannelAdapterDelegate extends SpanAdapterDelegate<Meishi.Channel, MeishiChannelAdapterDelegate.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meishi_channel, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, Meishi.Channel item) {
        super.onBindViewHolder(holder, position, item);
        Glide.with(holder.itemView.getContext()).load(item.img).into(holder.ivImg);
        holder.tvTitle.setText(item.title);
    }

    @Override
    public void onItemClick(@NonNull View view, Meishi.Channel item, int position) {
        Toast.makeText(view.getContext(), item.title, Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivImg;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            ivImg = view.findViewById(R.id.iv_pic);
        }
    }
}
