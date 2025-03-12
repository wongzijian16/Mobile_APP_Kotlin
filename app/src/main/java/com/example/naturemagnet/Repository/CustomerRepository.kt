package com.example.naturemagnet.repository

import androidx.lifecycle.LiveData
import com.example.naturemagnet.dao.CustomerDao
import com.example.naturemagnet.entity.Customer

class CustomerRepository(private val customerDao: CustomerDao) {

    val getCustAll: LiveData<List<Customer>> = customerDao.getCustAll()

    suspend fun insertCustomer(customer:Customer){
        customerDao.insertCustomer(customer)
    }

    suspend fun loginValidation(email:String):Customer{
        return customerDao.loginValidation(email)
    }

    suspend fun updateCustomer(customer: Customer){
        customerDao.updateCustomer(customer)
    }

    suspend fun deleteCustomer(customer: Customer){
        customerDao.deleteCustomer(customer)
    }

    suspend fun deleteAll(){
        customerDao.deleteAll()
    }
}