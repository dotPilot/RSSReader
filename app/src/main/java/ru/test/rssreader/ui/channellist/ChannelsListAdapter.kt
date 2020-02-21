package ru.test.rssreader.ui.channellist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import ru.test.rssreader.R
import ru.test.rssreader.db.entities.Channel
import ru.test.rssreader.ui.channeldetail.ItemViewHolder


class ChannelsListAdapter: RecyclerView.Adapter<ChannelsViewHolder>() {
    private var channels: List<Channel> = listOf()

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        holder.bind(channels[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.channels_list_item, parent, false)
        return ChannelsViewHolder(rootView)
    }

    override fun getItemCount(): Int = channels.size

    fun swap(channels: List<Channel>?) {
        channels?.let {
            this.channels = it
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int) = channels[position]
}