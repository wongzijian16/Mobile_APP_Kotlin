package com.example.naturemagnet.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "customer",
    indices = [Index(value = ["custEmail"], unique = true), Index(
        value = ["custID"],
        unique = true
    )]
)
data class Customer(
    @PrimaryKey(autoGenerate = true) var custID: Long,
    @ColumnInfo(name = "custName") val custName: String?,
    @ColumnInfo(name = "custEmail") val custEmail: String,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "address") val address: String?
) {
    constructor(
        custName: String, custEmail: String, phone: String, password: String,
        address: String
    ) : this(0, custName, custEmail, phone, password, address)
    constructor(
        custName: String, custEmail: String, phone: String,
        address: String
    ) : this(0, custName, custEmail, phone,"", address)
    constructor(
        custID: Long, custName: String, custEmail: String, phone: String,
        address: String
    ) : this(custID, custName, custEmail, phone,"", address)
    constructor(
        password: String
    ) : this(0, "", "", "", password, "")
}