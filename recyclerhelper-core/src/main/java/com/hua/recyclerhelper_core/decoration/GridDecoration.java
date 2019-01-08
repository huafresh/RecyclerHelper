package com.hua.recyclerhelper_core.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * recyclerView decoration for {@link GridLayoutManager}
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/7 16:48
 */

@SuppressWarnings("SuspiciousNameCombination")
public class GridDecoration extends LinearDecoration {

    public GridDecoration() {
        super();
    }

    private static int getSpanCount(RecyclerView parent) {
        return getLayoutManager(parent).getSpanCount();
    }

    @NonNull
    private static GridLayoutManager getLayoutManager(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof GridLayoutManager)) {
            throw new RuntimeException("GridDecoration only support GridLayoutManager");
        }
        return (GridLayoutManager) layoutManager;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            int orientation = getOrientation(parent);
            if (orientation == RecyclerView.VERTICAL) {
                if (!isLastColumn(parent, child) || drawLastLine) {
                    drawRight(c, parent, child);
                }
                if (!isLastRow(parent, child) || drawLastLine) {
                    drawBottom(c, parent, child);
                }
            } else {
                if (!isLastColumn(parent, child) || drawLastLine) {
                    drawBottom(c, parent, child);
                }
                if (!isLastRow(parent, child) || drawLastLine) {
                    drawRight(c, parent, child);
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int orientation = getOrientation(parent);
        int bottom = dividerHeight;
        int right = dividerHeight;
        if (isLastRow(parent, view) && !drawLastLine) {
            if (orientation == RecyclerView.VERTICAL) {
                bottom = 0;
            } else {
                right = 0;
            }
        }
        if (isLastColumn(parent, view) && !isLastRow(parent, view) && !drawLastLine) {
            if (orientation == RecyclerView.VERTICAL) {
                right = 0;
            } else {
                bottom = 0;
            }
        }
        outRect.set(0, 0, right, bottom);
    }

    private static boolean isLastRow(RecyclerView parent, View view) {
        final int childCount = parent.getAdapter().getItemCount();
        final int end = childCount % getSpanCount(parent);
        final int integerCount = childCount - (end == 0 ? getSpanCount(parent) : end);
        return (parent.getChildLayoutPosition(view) >= integerCount);
    }

    private static boolean isLastColumn(RecyclerView parent, View view) {
        final int position = parent.getChildLayoutPosition(view);
        return ((position + 1) % getSpanCount(parent) == 0);
    }
}
