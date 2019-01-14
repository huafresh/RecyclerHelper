package com.hua.rvhelper_core.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by hua on 2017/6/16.
 * {@link LinearLayoutManager}布局管理器的分割线
 */

public class LinearDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_DIVIDER_COLOR = 0xffdddddd;
    private static final int DEFAULT_DIVIDER_HEIGHT = 1;
    private Drawable mDividerDrawable;
    int dividerHeight;
    private final Rect mBounds = new Rect();
    private boolean isContainPadding = false;
    boolean drawLastLine = false;

    public LinearDecoration() {
        setDividerColor(DEFAULT_DIVIDER_COLOR, DEFAULT_DIVIDER_HEIGHT);
    }

    /**
     * 设置分割线的图片
     *
     * @param drawable Drawable
     */
    public LinearDecoration setDividerDrawable(@NonNull Drawable drawable) {
        mDividerDrawable = drawable;
        return this;
    }

    public LinearDecoration setDividerColor(@ColorInt int color, int dividerHeight) {
        this.setDividerDrawable(new ColorDrawable(color));
        this.dividerHeight = dividerHeight;
        return this;
    }

    public LinearDecoration setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
        return this;
    }

    public LinearDecoration drawLastLine(boolean draw) {
        this.drawLastLine = draw;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.save();
        final int count = drawLastLine ? parent.getChildCount() : parent.getChildCount() - 1;
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            int orientation = getOrientation(parent);
            if (orientation == RecyclerView.VERTICAL) {
                drawBottom(c, parent, child);
            } else {
                drawRight(c, parent, child);
            }
        }
        c.restore();
    }

    int getOrientation(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            throw new RuntimeException("LinearDecoration only support LinearLayoutManager");
        }
        return ((LinearLayoutManager) layoutManager).getOrientation();
    }

    void drawBottom(Canvas c, RecyclerView parent, View child) {
        parent.getDecoratedBoundsWithMargins(child, mBounds);
        final int left = mBounds.left + getLeftMarginAndPadding(child);
        final int right = mBounds.right - getRightMarginAndPadding(child);
        final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
        final int top = bottom - dividerHeight;
        mDividerDrawable.setBounds(left, top, right, bottom);
        mDividerDrawable.draw(c);
    }

    void drawRight(Canvas c, RecyclerView parent, View child) {
        parent.getDecoratedBoundsWithMargins(child, mBounds);
        final int right = mBounds.right + Math.round(child.getTranslationX());
        final int left = right - dividerHeight;
        final int top = mBounds.top + getTopMarginAndPadding(child);
        final int bottom = mBounds.bottom - getBottomMarginAndPadding(child);
        mDividerDrawable.setBounds(left, top, right, bottom);
        mDividerDrawable.draw(c);
    }

    private int getLeftMarginAndPadding(View child) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        return lp.leftMargin + (isContainPadding ? child.getPaddingLeft() : 0);
    }

    private int getRightMarginAndPadding(View child) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        return lp.rightMargin + (isContainPadding ? child.getPaddingRight() : 0);
    }

    private int getTopMarginAndPadding(View child) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        return lp.topMargin + (isContainPadding ? child.getPaddingTop() : 0);
    }

    private int getBottomMarginAndPadding(View child) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        return lp.bottomMargin + (isContainPadding ? child.getPaddingBottom() : 0);
    }

    /**
     * 设置分割线是否包含padding距离。默认是不包含
     *
     * @param isContain true包含，false不包含
     */
    public LinearDecoration containItemPadding(boolean isContain) {
        isContainPadding = isContain;
        return this;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        int totalCount = parent.getAdapter().getItemCount();
        if (position < totalCount - 1 || drawLastLine) {
            int orientation = getOrientation(parent);
            if (orientation == RecyclerView.VERTICAL) {
                outRect.set(0, 0, 0, dividerHeight);
            } else {
                outRect.set(0, 0, dividerHeight, 0);
            }
        }
    }
}
