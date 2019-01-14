package com.hua.rvhelper_core.adapter;

import android.util.SparseArray;


/**
 * @author hua
 * @version 1.0
 * @since 2018/8/26
 */
@SuppressWarnings("ALL")
class ItemViewTypeManager {

    private SparseArray<IRvItemType> itemViewTypes;

    void addItemViewDelegate(IRvItemType itemViewDelegate) {
        if (itemViewTypes == null) {
            itemViewTypes = new SparseArray<>();
        }
        itemViewTypes.put(itemViewTypes.size() + 1, itemViewDelegate);
    }

    <T> int getItemViewType(T t, int position) {
        if (itemViewTypes != null) {
            for (int i = 0; i < itemViewTypes.size(); i++) {
                IRvItemType itemViewDelegate = itemViewTypes.valueAt(i);
                if (itemViewDelegate.isForViewType(t, position)) {
                    return itemViewTypes.keyAt(i);
                }
            }
        }
        return 0;
    }

    int getItemViewType(IRvItemType itemType) {
        return itemViewTypes.keyAt(itemViewTypes.indexOfValue(itemType));
    }

    IRvItemType getItemView(int viewType) {
        if (itemViewTypes != null) {
            return itemViewTypes.get(viewType);
        }
        return null;
    }

    void bind(BaseViewHolder holder, Object itemData, int position) {
        if (itemViewTypes != null) {
            for (int i = 0; i < itemViewTypes.size(); i++) {
                IRvItemType itemViewDelegate = itemViewTypes.valueAt(i);
                if (itemViewDelegate.isForViewType(itemData, position)) {
                    itemViewDelegate.bind(holder, itemData);
                    break;
                }
            }
        }
    }
}
