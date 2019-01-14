package com.hua.rvhelper;

import com.hua.rvhelper_core.adapter.BaseRvItemType;
import com.hua.rvhelper_core.adapter.BaseViewHolder;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/9 17:05
 */

public class ItemType1 extends BaseRvItemType<Item> {
    @Override
    public int layoutId() {
        return R.layout.recycler_item;
    }

    @Override
    public boolean isForViewType(Item data, int position) {
        return data.type == 0;
    }

    @Override
    public void bind(BaseViewHolder viewHolder, Item data) {
        viewHolder.setText(R.id.text, data.name);
    }
}
