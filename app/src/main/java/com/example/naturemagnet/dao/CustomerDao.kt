package com.example.naturemagnet.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.entity.Admin
import com.example.naturemagnet.entity.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM Customer")
    fun getCustAll(): LiveData<List<Customer>>

    @Query("SELECT * FROM Customer")
    fun getAllCus(): List<Customer>

    @Query("SELECT * FROM Admin")
    fun getAllAdmin(): List<Admin>

    @Query("SELECT * FROM Customer Where Customer.custID = :cusID")
    fun getCust(cusID:Long): Customer

    @Insert
    fun insertCustomers(customer: List<Customer>)

    @Insert
    fun insertAdmins(admin: List<Admin>)

    @Insert
    suspend fun insertCustomer(customer: Customer)

    @Query("SELECT * FROM Customer WHERE custEmail = :email")
    fun loginValidation(email: String):Customer

    @Query("SELECT * FROM Admin WHERE adminEmail = :email")
    fun loginValidation1(email: String):Admin

    @Update
    fun updateCustomer(customer: Customer)

    @Query("UPDATE Customer SET custName = :name, phone = :phone, address = :address WHERE custID= :id")
    fun updateCust(name: String, phone: String, address: String, id: Long)

    @Query("UPDATE Customer SET password = :password WHERE custID= :id")
    fun updateCust1(password: String, id: Long)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("DELETE FROM Customer")
    suspend fun deleteAll()
}