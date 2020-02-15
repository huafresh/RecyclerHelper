package com.hua.rvhelper_core.adapter.lv;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ListView基类适配器
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/30 15:53
 */

public abstract class BaseLvAdapter<T> extends BaseAdapter {
    private final int layoutId;
    private Context context;
    private List<T> dataList;

    public BaseLvAdapter(Context context, @LayoutRes int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public BaseLvAdapter(Context context, @LayoutRes int layoutId, List<T> dataList) {
        this.context = context;
        this.layoutId = layoutId;
        this.dataList = dataList;
    }

    public final BaseLvAdapter<T> setDataList(List<T> list) {
        if (list == null) {
            return this;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
        return this;
    }

    public final @Nullable
    List<T> getDataList() {
        if (dataList != null) {
            return Collections.unmodifiableList(dataList);
        }
        return null;
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return dataList != null ? dataList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            BaseLvViewHolder holder = new BaseLvViewHolder(itemView);
            itemView.setTag(holder);
            convert(holder, dataList.get(position));
            return itemView;
        } else {
            BaseLvViewHolder holder = (BaseLvViewHolder) convertView.getTag();
            convert(holder, dataList.get(position));
            return convertView;
        }
    }

    protected abstract void convert(BaseLvViewHolder viewHolder, T bean);
}
