package com.example.naturemagnet.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naturemagnet.dao.ProductDao
import com.example.naturemagnet.entity.Product

class ProductViewModel () : ViewModel(){

    private var _product = MutableLiveData<Product>()

    val product: LiveData<Product>
        get() = _product

    fun setProduct(product: MutableLiveData<Product>) {
        _product = product
    }

}