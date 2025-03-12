package com.example.naturemagnet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.naturemagnet.repository.CustomerRepository
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.entity.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerViewModel(application: Application): AndroidViewModel(application) {

    private val getCustAll: LiveData<List<Customer>>
    private val repository: CustomerRepository

    init{
        val customerDao = NatureMagnetDB.getInstance(application)!!.customerDao()
        repository = CustomerRepository(customerDao)
        getCustAll = repository.getCustAll

    }

    fun insertCustomer(customer:Customer){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertCustomer(customer)
        }
    }

    fun loginValidation(email: String): Customer? {
        var checker: Customer? = null
        viewModelScope.launch(Dispatchers.IO) {
            checker = repository.loginValidation(email)
        }
        return checker
    }

    fun updateCustomer(customer: Customer){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateCustomer(customer)
        }
    }

    fun deleteCustomer(customer: Customer){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCustomer(customer)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}