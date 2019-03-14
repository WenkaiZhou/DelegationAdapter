package com.kevin.delegationadapter.sample.fallback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.sample.R;

/**
 * FallbackAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-18 22:10:21
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class FallbackAdapterDelegate extends AdapterDelegate<Object, RecyclerView.ViewHolder> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        TextView view = new TextView(parent.getContext());
        view.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Object item) {

        String content = "No delegate found for "
                + item
                + " at position = "
                + position
                + " for viewType = "
                + holder.getItemViewType()
                + ", item detail ==> "
                + new Gson().toJson(item);

        ((TextView) holder.itemView).setText(content);

//        if (isRelease) {
//            holder.tvContent.setVisibility(View.GONE);
//        }
    }
}