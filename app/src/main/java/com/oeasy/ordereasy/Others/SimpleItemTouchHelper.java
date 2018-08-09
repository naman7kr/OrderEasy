package com.oeasy.ordereasy.Others;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.oeasy.ordereasy.Adapters.VerticalRecyclerViewAdapter;

public class SimpleItemTouchHelper extends ItemTouchHelper.Callback{
    VerticalRecyclerViewAdapter mAdapter;
    VerticalRecyclerViewAdapter.MessageViewHolder viewHolder;
    public SimpleItemTouchHelper(VerticalRecyclerViewAdapter adapter){
        mAdapter=adapter;
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        VerticalRecyclerViewAdapter.MessageViewHolder holder=(VerticalRecyclerViewAdapter.MessageViewHolder)viewHolder;
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
