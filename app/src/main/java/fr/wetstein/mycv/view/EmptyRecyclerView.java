package fr.wetstein.mycv.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ThundeR on 12/08/15.
 */
public class EmptyRecyclerView extends RecyclerView {
    private View mEmptyView;

    public EmptyRecyclerView(Context context) { super(context); }

    public EmptyRecyclerView(Context context, AttributeSet attrs) { super(context, attrs); }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (mEmptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        checkIfEmpty();
    }
}
