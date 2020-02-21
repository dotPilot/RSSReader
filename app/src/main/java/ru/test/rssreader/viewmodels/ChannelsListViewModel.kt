package ru.test.rssreader.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.test.rssreader.Navigator
import ru.test.rssreader.RssReaderApp
import ru.test.rssreader.api.RequestResult
import ru.test.rssreader.db.entities.Channel
import ru.test.rssreader.repository.Repository
import timber.log.Timber
import javax.inject.Inject


class ChannelsListViewModel: ViewModel() {
    @Inject
    lateinit var repository: Repository
    private lateinit var _channelsLiveData : LiveData<List<Channel>>
    val channelsLiveData: LiveData<List<Channel>>
        get() = _channelsLiveData

    val errors: LiveData<Event<RequestResult>>

    init {
        RssReaderApp.appComponent.inject(this)
        loadChannels()
        errors = repository.errorData
    }

    private fun loadChannels() {
        viewModelScope.launch {
            _channelsLiveData = repository.getChannels()
            Timber.d("channels are: ${_channelsLiveData.value}")
        }
    }

    fun addChannel(channelLink: String) {
        repository.addChannel(channelLink)
    }

    fun deleteChannel(channel: Channel) {
        viewModelScope.launch {
            repository.deleteChannel(channel)
        }
    }

    fun showChannelItems(context: Context, position: Int) {
        val channel = _channelsLiveData.value?.get(position)
        channel?.let { Navigator(context).navigateToDetail(it) }
    }
}