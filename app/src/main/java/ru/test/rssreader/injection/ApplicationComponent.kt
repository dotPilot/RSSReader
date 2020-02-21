package ru.test.rssreader.injection

import dagger.Component
import ru.test.rssreader.repository.DBModule
import ru.test.rssreader.ui.channeldetail.ItemListActivity
import ru.test.rssreader.ui.channeldetail.ItemListFragment
import ru.test.rssreader.ui.channellist.ChannelsListActivity
import ru.test.rssreader.viewmodels.ItemsDetailViewModel
import ru.test.rssreader.viewmodels.ChannelsListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [DBModule::class, ApiModule::class, AppModule::class, ActivityModule::class])
interface ApplicationComponent {
    fun inject(activity: ChannelsListActivity)
    fun inject(viewmodel: ChannelsListViewModel)
    fun inject(itemsDetailViewModel: ItemsDetailViewModel)
    fun inject(activity: ItemListActivity)
    fun inject(fragment: ItemListFragment)
}