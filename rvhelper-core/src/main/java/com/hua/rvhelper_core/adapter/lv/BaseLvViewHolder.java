package com.hua.rvhelper_core.adapter.lv;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.appcompat.widget.SwitchCompat;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * recyclerView viewHolder封装
 *
 * @author hua
 * @date 2017/6/16
 */

public class BaseLvViewHolder {

    private View itemView;
    private SparseArray<View> viewHashMap = new SparseArray<>();

    public BaseLvViewHolder(View itemView) {
        this.itemView = itemView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int id) {
        View view = viewHashMap.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            if (view != null) {
                viewHashMap.put(id, view);
            }
        }
        return (T) view;
    }

    public BaseLvViewHolder setText(@IdRes int id, String text) {
        TextView textView = getView(id);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    public BaseLvViewHolder setText(@IdRes int id, int text) {
        TextView textView = getView(id);
        if (textView != null) {
            textView.setText(String.valueOf(text));
        }
        return this;
    }

    public BaseLvViewHolder setTextColor(@IdRes int id, int color) {
        TextView textView = getView(id);
        if (textView != null) {
            textView.setTextColor(color);
        }
        return this;
    }

    public BaseLvViewHolder setVisibility(@IdRes int id, int visible) {
        View view = getView(id);
        if (view != null) {
            if (view.getVisibility() != visible) {
                view.setVisibility(visible);
            }
        }
        return this;
    }

    public int getVisibility(@IdRes int id) {
        View view = getView(id);
        if (view != null) {
            return view.getVisibility();
        }
        return -1;
    }

    public BaseLvViewHolder setSwitchOnOff(@IdRes int id, boolean onOff) {
        SwitchCompat switchCompat = getView(id);
        if (switchCompat != null) {
            switchCompat.setSelected(onOff);
        }
        return this;
    }

    public BaseLvViewHolder setSelected(@IdRes int id, boolean selected) {
        View view = getView(id);
        if (view != null) {
            view.setSelected(selected);
        }
        return this;
    }

    public BaseLvViewHolder setImageResId(@IdRes int id, @DrawableRes int imgId) {
        ImageView imageView = getView(id);
        if (imageView != null) {
            imageView.setImageResource(imgId);
        }
        return this;
    }

    public BaseLvViewHolder setBackgroundColor(@IdRes int id, int color) {
        final View view = getView(id);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public BaseLvViewHolder setBackgroundRes(@IdRes int id, @DrawableRes int res) {
        final View view = getView(id);
        if (view != null) {
            view.setBackgroundResource(res);
        }
        return this;
    }

}
