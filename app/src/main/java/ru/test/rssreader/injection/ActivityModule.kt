package ru.test.rssreader.injection

import dagger.Module
import dagger.Provides
import ru.test.rssreader.ui.channeldetail.ItemListAdapter
import ru.test.rssreader.ui.channellist.ChannelsListAdapter

@Module
class ActivityModule {

    @Provides
    fun providesChannelsListAdapter() = ChannelsListAdapter()
    @Provides
    fun providesItemListAdapter() = ItemListAdapter()
}