package com.kevin.delegationadapter.sample.delegate;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import com.kevin.delegationadapter.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.BR;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.ImageBean;

/**
 * ImageViewViewHolder
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-04 15:56:57
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ImageViewViewHolder extends BindingAdapterDelegate<ImageBean> {

    @Override
    public int getLayoutRes() {
        return R.layout.item_image;
    }

    @Override
    public void setVariable(ViewDataBinding binding, ImageBean data) {
        binding.setVariable(BR.model, data);
    }

    @Override
    public void onItemClick(View view, ImageBean data, int position) {
        Toast.makeText(view.getContext(), "click: position = " + position + ", data.text = " + data.img, Toast.LENGTH_SHORT).show();
    }
}