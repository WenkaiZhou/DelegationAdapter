package com.kevin.delegationadapter.sample.delegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.TextBean;

import java.util.List;


/**
 * LeftTextViewHolder
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-04 15:56:04
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class LeftTextAdapterDelegate extends AdapterDelegate<TextBean, LeftTextAdapterDelegate.TextViewHolder> {

    @Override
    protected boolean isForViewType(@NonNull TextBean item, int position) {
        return item.isLeft;
    }

    @Override
    protected TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_left, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull TextBean item, int position, @NonNull TextViewHolder holder, @NonNull List<Object> payloads) {
        holder.text.setText(item.text);
    }


    static class TextViewHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public TextViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}