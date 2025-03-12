package com.example.naturemagnet.entity

import androidx.room.*

@Entity(
    tableName = "news",
    indices = [Index(value = ["newsID"], unique = true)]
)
data class News(
    @PrimaryKey(autoGenerate = true) var newsID: Long,
    val title: String?,
    val newsLink: String?,
    val sourceName: String?,
    val publishedDate: String?
){
    constructor(title: String, newsLink: String, sourceName: String, publishedDate: String)
            : this(0, title, newsLink, sourceName, publishedDate)
}
