package com.kevin.delegationadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AdapterDelegatesManager
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:24:58
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class AdapterDelegatesManager<VH extends RecyclerView.ViewHolder> {

    private static final List<Object> PAYLOADS_EMPTY_LIST = Collections.emptyList();

    private SparseArray<String> clazzWithTags = new SparseArray<>();
    private SparseArrayCompat<AdapterDelegate<Object, VH>> delegates = new SparseArrayCompat();
    protected AdapterDelegate fallbackDelegate;

    /**
     * Add a Delegate
     *
     * @param delegate
     */
    public AdapterDelegatesManager addDelegate(AdapterDelegate<Object, VH> delegate, String tag) {
        Type superclass = delegate.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Class<?> clazz = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            String nameWithTag = getNameWithTag(clazz, tag);

            int viewType = delegates.size();
            // Save the delegate to the collection;
            delegates.put(viewType, delegate);
            // Save the index of the delegate to the collection;
            clazzWithTags.put(viewType, nameWithTag);
        } else {
            // Has no generics.
            throw new IllegalArgumentException(
                    String.format("Please set the correct generic parameters on %s.", delegate.getClass().getName()));
        }
        return this;
    }

    public int getItemViewType(@NonNull Object item, int position) {

        if (item == null) {
            throw new NullPointerException("Items data source is null!");
        }

        Class clazz = getTargetClass(item);
        String tag = getTargetTag(item);

        String clazzWithTag = getNameWithTag(clazz, tag);
        ArrayList<Integer> indexList = indexListOfValue(clazzWithTags, clazzWithTag);
        if (indexList.size() == 0) {
            throw new RuntimeException(
                    String.format("Please register Delegate for %s type data first.", clazzWithTag));
        } else {
            for (Integer index : indexList) {
                AdapterDelegate<Object, VH> delegate = delegates.valueAt(index);
                if (null != delegate && delegate.getTag().equals(tag) && delegate.isForViewType(item, position)) {
                    return index;
                }
            }

            StringBuilder builder = new StringBuilder();
            for (Integer index : indexList) {
                AdapterDelegate<Object, VH> delegate = delegates.valueAt(index);
                if (null != delegate) {
                    builder.append(delegate.getClass().getSimpleName()).append("\t");
                }
            }
            throw new NullPointerException("No AdapterDelegate added that matches position="
                    + position + " in data source");
        }
    }

    @NonNull
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate<Object, VH> delegate = getDelegateForViewType(viewType);
        if (delegate == null) {
            throw new NullPointerException("No AdapterDelegate added for ViewType " + viewType);
        }

        VH vh = delegate.onCreateViewHolder(parent);
        if (vh == null) {
            throw new NullPointerException("ViewHolder returned from AdapterDelegate "
                    + delegate
                    + " for ViewType ="
                    + viewType
                    + " is null!");
        }
        return vh;
    }

    public void onBindViewHolder(@NonNull Object item, int position,
                                 @NonNull VH viewHolder, List payloads) {

        AdapterDelegate<Object, VH> delegate = getDelegate(item, position);
        if (delegate == null) {
            throw new NullPointerException("No delegate found for item at position = "
                    + position
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        delegate.onBindViewHolder(item, position, viewHolder,
                payloads != null ? payloads : PAYLOADS_EMPTY_LIST);
    }

    public void onBindViewHolder(@NonNull Object item, int position,
                                 @NonNull VH viewHolder) {
        onBindViewHolder(item, position, viewHolder, PAYLOADS_EMPTY_LIST);
    }

    public AdapterDelegatesManager setFallbackDelegate(
            @Nullable AdapterDelegate fallbackDelegate) {
        this.fallbackDelegate = fallbackDelegate;
        return this;
    }

    @Nullable
    public AdapterDelegate<Object, VH> getDelegateForViewType(int viewType) {
        AdapterDelegate<Object, VH> delegate = delegates.get(viewType, fallbackDelegate);
        return delegate;
    }

    public AdapterDelegate<Object, VH> getDelegate(int viewType) {
        AdapterDelegate<Object, VH> delegate = delegates.get(viewType);
        return delegate;
    }

    public AdapterDelegate<Object, VH> getDelegate(Object item, int position) {
        int viewType = getItemViewType(item, position);
        return getDelegate(viewType);
    }

    /**
     * Get the fallback delegate
     *
     * @return The fallback delegate or <code>null</code> if no fallback delegate has been set
     * @see #setFallbackDelegate(AdapterDelegate)
     */
    @Nullable
    public AdapterDelegate getFallbackDelegate() {
        return fallbackDelegate;
    }


    /**
     * Returns the class name with tag;
     *
     * @param clazz
     * @param tag
     * @return
     */
    private String getNameWithTag(Class clazz, String tag) {
        return clazz.getName() + tag;
    }

    /**
     * Returns the target class name
     *
     * @return
     */
    private Class getTargetClass(Object data) {
        return data instanceof ItemData ? ((ItemData) data).getData().getClass() : data.getClass();
    }

    /**
     * Returns the target tag
     *
     * @param data
     * @return
     */
    private String getTargetTag(Object data) {
        return data instanceof ItemData ? ((ItemData) data).getTag() : AdapterDelegate.DEFAULT_TAG;
    }

    /**
     * Returns all indexes for the specified start value
     *
     * @param array
     * @param value
     * @return
     */
    private ArrayList<Integer> indexListOfValue(SparseArray<String> array, String value) {
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            if (value.equals(array.valueAt(i))) {
                indexList.add(i);
            }
        }
        return indexList;
    }
}
