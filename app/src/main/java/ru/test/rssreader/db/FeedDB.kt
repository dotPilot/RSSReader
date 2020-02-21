package ru.test.rssreader.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.test.rssreader.db.entities.Channel
import ru.test.rssreader.db.entities.Item

@Database(entities = arrayOf(Channel::class, Item::class),
    version = 1,
    exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class FeedDb : RoomDatabase() {

    abstract fun channelsDao(): ChannelsDao

    abstract fun itemsDao(): ItemsDao

}