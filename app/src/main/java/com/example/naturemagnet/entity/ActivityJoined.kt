package com.example.naturemagnet.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(
    tableName = "activityJoined",
    primaryKeys = ["custID", "activityID"],
    indices = [Index(value = ["custID", "activityID"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = arrayOf("custID"),
            childColumns = arrayOf("custID"),
            onDelete = CASCADE
        ), ForeignKey(
            entity = Activity::class,
            parentColumns = arrayOf("activityID"),
            childColumns = arrayOf("activityID"),
            onDelete = CASCADE
        )
    ]
)
data class ActivityJoined(
    @ColumnInfo(name = "custID") val custID: Long,
    @ColumnInfo(name = "activityID") val activityID: Long,
    @ColumnInfo(name = "dateJoined") val dateJoined: String
)
