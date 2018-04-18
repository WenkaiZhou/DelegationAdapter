package com.kevin.delegationadapter.sample.delegate;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import com.kevin.delegationadapter.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.BR;
import com.kevin.delegationadapter.sample.bean.TextBean;


/**
 * RightTextViewHolder
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-04 15:56:04
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class RightTextAdapterDelegate extends BindingAdapterDelegate<TextBean> {

    @Override
    public int getLayoutRes() {
        return R.layout.item_text_right;
    }

    @Override
    public void setVariable(ViewDataBinding binding, TextBean data) {
        binding.setVariable(BR.model, data);
    }

    @Override
    protected boolean isForViewType(@NonNull TextBean item, int position) {
        return !item.isLeft;
    }

}