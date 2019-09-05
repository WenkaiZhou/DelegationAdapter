package com.kevin.delegationadapter.sample.samedata.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.extras.binding.BindingViewHolder;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.samedata.bean.Bill;

import java.util.List;

/**
 * ServiceInfoAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-28 16:33:18
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ServiceInfoAdapterDelegate extends BindingAdapterDelegate<Bill> {

    public static final String TAG = "ServiceInfoDelegateAdapter";

    public ServiceInfoAdapterDelegate() {
        super(TAG);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position, @NonNull List<?> payloads, Bill item) {
        super.onBindViewHolder(holder, position, payloads, item);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position, Bill item) {
        super.onBindViewHolder(holder, position, item);
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return super.onCreateViewHolder(parent);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull BindingViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BindingViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BindingViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(@NonNull BindingViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onItemLongClick(@NonNull View view, Bill item, int position) {
        return super.onItemLongClick(view, item, position);
    }

    @Override
    public void onItemClick(@NonNull View view, Bill item, int position) {
        super.onItemClick(view, item, position);
    }

    @Override
    public boolean longClickable(int position) {
        return super.longClickable(position);
    }

    @Override
    public void configureViewHolder(@NonNull BindingViewHolder holder) {
        super.configureViewHolder(holder);
    }

    @Override
    public boolean isForViewType(Bill item, int position) {
        return super.isForViewType(item, position);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_bill_service_info;
    }

    @Override
    public void setVariable(@NonNull ViewDataBinding binding, Bill item, int position) {
        binding.setVariable(BR.model, item);
    }
}
