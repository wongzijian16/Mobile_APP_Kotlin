package com.example.naturemagnet.dao

import android.graphics.Bitmap
import androidx.room.*
import com.example.naturemagnet.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    fun getProdAll(): List<Product>

    @Query(
        "SELECT * FROM Product WHERE Product.prodName = :productName"
    )
    fun loadProductDetail(productName: String): Product

    @Query(
        "UPDATE Product SET prodName = :newProdName WHERE prodName = :oldProdName"
    )
    fun replaceNewProdName(newProdName: String , oldProdName: String): Void

    @Query(
        "UPDATE Product SET prodPrice = :newProdPrice WHERE prodPrice = :oldProdPrice"
    )
    fun replaceNewProdPrice(newProdPrice: String , oldProdPrice: String): Void

    @Query(
        "UPDATE Product SET prodQuantityStock = :newProdStock WHERE prodQuantityStock = :oldProdStock"
    )
    fun replaceNewProdStock(newProdStock: Int , oldProdStock: Int): Void

    @Query(
        "UPDATE Product SET prodDescription = :newProdDesc WHERE prodDescription = :oldProdDesc"
    )
    fun replaceNewProdDesc(newProdDesc: String, oldProdDesc: String): Void

    @Query(
        "UPDATE Product SET prodImage = :newProdImg WHERE prodName = :prodName"
    )
    fun replaceNewProdImage(newProdImg: Bitmap, prodName: String): Void

    @Query(
        "DELETE FROM Product WHERE Product.prodName = :prodName"
    )
    fun queDelProduct(prodName: String): Void

    @Insert
    fun insertProducts(product: List<Product>)

    @Insert
    fun insertProduct(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

}