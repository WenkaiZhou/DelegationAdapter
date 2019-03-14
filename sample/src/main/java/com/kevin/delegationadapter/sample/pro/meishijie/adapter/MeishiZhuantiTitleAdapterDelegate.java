package com.kevin.delegationadapter.sample.pro.meishijie.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.pro.meishijie.bean.Meishi;

/**
 * MeishiSancanAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-13 12:34:00
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MeishiZhuantiTitleAdapterDelegate extends BindingAdapterDelegate<Meishi.Zhuanti> {

    @Override
    public int getLayoutRes() {
        return R.layout.item_meishi_zhuanti_title;
    }

    @Override
    public void setVariable(@NonNull ViewDataBinding binding, Meishi.Zhuanti item, int position) {
        binding.setVariable(BR.model, item);
        binding.setVariable(BR.view, this);
    }

    /**
     * "更多"被点击的监听回调
     *
     * @param view
     */
    public void onMoreClick(View view) {
        Toast.makeText(view.getContext(), "更多", Toast.LENGTH_SHORT).show();
    }

}
