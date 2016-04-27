package com.andela.checksmarter.utilities

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.andela.checksmarter.adapters.CheckSmarterTaskAdapter

/**
 * Created by CodeKenn on 25/04/16.
 */
class SimpleItemTouchHelperCallback(itemTouchHelperCallback: ItemTouchHelperCallback) : ItemTouchHelper.Callback() {
    val itemTouchHelperCallback = itemTouchHelperCallback

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlag = 0
        var swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlag, swipeFlag)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        this.itemTouchHelperCallback.onItemSwipedOffScreen((viewHolder as CheckSmarterTaskAdapter.ViewHolder).adapterPosition)
    }
}