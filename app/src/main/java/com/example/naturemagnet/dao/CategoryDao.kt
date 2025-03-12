package com.example.naturemagnet.dao

import androidx.room.*
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE Category.categoryID == :categoryId")
    fun getCategory(categoryId: Long): Category

    @Insert
    fun insertCategory(category: Category)

    @Insert
    fun insertCategories(category: List<Category>)

    @Update
    fun updateCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)
}