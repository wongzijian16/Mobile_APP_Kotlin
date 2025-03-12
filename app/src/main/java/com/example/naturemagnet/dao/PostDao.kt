package com.example.naturemagnet.dao

import android.graphics.Bitmap
import androidx.room.*
import com.example.naturemagnet.entity.*
import com.example.naturemagnet.entity.entityRelationship.CustomerWithActivities
import com.example.naturemagnet.entity.entityRelationship.CustomerWithNewsSaved
import com.example.naturemagnet.entity.entityRelationship.CustomerWithPostLiked
import com.example.naturemagnet.entity.entityRelationship.CustomerWithPostSaved

@Dao
interface PostDao {

    //Post
    @Query("SELECT * FROM Post")
    fun getAllPost(): List<Post>

    @Query("SELECT * FROM Post ORDER BY RANDOM() LIMIT 4")
    fun getRandomPost(): List<Post>


    @Query("SELECT * FROM Post WHERE Post.postID = :postID")
    fun getPost(postID:Long): Post

    @Query("SELECT * FROM Post WHERE Post.custID = :cusID")
    fun getCusPost(cusID: Long): List<Post>

    @Query("DELETE FROM Post WHERE Post.postID = :postID")
    fun deleteCusPost(postID: Long)

    @Query("UPDATE Post SET title = :title, content = :content, imgPost= :imgPost WHERE postID= :id")
    fun updatePost(id:Long, title:String, content:String, imgPost:Bitmap)

    @Insert
    fun insertPosts(post: List<Post>)

    @Insert
    fun insertPost(post: Post)

    @Delete
    fun deletePost(post: Post)

    //PostSaved
    @Query("SELECT * FROM PostSaved WHERE PostSaved.postID = :postID AND PostSaved.custID = :custID")
    fun getCusPostSaved(postID:Long, custID: Long) : PostSaved

    @Insert
    fun insertPostSaveds(postSaved: List<PostSaved>)

    @Insert
    fun insertPostSaved(postSaved: PostSaved)

    @Delete
    fun deletePostSaved(postSaved: PostSaved)


    //PostLiked
    @Query("SELECT * FROM PostLiked WHERE PostLiked.postID = :postID AND PostLiked.custID = :custID")
    fun getCusPostLiked(postID:Long, custID: Long) : PostLiked

    @Insert
    fun insertPostLikeds(postLiked: List<PostLiked>)

    @Insert
    fun insertPostLiked(postLiked: PostLiked)

    @Delete
    fun deletePostLiked(postLiked: PostLiked)


    //Example - Intermediate data class
    @Transaction
    @Query("SELECT * FROM Customer WHERE custID = :custID")
    fun getCustomerWithPostSaved(custID: Long): List<CustomerWithPostSaved>

    @Transaction
    @Query("SELECT * FROM Customer WHERE custID = :custID")
    fun getCustomerWithPostLiked(custID: Long): List<CustomerWithPostLiked>

    @Transaction
    @Query("SELECT * FROM Customer WHERE custID = :custID")
    fun getCustomerWithNewsSaved(custID: Long): List<CustomerWithNewsSaved>

}