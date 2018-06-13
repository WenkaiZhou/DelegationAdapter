package com.kevin.delegationadapter.sample.multidataandtype.binding.adapter;

import android.databinding.ViewDataBinding;

import com.android.databinding.library.baseAdapters.BR;
import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.Goods;

/**
 * ArticleAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-11 19:20:25
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ArticleAdapterDelegate extends BindingAdapterDelegate<Goods.Article> {

    @Override
    public int getLayoutRes() {
        return R.layout.item_goods_articles_binding;
    }

    @Override
    public void setVariable(ViewDataBinding binding, Goods.Article item, int position) {
        binding.setVariable(BR.model, item);
    }

}
