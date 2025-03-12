package com.example.naturemagnet.dao

import androidx.room.*
import com.example.naturemagnet.entity.Admin

@Dao
interface AdminDao {
    @Query("SELECT * FROM Admin")
    fun getAdminAll(): List<Admin>

    @Insert
    fun insertAdmin(admin: List<Admin>)

    @Update
    fun updateAdmin(admin: Admin)

    @Delete
    fun deleteAdmin(admin: Admin)
}