package ru.test.rssreader.ui.channeldetail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.test.rssreader.R
import ru.test.rssreader.TextUtils
import ru.test.rssreader.db.entities.Item

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView?
    val desc: TextView?
    val imageView: ImageView?

    init{
        title = itemView.findViewById(R.id.title)
        desc = itemView.findViewById(R.id.desc)
        imageView = itemView.findViewById(R.id.image_view)
    }

    fun bind(item: Item){
        title?.text = TextUtils.htmlToText(item.title)
        desc?.text = TextUtils.htmlToText(item.description)
    }
}