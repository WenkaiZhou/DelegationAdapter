package com.kevin.delegationadapter.sample.pro.meishijie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.extras.load2.Load2DelegationAdapter;
import com.kevin.delegationadapter.sample.R;

/**
 * ChatLoadAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2019-03-09 12:55:19
 * Major Function：<b></b>
 * <p/>
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
public class MeishiLoad2AdapterDelegate extends AdapterDelegate<Load2DelegationAdapter.LoadFooter, MeishiLoad2AdapterDelegate.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_load_more_footer, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Load2DelegationAdapter.LoadFooter item) {
        if (item.getLoadState() == Load2DelegationAdapter.LOAD_STATE_LOADING) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.textView.setText("加载中...");
        } else if (item.getLoadState() == Load2DelegationAdapter.LOAD_TYPE_FAILED) {
            holder.progressBar.setVisibility(View.GONE);
            holder.textView.setText("加载失败，点击重试");
            // TODO 失败重试
        } else if (item.getLoadState() == Load2DelegationAdapter.LOAD_STATE_COMPLETED) {
            holder.progressBar.setVisibility(View.GONE);
            holder.textView.setText("没有更多数据");
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loading_progress);
            textView = itemView.findViewById(R.id.text_load_label);
        }
    }
}
