package com.hua.rvhelper_core.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/10 15:50
 */

class HeaderFooterHelper {
    private List<HeaderFooterType> headers;
    private List<HeaderFooterType> footers;
    private BaseRvAdapter adapter;

    HeaderFooterHelper(BaseRvAdapter adapter) {
        this.adapter = adapter;
        adapter.registerAdapterDataObserver(new HeaderFooterHelper.AdapterObserver(this));
    }

    void addHeaderView(View header, int position) {
        if (headers == null) {
            headers = new ArrayList<>();
        }
        HeaderFooterType itemType = new HeaderFooterType(header, position);
        headers.add(itemType);
        adapter.addRvItemType(itemType);
    }

    void addFooterView(View footer, int position) {
        if (footers == null) {
            footers = new ArrayList<>();
        }
        HeaderFooterType itemType = new HeaderFooterType(footer, position);
        footers.add(itemType);
        adapter.addRvItemType(itemType);
    }

    int headerCount() {
        return headers != null ? headers.size() : 0;
    }

    int footerCount() {
        return footers != null ? footers.size() : 0;
    }

    int totalCount() {
        return headerCount() + footerCount();
    }

    boolean isHeaderPos(int pos) {
        return pos < headerCount();
    }

    boolean isFooterPos(int pos) {
        if (footers != null) {
            for (HeaderFooterType footer : footers) {
                if (footer.position == pos) {
                    return true;
                }
            }
        }
        return false;
    }

    IRvItemType getRvItemType(int position) {
        if (headers != null) {
            for (HeaderFooterType header : headers) {
                if (header.isForViewType(null, position)) {
                    return header;
                }
            }
        }

        if (footers != null) {
            for (HeaderFooterType footer : footers) {
                if (footer.isForViewType(null, position)) {
                    return footer;
                }
            }
        }

        return null;
    }

    static class HeaderFooterType implements IRvItemType {
        private int position;
        private View view;

        HeaderFooterType(View view, int position) {
            this.view = view;
            this.position = position;
        }

        @Override
        public int layoutId() {
            return 0;
        }

        @Nullable
        @Override
        public View getItemView() {
            return view;
        }

        @Override
        public boolean isForViewType(Object data, int position) {
            return this.position == position;
        }

        @Override
        public void bind(BaseViewHolder viewHolder, Object data) {

        }
    }

    void refreshFooterPosition() {
        if (footers != null) {
            for (int i = 0; i < footers.size(); i++) {
                HeaderFooterType type = footers.get(i);
                type.position = adapter.getDataListCount() + headerCount() + i;
            }
        }
    }

    static class AdapterObserver extends RecyclerView.AdapterDataObserver {
        private HeaderFooterHelper headerFooterHelper;

        AdapterObserver(HeaderFooterHelper headerFooterHelper) {
            this.headerFooterHelper = headerFooterHelper;
        }

        @Override
        public void onChanged() {
            super.onChanged();
            headerFooterHelper.refreshFooterPosition();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            headerFooterHelper.refreshFooterPosition();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            headerFooterHelper.refreshFooterPosition();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            headerFooterHelper.refreshFooterPosition();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            headerFooterHelper.refreshFooterPosition();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            headerFooterHelper.refreshFooterPosition();
        }
    }

}
