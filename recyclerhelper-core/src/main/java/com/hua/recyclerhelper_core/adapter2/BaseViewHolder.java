package com.hua.recyclerhelper_core.adapter2;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
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

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewHashMap = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
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

    public BaseViewHolder setText(@IdRes int id, String text) {
        TextView textView = getView(id);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    public BaseViewHolder setText(@IdRes int id, int text) {
        TextView textView = getView(id);
        if (textView != null) {
            textView.setText(String.valueOf(text));
        }
        return this;
    }

    public BaseViewHolder setTextColor(@IdRes int id, int color) {
        TextView textView = getView(id);
        if (textView != null) {
            textView.setTextColor(color);
        }
        return this;
    }

    public BaseViewHolder setVisibility(@IdRes int id, int visible) {
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

    public BaseViewHolder setSwitchOnOff(@IdRes int id, boolean onOff) {
        SwitchCompat switchCompat = getView(id);
        if (switchCompat != null) {
            switchCompat.setSelected(onOff);
        }
        return this;
    }

    public BaseViewHolder setSelected(@IdRes int id, boolean selected) {
        View view = getView(id);
        if (view != null) {
            view.setSelected(selected);
        }
        return this;
    }

    public BaseViewHolder setImageResId(@IdRes int id, @DrawableRes int imgId) {
        ImageView imageView = getView(id);
        if (imageView != null) {
            imageView.setImageResource(imgId);
        }
        return this;
    }

    public BaseViewHolder setBackgroundColor(@IdRes int id, int color) {
        final View view = getView(id);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public BaseViewHolder setBackgroundRes(@IdRes int id, @DrawableRes int res) {
        final View view = getView(id);
        if (view != null) {
            view.setBackgroundResource(res);
        }
        return this;
    }

}
