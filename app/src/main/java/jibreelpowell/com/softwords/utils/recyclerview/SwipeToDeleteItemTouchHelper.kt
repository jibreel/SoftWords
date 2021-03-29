package jibreelpowell.com.softwords.utils.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import jibreelpowell.com.softwords.R

class SwipeToDeleteItemTouchHelper(context: Context, deleteCallback: (index: Int) -> Unit): ItemTouchHelper(SimpleSwipeToDeleteCallback(context, deleteCallback))

class SimpleSwipeToDeleteCallback(context: Context, val deleteCallback: (index: Int) -> Unit): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteSweepIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_sweep)
    private val iconIntrinsicWidth = deleteSweepIcon?.intrinsicWidth ?: 0
    private val iconIntrinsicHeight = deleteSweepIcon?.intrinsicHeight ?: 0
    private val background = ColorDrawable()
    private val backgroundColor = ContextCompat.getColor(context, R.color.recyclerview_swipe_to_delete_background)
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

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

        val bounds = Rect((itemView.right + dX).toInt(), itemView.top, itemView.right, itemView.bottom)

        //if swipe is cancelled
        if (dX == 0f && !isCurrentlyActive) {
            //clear canvas
            c.drawRect(bounds, clearPaint)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        //draw the red delete background
        background.color = backgroundColor
        background.bounds = bounds
        background.draw(c)

        //Calculate delete icon position
        val deleteIconMargin = (itemHeight - iconIntrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - iconIntrinsicWidth
        val deleteIconTop = itemView.top + (itemHeight - iconIntrinsicHeight) / 2
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + iconIntrinsicHeight

        deleteSweepIcon?.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteSweepIcon?.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}