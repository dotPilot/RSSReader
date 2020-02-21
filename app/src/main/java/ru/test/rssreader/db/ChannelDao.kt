package ru.test.rssreader.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.test.rssreader.db.entities.Channel

@Dao
abstract class ChannelsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(channel: Channel)

    @Query("SELECT * FROM channels")
    abstract suspend fun getChannelsList(): List<Channel>

    @Query("SELECT * FROM channels")
    abstract fun  getChannelsListLive(): LiveData<List<Channel>>

    @Delete
    abstract suspend fun delete(channel: Channel)

    @Query("SELECT * FROM channels WHERE feedUrl = :feedUrl")
    abstract fun getChannel(feedUrl: String): Channel
}