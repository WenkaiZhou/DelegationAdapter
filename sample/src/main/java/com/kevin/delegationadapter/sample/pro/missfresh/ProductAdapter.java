/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.sample.pro.missfresh;

import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.ColorInt;

import com.kevin.delegationadapter.extras.span.SpanDelegationAdapter;

/**
 * ProductAdapter
 *
 * @author zhouwenkai@baidu.com, Created on 2019-05-22 16:20:28
 * Major Function：<b></b>
 * <p/>
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
public class ProductAdapter extends SpanDelegationAdapter {

    public ProductAdapter() {
        super(true);
        // 注册委托Adapter
        addDelegate(new SecondBannerDelegate());
        addDelegate(new NormalProductDelegate());
    }

    /**
     * 获取指定位置背景装饰器描述，仅仅在当前类型为普通商品并且上一条目为第二banner，并且上一条目bgImage不为空时候返回
     *
     * @param position
     * @return
     */
    public BackgroundItemDecoration.Decoration getDecoration(int position) {
        BackgroundItemDecoration.Decoration decoration = null;
        // 获取指定位置数据
        Products.CellItem cellItem = getItemData(position);
        if (cellItem != null) {
            decoration = new BackgroundItemDecoration.Decoration();
            // 当前条目为普通产品类型（`TYPE_NORMAL_PRODUCT`）
            if (Products.TYPE_NORMAL_PRODUCT == cellItem.cellType
                    // 上一条目不为空
                    && getItemData(position - 1) != null
                    // 上一条目为第二banner(`TYPE_SECOND_BANNER`)
                    && Products.TYPE_SECOND_BANNER == getItemData(position - 1).cellType
                    // 上一条目bgImage字段数据不为空，重置数据
                    && !TextUtils.isEmpty(getItemData(position - 1).bgImage)) {
                decoration.bgImageUrl = getItemData(position - 1).bgImage;
                decoration.beginColor = getBeginColor();
                decoration.endColor = getEndColor();
                decoration.gradient = true;
            }
        }
        return decoration;
    }

    /**
     * 获取指定位置数据，如果位置越界则返回空
     *
     * @param position
     * @return
     */
    private Products.CellItem getItemData(int position) {
        Products.CellItem cellItem = null;
        if (position >= 0 && position < getItemCount()) {
            cellItem = (Products.CellItem) super.getItem(position);
        }
        return cellItem;
    }

    @ColorInt
    private int getBeginColor() {
        return Color.parseColor("#00f8f8f8");
    }

    @ColorInt
    private int getEndColor() {
        return Color.parseColor("#fff8f8f8");
    }
}
