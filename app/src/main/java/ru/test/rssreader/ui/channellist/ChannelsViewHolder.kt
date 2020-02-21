package ru.test.rssreader.ui.channellist

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.test.rssreader.R
import ru.test.rssreader.TextUtils
import ru.test.rssreader.db.entities.Channel
import ru.test.rssreader.db.entities.Item


class ChannelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView = itemView.findViewById(R.id.title)
    var imageView: ImageView = itemView.findViewById(R.id.image_view)

    fun bind(channel: Channel){
        name.text = TextUtils.htmlToText(channel.title)
        Picasso.get().load(channel.imageUrl).placeholder(R.drawable.ic_rss_feed_24px).into(imageView);
    }
}