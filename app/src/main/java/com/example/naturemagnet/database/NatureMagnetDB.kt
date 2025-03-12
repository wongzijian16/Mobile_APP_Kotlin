package com.example.naturemagnet.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.naturemagnet.dao.*
import com.example.naturemagnet.datagenerator.SampleDataGenerator
import com.example.naturemagnet.entity.*
import com.example.naturemagnet.typeconverterClass.ImageConverter
import java.util.concurrent.Executors

//Any new entity please add here
@Database(
    entities = [Activity::class, ActivityJoined::class, Admin::class, Category::class, Comment::class, Customer::class,
        News::class, NewsSaved::class, Post::class, PostLiked::class,
        PostSaved::class, Product::class],
    version = 1
)
//Any new converter please add here
@TypeConverters(ImageConverter::class)
abstract class NatureMagnetDB : RoomDatabase () {
    //define your DAO here
    abstract fun activityDao(): ActivityDao
    abstract fun customerDao(): CustomerDao
    abstract fun categoryDao(): CategoryDao
    abstract fun activityJoinedDao(): ActivityJoinedDao
    abstract fun postDao(): PostDao
    abstract fun newsDao(): NewsDao
    abstract fun commentDao(): CommentDao
    abstract fun productDao(): ProductDao

    companion object {
        private var INSTANCE: NatureMagnetDB? = null
        fun getInstance(context: Context): NatureMagnetDB? {
            if (INSTANCE == null) {
                synchronized(NatureMagnetDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NatureMagnetDB::class.java,
                        "natureMagnet.db"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread(Runnable {
                                val db = getInstance(context)!!
                                db.customerDao().insertCustomers(SampleDataGenerator.getCustomers())
                                db.customerDao().insertAdmins(SampleDataGenerator.getAdmins())
                                db.categoryDao().insertCategories(SampleDataGenerator.injectCategories(context))
                                db.activityDao().insertActivities(SampleDataGenerator.injectActivities(context))
                                db.activityJoinedDao().insertActivitiesJoined(SampleDataGenerator.injectActivityJoined())
                                db.postDao().insertPosts(SampleDataGenerator.injectPost(context))
                                db.postDao().insertPostLikeds(SampleDataGenerator.injectPostLiked())
                                db.postDao().insertPostSaveds(SampleDataGenerator.injectPostSaved())
                                db.newsDao().insertNews(SampleDataGenerator.injectNews())
                                db.newsDao().insertNewsSaved(SampleDataGenerator.injectNewsSaved())
                                db.commentDao().insertComments(SampleDataGenerator.injectComment())
                                db.productDao().insertProducts(SampleDataGenerator.getProducts(context))
                            }).start()
                        }
                    }).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}