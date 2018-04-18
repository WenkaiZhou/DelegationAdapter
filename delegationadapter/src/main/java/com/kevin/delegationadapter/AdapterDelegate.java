package com.kevin.delegationadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * AdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:04:28
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public abstract class AdapterDelegate<T, VH extends RecyclerView.ViewHolder> {

    public static final String DEFAULT_TAG = "";

    private String mTag;

    public AdapterDelegate() {
        this.setTag(DEFAULT_TAG);
    }

    public AdapterDelegate(@NonNull String tag) {
        if (null == tag || tag.length() == 0) {
            throw new NullPointerException("The tag of "
                    + this
                    + " is null.");
        }
        this.setTag(tag);
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    protected boolean isForViewType(T item, int position) {
        return true;
    }

    protected abstract VH onCreateViewHolder(ViewGroup parent);

    protected abstract void onBindViewHolder(T item, int position, VH holder, List<Object> payloads);
}
