package ru.test.rssreader.api

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.xmlpull.v1.XmlPullParserException
import ru.test.rssreader.api.model.Feed
import ru.test.rssreader.api.model.FeedItem
import ru.test.rssreader.db.ChannelsDao
import ru.test.rssreader.db.ItemsDao
import ru.test.rssreader.db.entities.Channel
import ru.test.rssreader.db.entities.Item
import timber.log.Timber
import java.net.UnknownHostException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.xml.stream.XMLStreamException


@SuppressLint("CheckResult")
class RssService
    @Inject
    constructor(
        val itemsDao: ItemsDao,
        val channelsDao: ChannelsDao,
        val feedService: RssApi
    ) {
    companion object{
        val formatList = listOf(
            SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH),
            SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
            )
    }

    fun refreshItems(channelLink: String, callback: (RequestResult) -> Unit) {

        feedService.getFeed((channelLink))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map{feed ->
                saveChannel(channelLink, feed)
                saveItems(channelLink, feed.channel?.feedItems ?: emptyList())
                feed
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {feed ->
                    Timber.d("getFeed response: $feed")
                    callback(RequestResult.SUCCESS)
                },
                {error ->
                    run {
                        Timber.d("getFeed error: ${error.javaClass}")
                        error.printStackTrace()
                        when (error) {
                            is UnknownHostException -> callback(RequestResult.DISCONNECTED)
                            is IllegalArgumentException -> callback(RequestResult.BAD_URL)
                            is XmlPullParserException -> callback(RequestResult.NOT_A_FEED)
                            is RuntimeException -> {
                                when (error.cause) {
                                    is ClassNotFoundException -> callback(RequestResult.NOT_A_FEED)
                                    is XMLStreamException -> callback(RequestResult.NOT_A_FEED)
                                    else -> callback(RequestResult.UNKNOWN)
                                }
                            }
                            else -> callback(RequestResult.UNKNOWN)
                        }
                    }
                }
            )
    }

    private fun saveChannel(channelLink: String, feed: Feed) {
        channelsDao.insert(Channel(
            channelLink,
            feed.url ?: "",
            feed.channel?.title ?: "",
            feed.channel?.description ?: "",
            feed.channel?.image?.url?.let {
                if(it.startsWith("http:")) it.replace("http:", "https:")
                else it}))
    }

    private fun saveItems(channelLink: String, items: List<FeedItem>) {
        val result = mutableListOf<Item>()
        for(item in items){
            result.add(Item(
                channelKey = channelLink,
                pubDate = parseDate(item.pubDate),
                title = item.title ?: "",
                link = item.link ?: "",
                description = item.description ?: "",
                read = 0
            ))
        }
        itemsDao.insert(result)
    }

    private fun parseDate(dateString: String?): Long {
        for (format in formatList)
            try{
                return format.parse(dateString ?: "").time
            }catch (ex: ParseException){}
        return 0L
    }

}