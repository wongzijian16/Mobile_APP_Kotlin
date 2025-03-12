package com.example.naturemagnet.dao

import androidx.room.*
import com.example.naturemagnet.entity.News
import com.example.naturemagnet.entity.NewsSaved

@Dao
interface NewsDao {

    //General Query
    @Query("SELECT * FROM News")
    fun getAllNews(): List<News>

    @Query("SELECT * FROM News WHERE News.newsID = :newsID")
    fun getNews(newsID : Long): News

    @Insert
    fun insertNews(news: List<News>)

    @Update
    fun updateNews(news: News)

    @Delete
    fun deleteNews(news: News)

    //NewsSaved
    @Insert
    fun insertNewsSaved(newsSaved : List<NewsSaved>)

    @Insert
    fun insertCusNewsSaved(newsSaved :NewsSaved)

    @Delete
    fun deleteNewsSaved(newsSaved: NewsSaved)







}