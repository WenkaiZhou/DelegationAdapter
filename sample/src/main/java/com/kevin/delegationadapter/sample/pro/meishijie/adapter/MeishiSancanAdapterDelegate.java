package com.kevin.delegationadapter.sample.pro.meishijie.adapter;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.databinding.ItemMeishiSancanBinding;
import com.kevin.delegationadapter.sample.pro.meishijie.bean.Meishi;

import java.util.List;

/**
 * MeishiSancanAdapterDelegate
 *
 * @author zhouwenkai@baidu.com, Created on 2018-06-13 12:34:00
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MeishiSancanAdapterDelegate extends BindingAdapterDelegate<Meishi.Sancan> {

    public ObservableField<String> titleMeal = new ObservableField<>();
    public ObservableInt selected = new ObservableInt();

    @Override
    public int getLayoutRes() {
        return R.layout.item_meishi_sancan;
    }

    @Override
    public void setVariable(ViewDataBinding binding, Meishi.Sancan sancan, int position) {
        binding.setVariable(BR.model, sancan);
        binding.setVariable(BR.view, this);
        ItemMeishiSancanBinding sancanBinding = (ItemMeishiSancanBinding) binding;

        if (null == sancanBinding.vpMeal.getAdapter()) {
            selected.set(sancan.select);
            titleMeal.set(sancan.meal);
            MealPageAdapter adapter = new MealPageAdapter(sancan.items);
            sancanBinding.vpMeal.setAdapter(adapter);
            sancanBinding.vpMeal.setCurrentItem(sancan.select, false);
        }
    }


    public void onMealTitleClick(View view, String meal, int index) {
        titleMeal.set(meal);
        selected.set(index);
    }


    static class MealPageAdapter extends PagerAdapter {

        private List<Meishi.SancanItem> itemList;

        public MealPageAdapter(List<Meishi.SancanItem> itemList) {
            this.itemList = itemList;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_meishi_sancan, container, false);
            ImageView ivMeal1 = view.findViewById(R.id.iv_meal_item1);
            ImageView ivMeal2 = view.findViewById(R.id.iv_meal_item2);
            Glide.with(ivMeal1.getContext()).load(itemList.get(position).items.get(0).img).into(ivMeal1);
            Glide.with(ivMeal1.getContext()).load(itemList.get(position).items.get(1).img).into(ivMeal2);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
