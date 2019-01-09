package com.hua.recyclerhelper;

import com.hua.recyclerhelper_core.adapter2.BaseRvItemType;
import com.hua.recyclerhelper_core.adapter2.BaseViewHolder;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/9 17:05
 */

public class ItemType2 extends BaseRvItemType<Item> {
    @Override
    public int layoutId() {
        return R.layout.recycler_item3;
    }

    @Override
    public boolean isForViewType(Item data, int position) {
        return data.type == 1;
    }

    @Override
    public void bind(BaseViewHolder viewHolder, Item data) {
        viewHolder.setText(R.id.text, data.name);
    }
}
