package com.kevin.delegationadapter.sample.multidataandtype.binding.adapter;

import android.databinding.ViewDataBinding;

import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.BR;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.multidataandtype.bean.BlankParameters;

/**
 * BlankAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-11 17:42:49
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class BlankAdapterDelegate extends BindingAdapterDelegate<BlankParameters> {

    @Override
    public int getLayoutRes() {
        return R.layout.layout_item_blank;
    }

    @Override
    public void setVariable(ViewDataBinding binding, BlankParameters item, int position) {
        binding.setVariable(BR.params, item);
    }

}
