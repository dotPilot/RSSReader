package ru.test.rssreader.ui.channeldetail

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.test.rssreader.R
import ru.test.rssreader.db.entities.Item
import ru.test.rssreader.ui.callbacks.ItemsDiffUtilCallback


class ItemListAdapter: RecyclerView.Adapter<ItemViewHolder>() {
    private var items: List<Item> = listOf()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return ItemViewHolder(rootView)
    }

    override fun getItemCount(): Int = items.size

    fun swap(newItems: List<Item>?) {
        newItems?.let {
            val itemsDiffResult = DiffUtil.calculateDiff(ItemsDiffUtilCallback(items, it))
            this.items = it
            itemsDiffResult.dispatchUpdatesTo(this)
        }
    }

    fun getItem(position: Int) = items[position]
}
