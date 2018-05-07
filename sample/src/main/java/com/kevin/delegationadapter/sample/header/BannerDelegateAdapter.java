package com.kevin.delegationadapter.sample.header;

import android.databinding.ViewDataBinding;

import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.BR;
import com.kevin.loopview.internal.LoopData;

/**
 * BannerViewHolder
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-07 21:53:19
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class BannerDelegateAdapter extends BindingAdapterDelegate<LoopData> {

    @Override
    public int getLayoutRes() {
        return R.layout.item_banner;
    }

    @Override
    public void setVariable(ViewDataBinding binding, LoopData item, int position) {
        binding.setVariable(BR.model, item);
    }
}
