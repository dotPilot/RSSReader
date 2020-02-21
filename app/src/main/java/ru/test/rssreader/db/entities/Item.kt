package ru.test.rssreader.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(
    @PrimaryKey
    var link: String,
    var channelKey: String,
    var pubDate: Long,
    var title: String,
    var description: String,
    var read: Int
)