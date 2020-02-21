package ru.test.rssreader.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.test.rssreader.db.entities.Item

@Dao
abstract class ItemsDao {

    @Transaction
    open fun insert(items: List<Item>) {
        for (item in items) {
            insert(item)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: Item)

    @Update
    abstract fun updateItem(item: Item)

    @Query("SELECT * FROM items WHERE channelKey = :channelKey ORDER BY pubDate DESC")
    abstract suspend fun getAll(channelKey: String): List<Item>

    @Query("SELECT * FROM items WHERE channelKey = :channelKey ORDER BY pubDate DESC")
    abstract fun getAllLive(channelKey: String): LiveData<List<Item>>

    @Delete
    abstract suspend fun delete(items: List<Item>)
}