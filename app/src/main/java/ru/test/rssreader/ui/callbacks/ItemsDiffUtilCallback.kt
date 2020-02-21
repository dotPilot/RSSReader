package ru.test.rssreader.ui.callbacks

import androidx.recyclerview.widget.DiffUtil
import ru.test.rssreader.db.entities.Item


class ItemsDiffUtilCallback(val oldItems: List<Item>, val newItems: List<Item>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].link.equals(newItems[newItemPosition].link)
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.title.equals(newItem.title) && oldItem.description.equals(newItem.description)
    }
}