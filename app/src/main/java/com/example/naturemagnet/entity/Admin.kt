package com.example.naturemagnet.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "admin",
    indices = [Index(value = ["adminEmail"], unique = true),Index(value = ["adminID"], unique = true)],
)
data class Admin(
    @PrimaryKey(autoGenerate = true) val adminID: Long,
    val adminName: String?,
    val adminEmail: String,
    val address: String?,
    val phone: String?,
    val password: String?,
){
    constructor(adminName: String, adminEmail: String, address: String, phone:String,
                password: String) : this(0,adminName, adminEmail, address, phone, password)
}
