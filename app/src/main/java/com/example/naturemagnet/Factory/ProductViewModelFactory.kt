//package com.example.naturemagnet.Factory
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.naturemagnet.Repository.EcommerceRepository
//import com.example.naturemagnet.ViewModel.ProductViewModel
//
//class ProductViewModelFactory(private val repository: EcommerceRepository): ViewModelProvider.Factory  {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
//            return ProductViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}