package com.example.naturemagnet.entity

import android.graphics.Bitmap
import androidx.room.*

@Entity(
    tableName = "product",
    foreignKeys = [
        ForeignKey(
            entity = Admin::class,
            parentColumns = arrayOf("adminID"),
            childColumns = arrayOf("adminID"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["productID"], unique = true)],
)

data class Product(
    val prodName: String?,
    val prodPrice: String?,
    val prodDescription: String?,
    val prodQuantityStock: Int?,
    val prodImage: Bitmap?,
    @ColumnInfo(index = true) var adminID: Long
){
    @PrimaryKey(autoGenerate = true) var productID: Long=0
}