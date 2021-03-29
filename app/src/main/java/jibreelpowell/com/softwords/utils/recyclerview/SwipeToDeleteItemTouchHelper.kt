package jibreelpowell.com.softwords.utils.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import jibreelpowell.com.softwords.R

class SwipeToDeleteItemTouchHelper(context: Context, deleteCallback: (index: Int) -> Unit): ItemTouchHelper(SimpleSwipeToDeleteCallback(context, deleteCallback))

class SimpleSwipeToDeleteCallback(context: Context, val deleteCallback: (index: Int) -> Unit): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val backgroundColor = ContextCompat.getColor(context, R.color.recyclerview_swipe_to_delete_background)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false // Callback does not allow for items to be moved
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction != ItemTouchHelper.LEFT) return
        val itemPosition = viewHolder.adapterPosition
        deleteCallback(itemPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        //if swipe is cancelled
        if (dX == 0f && !isCurrentlyActive) {
            //clear canvas
            c.drawRect(itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat(), clearPaint)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }


    }

    companion object {
        private val CLEAR_PAINT = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }
    }
}