package com.kevin.delegationadapter.sample.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.delegationadapter.extras.ClickableAdapterDelegate;
import com.kevin.delegationadapter.sample.R;

/**
 * HomeAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-27 16:16:40
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class HomeAdapterDelegate extends ClickableAdapterDelegate<String, HomeAdapterDelegate.ViewHolder> {

    private HomeActivity mActivity;

    public HomeAdapterDelegate(HomeActivity activity) {
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position, final String item) {
        super.onBindViewHolder(holder, position, item);
        holder.tvContent.setText(item);
    }

    @Override
    public void onItemClick(@NonNull View view, String item, int position) {
        mActivity.onItemClick(view, position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
