package com.kevin.delegationadapter.sample.multidataandtype.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.sample.R;

/**
 * FloatAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-28 11:23:23
 *         Major Function：<b>Float类型数据委托Adapter</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class FloatAdapterDelegate extends AdapterDelegate<Float, FloatAdapterDelegate.ViewHolder> {

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindViewHolder(FloatAdapterDelegate.ViewHolder holder, int position, Float item) {
        holder.tvContent.setText(String.valueOf(item));
        holder.tvDesc.setText("Float类型委托Adapter");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvContent;
        public TextView tvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
