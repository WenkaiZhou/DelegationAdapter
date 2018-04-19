package com.kevin.delegationadapter.sample.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.sample.BuildConfig;
import com.kevin.delegationadapter.sample.R;
import com.kevin.jsontool.JsonTool;

/**
 * FallbackAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-18 22:10:21
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class FallbackAdapterDelegate extends AdapterDelegate<Object, FallbackAdapterDelegate.ViewHolder> {

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fallback, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, Object item) {

        String content = "No delegate found for "
                + item
                + " at position = "
                + position
                + " for viewType = "
                + holder.getItemViewType()
                + ", item detail ==> "
                + JsonTool.toJson(item);

        holder.tvContent.setText(content);

//        if (isRelease) {
//            holder.tvContent.setVisibility(View.GONE);
//        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
