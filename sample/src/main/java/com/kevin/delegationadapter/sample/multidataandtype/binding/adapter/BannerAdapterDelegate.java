package com.kevin.delegationadapter.sample.multidataandtype.binding.adapter;

import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.Toast;

import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.BR;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.databinding.ItemGoodsBannerBindingBinding;
import com.kevin.loopview.AdLoopView;
import com.kevin.loopview.internal.BaseLoopAdapter;
import com.kevin.loopview.internal.LoopData;

/**
 * BannerAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-07 21:53:19
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class BannerAdapterDelegate extends BindingAdapterDelegate<LoopData> {

    @Override
    public int getLayoutRes() {
        return R.layout.item_goods_banner_binding;
    }

    @Override
    public void setVariable(ViewDataBinding binding, final LoopData item, int position) {
        binding.setVariable(BR.model, item);

        AdLoopView loopView = ((ItemGoodsBannerBindingBinding) binding).loopView;
        // 设置轮转大图点击监听
        loopView.setOnClickListener(new BaseLoopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PagerAdapter parent, View view, int position, int realPosition) {
                Toast.makeText(view.getContext(), item.items.get(position).link, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected int getSpanSize() {
        return 5;
    }
}
