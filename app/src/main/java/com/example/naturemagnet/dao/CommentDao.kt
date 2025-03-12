package com.example.naturemagnet.dao

import androidx.room.*
import com.example.naturemagnet.entity.Comment
import com.example.naturemagnet.entity.Post

@Dao
interface CommentDao {
    //Comment
    @Query("SELECT * FROM Comment")
    fun getAllComment(): List<Comment>

    @Insert
    fun insertComments(comment: List<Comment>)

    @Update
    fun updateComment(comment: Comment)

    @Delete
    fun deleteComment(comment: Comment)

}