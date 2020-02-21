package ru.test.rssreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.test.rssreader.RssReaderApp
import ru.test.rssreader.api.RequestResult
import ru.test.rssreader.db.entities.Item
import ru.test.rssreader.repository.Repository
import timber.log.Timber
import javax.inject.Inject

class ItemsDetailViewModel(): ViewModel() {
    @Inject
    lateinit var repository: Repository
    private lateinit var _itemListLiveData : LiveData<List<Item>>
    val itemListLiveData : LiveData<List<Item>>
        get() = _itemListLiveData
    private var _itemLiveData = MutableLiveData<Event<Item>>()
    var itemLiveData = _itemLiveData
        get() = _itemLiveData
    var feedUrl: String = ""
    val errors: LiveData<Event<RequestResult>>

    init {
        Timber.d("ItemsDetailViewModel init()")
        RssReaderApp.appComponent.inject(this)
        errors = repository.errorData
    }

    fun selectItem(item: Item){
        _itemLiveData.value = Event(item)
    }

    fun loadItems() {
        Timber.d("Loading items for $feedUrl")
        _itemListLiveData = repository.getItems(feedUrl)
    }

    fun refreshChannel() =  repository.refreshChannel(feedUrl)
    fun getChannel() = repository.getChannel(feedUrl)

}
