package com.kevin.delegationadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * DelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:08:38
 *         Major Function：<b>Delegation Adapter</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
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
        addHeaderItem(getHeaderCount(), headerItem);
    }

    public void addHeaderItem(int position, Object headerItem) {
        mHeaderItems.add(position, headerItem);
        notifyItemRangeInserted(position, 1);
    }

    public void addFooterItem(Object footerItem) {
        addFooterItem(getFootCount(), footerItem);
    }

    public void addFooterItem(int position, Object footerItem) {
        mFooterItems.add(position, footerItem);
        notifyItemRangeInserted(getHeaderCount() + getDataCount() + position, 1);
    }

    public void setDataItems(List dataItems) {
        this.mDataItems = dataItems;
        notifyDataSetChanged();
    }

    public void addDataItem(Object item) {
        addDataItem(getDataCount(), item);
    }

    public void addDataItem(int position, Object item) {
        mDataItems.add(position, item);
        notifyItemRangeInserted(position + getHeaderCount(), 1);
    }

    public void addDataItems(List dataItems) {
        addDataItems(getDataCount(), dataItems);
    }

    public void addDataItems(int position, List dataItems) {
        mDataItems.addAll(position, dataItems);
        notifyItemRangeInserted(position + getHeaderCount(), dataItems.size());
    }

    public void moveDataItem(int fromPosition, int toPosition) {
        toPosition = fromPosition < toPosition ? toPosition - 1 : toPosition;
        mDataItems.add(toPosition, mDataItems.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }

    public void removeDataItem(Object dataItem) {
        int index = mDataItems.indexOf(dataItem);
        if (index != -1 && index <= getDataCount()) {
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

    @Override
    protected Object getItem(int position) {
        if (position < mHeaderItems.size()) {
            return mHeaderItems.get(position);
        }

        position -= mHeaderItems.size();
        if (position < getDataCount()) {
            return mDataItems.get(position);
        }

        position -= getDataCount();
        if (position < mFooterItems.size()) {
            return mFooterItems.get(position);
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return getDataCount() + getHeaderCount() + getFootCount();
    }

    public int getDataCount() {
        return mDataItems.size();
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
