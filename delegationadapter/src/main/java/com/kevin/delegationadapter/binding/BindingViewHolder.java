/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */

package com.kevin.delegationadapter.binding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * BindingViewHolder
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-03 17:51:49
 *         Major Function：<b>Binding ViewHolder</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public final class BindingViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public BindingViewHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}
