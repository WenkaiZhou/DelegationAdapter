package com.kevin.delegationadapter.sample.delegate;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import com.kevin.delegationadapter.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.BR;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.ImageTextBean;

/**
 * ImageAndTextAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-04 15:57:48
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ImageAndTextAdapterDelegate extends BindingAdapterDelegate<ImageTextBean> {

    @Override
    public int getLayoutRes() {
        return R.layout.item_image_text;
    }

    @Override
    public void setVariable(ViewDataBinding binding, ImageTextBean data, int position) {
        binding.setVariable(BR.model, data);
    }

    @Override
    protected boolean clickable(int position) {
        return position != 7;
    }

    @Override
    public void onItemClick(View view, ImageTextBean data, int position) {
        Toast.makeText(view.getContext(), "click: position = " + position + ", data.text = " + data.text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(View view, ImageTextBean data, int position) {
        Toast.makeText(view.getContext(), "longClick: position = " + position + ", data.text = " + data.text, Toast.LENGTH_SHORT).show();
        return true;
    }
}