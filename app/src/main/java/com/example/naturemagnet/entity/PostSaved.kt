package com.example.naturemagnet.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "postSaved",
    primaryKeys = ["custID", "postID"],
    indices = [Index(value = ["custID","postID"], unique = true)],
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
    ]
)

data class PostSaved(
    val custID: Long,
    val postID: Long,
    val savedDateTime: String?
){
    constructor(cusID:Long, postID: Long)
            : this(cusID, postID,"")
}
