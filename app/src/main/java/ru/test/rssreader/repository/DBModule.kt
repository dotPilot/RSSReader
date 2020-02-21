package ru.test.rssreader.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.test.rssreader.api.RequestResult
import ru.test.rssreader.api.RssService
import ru.test.rssreader.db.*
import ru.test.rssreader.viewmodels.Event
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class DBModule() {

    @Singleton
    @Provides
    fun provideFeedDb(application: Context): FeedDb {
        return Room.databaseBuilder(application, FeedDb::class.java, "rss_feeds.db")
//            .allowMainThreadQueries()
            .createFromAsset("rss_feeds.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideChannelsDao(db: FeedDb): ChannelsDao {
        return db.channelsDao()
    }

    @Singleton
    @Provides
    fun provideItemsDao(db: FeedDb): ItemsDao {
        return db.itemsDao()
    }

    @Singleton
    @Provides
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Singleton
    @Provides
    fun provideRepository(
        itemsDao: ItemsDao,
        channelsDao: ChannelsDao,
        apiService: RssService,
        errorData: MutableLiveData<Event<RequestResult>>
    ): Repository {
        return Repository(itemsDao, channelsDao, apiService, errorData)
    }

    @Singleton
    @Provides
    fun provideErrorData() = MutableLiveData<Event<RequestResult>>()

}