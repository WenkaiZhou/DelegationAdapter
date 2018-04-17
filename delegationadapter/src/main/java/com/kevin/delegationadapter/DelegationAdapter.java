package com.kevin.delegationadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * DelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:08:38
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class DelegationAdapter<VH extends RecyclerView.ViewHolder> extends AbsDelegationAdapter<VH> {

    private List<Object> mDataItems = new ArrayList<>();
    private List<Object> mHeaderItems = new ArrayList<>();
    private List<Object> mFooterItems = new ArrayList<>();

    public DelegationAdapter() {
        super();
    }

    public DelegationAdapter(@NonNull AdapterDelegatesManager delegatesManager) {
        super(delegatesManager);
    }

    public void addHeaderItem(Object headerItem) {
        mHeaderItems.add(headerItem);
    }

    public void addFooterItem(Object footerItem) {
        mFooterItems.add(footerItem);
    }

    public void setDataItems(List dataItems) {
        this.mDataItems = dataItems;
        notifyDataSetChanged();
    }

    public void addDataItem(Object item) {
        addDataItem(mDataItems.size(), item);
    }

    public void addDataItem(int position, Object itemData) {
        mDataItems.add(position, itemData);
        notifyItemRangeInserted(position, 1);
    }

    public void addDataItems(List dataItems) {
        addDataItems(mDataItems.size(), dataItems);
    }

    public void addDataItems(int position, List dataItems) {
        mDataItems.addAll(position, dataItems);
        notifyItemRangeInserted(position, dataItems.size());
    }

    public void moveDataItem(int fromPosition, int toPosition) {
        toPosition = fromPosition < toPosition ? toPosition - 1 : toPosition;
        mDataItems.add(toPosition, mDataItems.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }

    public void removeDataItem(Object dataItem) {
        int index = mDataItems.indexOf(dataItem);
        if (index != -1 && index <= mDataItems.size()) {
            removeDataItem(index);
        }
    }

    public void removeDataItem(int position) {
        removeDataItem(position, 1);
    }

    public void removeDataItem(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            mDataItems.remove(position);
        }
        notifyItemRangeRemoved(position + getHeaderCount(), itemCount);
    }

    public List<Object> getDataList() {
        return mDataItems;
    }

    public Object getItem(int position) {
        if (position < mHeaderItems.size()) {
            return mHeaderItems.get(position);
        }

        position -= mHeaderItems.size();
        if (position < mDataItems.size()) {
            return mDataItems.get(position);
        }

        position -= mDataItems.size();
        if (position < mFooterItems.size()) {
            return mFooterItems.get(position);
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return mDataItems.size() + getHeaderCount() + getFootCount();
    }

    public int getHeaderCount() {
        return mHeaderItems.size();
    }

    public int getFootCount() {
        return mFooterItems.size();
    }

    public void clearData() {
        mDataItems.clear();
    }

    public void clearAllData() {
        clearData();
        mHeaderItems.clear();
        mFooterItems.clear();
    }

}
