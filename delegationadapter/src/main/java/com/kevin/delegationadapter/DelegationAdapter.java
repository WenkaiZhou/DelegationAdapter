/*
 * Copyright (c) 2018 Kevin zhou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public void setHeaderItem(Object headerItem) {
        mHeaderItems.clear();
        mHeaderItems.add(headerItem);
        notifyDataSetChanged();
    }

    public void setHeaderItems(List headerItems) {
        mHeaderItems = headerItems;
        notifyDataSetChanged();
    }

    public void addHeaderItem(Object headerItem) {
        addHeaderItem(getHeaderCount(), headerItem);
    }

    public void addHeaderItem(int position, Object headerItem) {
        mHeaderItems.add(position, headerItem);
        notifyItemRangeInserted(position, 1);
    }

    public void addHeaderItems(List headerItems) {
        addHeaderItems(getHeaderCount(), headerItems);
    }

    private void addHeaderItems(int position, List headerItems) {
        mHeaderItems.addAll(position, headerItems);
        notifyItemRangeInserted(position, headerItems.size());
    }

    public void setFooterItem(Object footerItem) {
        mFooterItems.clear();
        mFooterItems.add(footerItem);
        notifyDataSetChanged();
    }

    public void setFooterItems(List footerItems) {
        mFooterItems = footerItems;
        notifyDataSetChanged();
    }

    public void addFooterItem(Object footerItem) {
        addFooterItem(getFooterCount(), footerItem);
    }

    public void addFooterItem(int position, Object footerItem) {
        mFooterItems.add(position, footerItem);
        notifyItemRangeInserted(getHeaderCount() + getDataCount() + position, 1);
    }

    public void addFooterItems(List footerItems) {
        addFooterItems(getFooterCount(), footerItems);
    }

    private void addFooterItems(int position, List footerItems) {
        mFooterItems.addAll(position, footerItems);
        notifyItemRangeInserted(getHeaderCount() + getDataCount() + position, footerItems.size());
    }

    public void setDataItems(List dataItems) {
        mDataItems = dataItems;
        notifyDataSetChanged();
    }

    public void addDataItem(Object item) {
        addDataItem(getDataCount(), item);
    }

    public void addDataItem(int position, Object item) {
        mDataItems.add(position, item);
        notifyItemRangeInserted(getHeaderCount() + position, 1);
    }

    public void addDataItems(List dataItems) {
        addDataItems(getDataCount(), dataItems);
    }

    public void addDataItems(int position, List dataItems) {
        mDataItems.addAll(position, dataItems);
        notifyItemRangeInserted(getHeaderCount() + position , dataItems.size());
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
        notifyItemRangeRemoved(getHeaderCount() + position , itemCount);
    }

    public List<Object> getDataList() {
        return mDataItems;
    }

    public List<Object> getHeaderList() {
        return mHeaderItems;
    }

    public List<Object> getFooterItems() {
        return mFooterItems;
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

        position -= mDataItems.size();
        if (position < mFooterItems.size()) {
            return mFooterItems.get(position);
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getDataCount() + getFooterCount();
    }

    public int getDataCount() {
        return mDataItems.size();
    }

    public int getHeaderCount() {
        return mHeaderItems.size();
    }

    public int getFooterCount() {
        return mFooterItems.size();
    }

    public void clearData() {
        mDataItems.clear();
    }

    public void clearHeader() {
        mHeaderItems.clear();
    }

    public void clearFooter() {
        mFooterItems.clear();
    }

    public void clearAllData() {
        clearData();
        clearHeader();
        clearFooter();
    }

}
