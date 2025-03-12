package com.example.naturemagnet.entity

import android.graphics.Bitmap
import androidx.room.*

//one customer can have many comments
//one post can have many comments
@Entity(
    tableName = "comment",
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = arrayOf("custID"),
            childColumns = arrayOf("custID"),
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Post::class,
            parentColumns = arrayOf("postID"),
            childColumns = arrayOf("postID"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["commentID"], unique = true)]
)

data class Comment(
    @PrimaryKey(autoGenerate = true) var commentID: Long,
    val content: String?,
    val commentDatetime: String?,
    val eventLink: String?,
    @ColumnInfo(index = true) var custID: Long,
    @ColumnInfo(index = true) var postID: Long,
){
    constructor(content: String, commentDatetime: String, eventLink: String?, custID: Long, postID: Long)
            : this(0, content, commentDatetime, eventLink, custID, postID)
}
