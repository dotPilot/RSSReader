package ru.test.rssreader.ui.callbacks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.test.rssreader.ui.channellist.ChannelsListAdapter
import ru.test.rssreader.viewmodels.ChannelsListViewModel


class SwipeToDeleteCallback(adapter: ChannelsListAdapter, viewModel: ChannelsListViewModel, context: Context):
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val adapter: ChannelsListAdapter
    private val viewModel:ChannelsListViewModel
    val background = ColorDrawable(Color.RED);
    val icon: Drawable
    init{
        this.adapter = adapter
        this.viewModel = viewModel
        icon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete)!!
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewModel.deleteChannel(adapter.getItem(viewHolder.adapterPosition))
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
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
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        if (dX > 0) {
            val iconLeft = itemView.getLeft() + iconMargin;
            val iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(
                itemView.left, itemView.top,
                itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
            )
        } else if (dX < 0) {
            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else {
            icon.setBounds(0, 0, 0, 0);
            background.setBounds(0, 0, 0, 0)
        }

        background.draw(c)
        icon.draw(c)
    }
}