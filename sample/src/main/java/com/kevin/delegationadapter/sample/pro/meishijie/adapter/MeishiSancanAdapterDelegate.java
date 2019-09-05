package com.kevin.delegationadapter.sample.pro.meishijie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.databinding.ItemMeishiSancanBinding;
import com.kevin.delegationadapter.sample.pro.meishijie.bean.Meishi;

import java.util.List;

/**
 * MeishiSancanAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-13 12:34:00
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MeishiSancanAdapterDelegate extends BindingAdapterDelegate<Meishi.Sancan> {

    public ObservableInt selected = new ObservableInt();

    @Override
    public int getLayoutRes() {
        return R.layout.item_meishi_sancan;
    }

    @Override
    public void setVariable(@NonNull ViewDataBinding binding, Meishi.Sancan sancan, int position) {
        binding.setVariable(BR.model, sancan);
        binding.setVariable(BR.view, this);
        ItemMeishiSancanBinding sancanBinding = (ItemMeishiSancanBinding) binding;

        // 未初始化过ViewPager
        if (null == sancanBinding.vpMeal.getAdapter()) {
            selected.set(sancan.select);
            MealPageAdapter adapter = new MealPageAdapter(sancan.items);
            sancanBinding.vpMeal.setAdapter(adapter);
            sancanBinding.vpMeal.setOffscreenPageLimit(sancan.items.size() - 1); // 设置最多预加载
            sancanBinding.vpMeal.setCurrentItem(sancan.select, false);
            sancanBinding.vpMeal.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    selected.set(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    /**
     * 底部图标点击的监听回调
     *
     * @param view
     * @param index
     */
    public void onMealTitleClick(@NonNull View view, int index) {
        selected.set(index);
    }

    /**
     * "更多"被点击的监听回调
     *
     * @param view
     */
    public void onMoreClick(@NonNull View view) {
        Toast.makeText(view.getContext(), "更多", Toast.LENGTH_SHORT).show();
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
            ViewDataBinding binding = (ViewDataBinding) object;
            return binding.getRoot() == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            return DataBindingUtil.inflate(inflater, R.layout.layout_meishi_sancan, container, true);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            ViewDataBinding binding = (ViewDataBinding) object;
            binding.setVariable(BR.model, itemList.get(position));
            binding.executePendingBindings();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ViewDataBinding dataBinding = (ViewDataBinding) object;
            container.removeView(dataBinding.getRoot());
        }

    }
}
