package ru.test.rssreader.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey
    var feedUrl: String,
    var link: String,
    var title: String,
    var description: String,
    var imageUrl: String?
)
