package com.example.naturemagnet.entity

import android.graphics.Bitmap
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.naturemagnet.typeconverterClass.ImageConverter


//one to many (one customer can have many posts)
@Entity(
    tableName = "post",
    indices = [Index(value = ["postID"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("custID"),
        childColumns = arrayOf("custID"),
        onDelete = CASCADE
    )]
)
data class Post(
    @PrimaryKey(autoGenerate = true) var postID: Long,
    val title: String?,
    val content: String?,
    var imgPost: Bitmap?,
    var eventLink: String?,
    val shareCount: Int?,
    val postDate: String?,
    @ColumnInfo(index = true) var custID: Long
){
    constructor(title: String, content: String, imgPost: Bitmap?, eventLink: String?, shareCount: Int, postDate: String, custID: Long)
            : this(0, title, content, imgPost, eventLink, shareCount, postDate, custID)

    constructor(title: String, content: String, imgPost: Bitmap?)
            : this(0, title, content, imgPost, "", 0, "", 0)
}
