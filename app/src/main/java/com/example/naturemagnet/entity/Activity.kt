package com.example.naturemagnet.entity

import android.graphics.Bitmap
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

//one to many (one category can have many activity)
@Entity(
    tableName = "activity",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = arrayOf("categoryID"),
        childColumns = arrayOf("categoryID"),
        onDelete = CASCADE
    ), ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("custID"),
        childColumns = arrayOf("custID"),
        onDelete = CASCADE
    )],
    indices = [Index(value = ["activityID"], unique = true)]

)

data class Activity(
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "datetime") var dateTime: String?,
    @ColumnInfo(name = "descriptions") var descriptions: String?,
    @ColumnInfo(name = "registration_deadline") var registrationDeadline: String?,
    @ColumnInfo(name = "date_created") val dateCreated: String?,
    @ColumnInfo(name = "venue") var venue: String?,
    @ColumnInfo(name = "sneak_peek") var sneakPeek: Bitmap?,
    @ColumnInfo(name = "participants") var participants: Int?,
    @ColumnInfo(index = true) val custID: Long,
    @ColumnInfo(index = true) val categoryID: Long
) {
    @PrimaryKey(autoGenerate = true)
    var activityID: Long = 0L

    constructor(): this (
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        null,
        0,
        1,
        1)

    constructor(sneakPeek: Bitmap?, title: String?, participants: Int?) : this(
        "example",
        title,
        "example",
        "example",
        "example",
        "example",
        "example",
        sneakPeek,
        participants,
        2,
        1
    )
}

