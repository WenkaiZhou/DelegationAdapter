/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.sample.pro.missfresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashMap;

/**
 * BackgroundItemDecoration
 *
 * @author zhouwenkai@baidu.com, Created on 2019-05-22 17:54:45
 * Major Function：<b></b>
 * <p/>
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
public class BackgroundItemDecoration extends RecyclerView.ItemDecoration {

    private DecorationFactory decorationFactory;
    private HashMap<String, BitmapDrawable> bgBitmaps = new HashMap<>();
    private final Rect mTempRect = new Rect();

    public void setDecorationFactory(DecorationFactory factory) {
        this.decorationFactory = factory;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Context context = view.getContext();
        if (!isTargetItem(view, parent)) {
            super.getItemOffsets(outRect, view, parent, state);
        } else if (isLeftItem(view, parent)) {
            outRect.set(dp2px(context, 10), 0, dp2px(context, 3), dp2px(context, 6));
        } else {
            outRect.set(dp2px(context, 3), 0, dp2px(context, 10), dp2px(context, 6));
        }
    }

    /**
     * 判断是否是目标条目
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isTargetItem(View view, RecyclerView parent) {
        boolean target = false;
        if (view.getVisibility() != View.GONE) {
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter != null && adapter.getItemViewType(parent.getChildAdapterPosition(view))
                    == Products.TYPE_NORMAL_PRODUCT) {
                target = true;
            }
        }
        return target;
    }

    private boolean isLeftItem(View view, RecyclerView parent) {
        boolean isLeft;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof GridLayoutManager)) {
            isLeft = true;
        } else {
            int spanIndex = ((GridLayoutManager) layoutManager).getSpanSizeLookup()
                    .getSpanIndex(parent.getChildAdapterPosition(view),
                            ((GridLayoutManager) layoutManager).getSpanCount());
            isLeft = spanIndex == 0;
        }
        return isLeft;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            if (view.getVisibility() != View.GONE && isTargetItem(view, parent)) {
                BitmapDrawable drawable = null;
                if (decorationFactory != null && isLeftItem(view, parent)) {
                    drawable = createDrawable(parent, view);
                }

                if (drawable != null) {
                    parent.getDecoratedBoundsWithMargins(view, mTempRect);
                    drawBitmap(c, drawable, parent.getLeft(), mTempRect.top, parent.getRight(), mTempRect.bottom);
                }
            }
        }
    }

    /**
     * 创建上下线性渐变的Drawable
     *
     * @param bitmap
     * @param beginColor
     * @param endColor
     */
    private void createGradientDrawable(Bitmap bitmap, @ColorInt int beginColor, @ColorInt int endColor) {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{beginColor, endColor});
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        drawable.draw(new Canvas(bitmap));
    }

    private BitmapDrawable createDrawable(final RecyclerView parent, final View view) {
        final Decoration decoration = decorationFactory.createDecoration(parent.getChildAdapterPosition(view));
        if (decoration != null) {
            BitmapDrawable drawable = this.bgBitmaps.get(decoration.toString());
            if (drawable != null) {
                if (drawable.getBitmap() != null && !drawable.getBitmap().isRecycled()) {
                    return drawable;
                } else {
                    this.bgBitmaps.remove(decoration.toString());
                }
            }

            parent.getDecoratedBoundsWithMargins(view, mTempRect);
            Glide.with(parent.getContext())
                    .asBitmap()
                    .override(parent.getWidth(), mTempRect.bottom - mTempRect.top)
                    .load(decoration.bgImageUrl)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            int position = parent.getChildAdapterPosition(view);
                            BackgroundItemDecoration.Decoration decoration = decorationFactory.createDecoration(position);
                            if (decoration != null) {
                                Bitmap bitmap = resource.copy(Bitmap.Config.ARGB_8888, true);
                                if (decoration.gradient) {
                                    createGradientDrawable(resource, decoration.beginColor, decoration.endColor);
                                }
                                bgBitmaps.put(decoration.toString(), new BitmapDrawable(parent.getResources(), bitmap));
                                parent.invalidate();
                            }
                        }
                    });
        }

        return null;
    }

    /**
     * 绘制drawable
     *
     * @param c
     * @param drawable
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private void drawBitmap(Canvas c, BitmapDrawable drawable, int left, int top, int right, int bottom) {
        if (drawable.getBitmap() != null && !drawable.getBitmap().isRecycled()) {
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);
        }
    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public interface DecorationFactory {
        Decoration createDecoration(int position);
    }

    /**
     * 条目装饰器描述信息
     */
    public static class Decoration {
        /**
         * 背景图地址
         */
        public String bgImageUrl;
        /**
         * 是否有上下渐变
         */
        public boolean gradient;
        /**
         * 上下渐变的开始颜色
         */
        @ColorInt
        public int beginColor;
        /**
         * 上下渐变的解释颜色
         */
        @ColorInt
        public int endColor;

        @Override
        public String toString() {
            if (TextUtils.isEmpty(bgImageUrl)) {
                return super.toString();
            }
            return bgImageUrl + gradient + beginColor + endColor;
        }
    }
}
