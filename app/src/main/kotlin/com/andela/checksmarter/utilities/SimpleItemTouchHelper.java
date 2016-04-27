package com.andela.checksmarter.utilities;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by CodeKenn on 25/04/16.
 */
public class SimpleItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private ItemTouchHelperCallback itemTouchHelperCallback;

    public SimpleItemTouchHelper(ItemTouchHelperCallback itemTouchHelperCallback) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.itemTouchHelperCallback = itemTouchHelperCallback;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchHelperCallback.onItemSwipedOffScreen(viewHolder.getAdapterPosition());
    }
}
