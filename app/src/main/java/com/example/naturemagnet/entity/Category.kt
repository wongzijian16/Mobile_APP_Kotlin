package com.example.naturemagnet.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "category",
    indices = [Index(value = ["categoryID"], unique = true)]
)
data class Category(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo (name = "description") val description: String?,
    @ColumnInfo (name = "icon") val icon: Bitmap?
){
    @PrimaryKey (autoGenerate = true) var categoryID: Long = 0
}