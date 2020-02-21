package ru.test.rssreader.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.test.rssreader.api.RequestResult
import ru.test.rssreader.db.ChannelsDao
import ru.test.rssreader.db.ItemsDao
import ru.test.rssreader.api.RssService
import ru.test.rssreader.db.entities.Channel
import ru.test.rssreader.viewmodels.Event


class Repository(
    val itemsDao: ItemsDao,
    val channelsDao: ChannelsDao,
    val rssService: RssService,
    private val _errorData: MutableLiveData<Event<RequestResult>>){

    var errorData
        get() = _errorData

    init {
        errorData = _errorData
    }

    fun getChannels(): LiveData<List<Channel>> {
        return channelsDao.getChannelsListLive()
    }

    fun getChannel(feedUrl: String): Channel{
        return channelsDao.getChannel(feedUrl)
    }

    fun getItems(channelLink: String) = itemsDao.getAllLive(channelLink)

    fun addChannel(channelLink: String) = rssService.refreshItems(channelLink, { _errorData.value = Event(it) })

    suspend fun deleteChannel(channel: Channel){
        itemsDao.delete(itemsDao.getAll(channel.feedUrl))
        channelsDao.delete(channel)
    }

    suspend fun refreshAll() {
        var links = channelsDao.getChannelsList().map { (feedUrl) -> feedUrl }
        for (link in links) {
            rssService.refreshItems(link, { _errorData.value = Event(it) })
        }
    }

    fun refreshChannel(feedUrl: String) {
        rssService.refreshItems(feedUrl, { _errorData.value = Event(it) })
    }

}
