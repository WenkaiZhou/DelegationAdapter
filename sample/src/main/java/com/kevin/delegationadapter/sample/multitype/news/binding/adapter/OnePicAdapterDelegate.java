package com.kevin.delegationadapter.sample.multitype.news.binding.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.News;

/**
 * OnePicAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-27 19:09:27
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class OnePicAdapterDelegate extends BindingAdapterDelegate<News> {

    @Override
    protected boolean isForViewType(News news, int position) {
        // 我能处理一张图片
        return news.type == 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_binding_news_one_pic;
    }

    @Override
    public void setVariable(ViewDataBinding binding, News item, int position) {
        binding.setVariable(BR.model, item);
    }

    @Override
    public void onItemClick(View view, News item, int position) {
        Toast.makeText(view.getContext(), "click position " + position + "\ncontent:" + item.content,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(View view, News item, int position) {
        Toast.makeText(view.getContext(), "long click position " + position + "\ncontent:" + item.content,
                Toast.LENGTH_SHORT).show();
        return true;
    }
}
