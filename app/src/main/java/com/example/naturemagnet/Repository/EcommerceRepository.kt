package com.example.naturemagnet.Repository

import com.example.naturemagnet.dao.ProductDao
import com.example.naturemagnet.entity.Product

class EcommerceRepository(private val productDao: ProductDao) {

    fun getAllProd(): List<Product> = productDao.getProdAll()
    suspend fun getProd(product: Product) = productDao.loadProductDetail(product.prodName.toString())
    suspend fun insertProd(product: Product) = productDao.insertProduct(product)
    suspend fun updateProd(product: Product) = productDao.updateProduct(product)
    suspend fun deleteProd(product: Product) = productDao.deleteProduct(product)
}