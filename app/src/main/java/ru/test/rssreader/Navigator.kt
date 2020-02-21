package ru.test.rssreader

import android.content.Context
import android.content.Intent
import ru.test.rssreader.db.entities.Channel
import ru.test.rssreader.ui.channeldetail.ItemListActivity
import javax.inject.Inject

class Navigator
@Inject
constructor(
        val context: Context
) {
    fun navigateToDetail(channel: Channel) {
        val intent = Intent(context, ItemListActivity::class.java)
        intent.putExtra(ItemListActivity.FEED_URL_LINK_KEY, channel.feedUrl)
        context.startActivity(intent)
    }
}