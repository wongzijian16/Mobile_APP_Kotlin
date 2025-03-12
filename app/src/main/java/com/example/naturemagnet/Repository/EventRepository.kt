package com.example.naturemagnet.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.naturemagnet.dao.ActivityDao
import com.example.naturemagnet.dao.ActivityJoinedDao
import com.example.naturemagnet.dao.CategoryDao
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.entity.ActivityJoined
import com.example.naturemagnet.entity.Category

class EventRepository(private val activityDao: ActivityDao, private val categoryDao: CategoryDao, private val activityJoinedDao: ActivityJoinedDao) {
    /** activity */
    fun getAllActivities(): List<Activity> = activityDao.getAll()
    fun getActivity(activityId: Long): Activity = activityDao.getActivity(activityId)
    fun getActivitiesByDate(date: String): List<Activity> {
        var activities = getAllActivities().toMutableList()
        var activitiesWanted: ArrayList<Activity> = ArrayList()
        var index = 0
        for (a in activities){
            var currentActivityDate: String = a.dateTime?.subSequence(0, 10) as String
            currentActivityDate = currentActivityDate.replace('-', '/')
            Log.e("eventRepository: A >", currentActivityDate)
            Log.e("eventRepository: Date >", date)
            if (currentActivityDate == date ){
                activitiesWanted.add(index, a)
                index++
            }
        }
        return activitiesWanted.toList()
    }
    suspend fun insertActivity(activity: Activity) {
        activityDao.insertActivity(activity)
    }
    suspend fun insertActivities(activities: List<Activity>) {
        return activityDao.insertActivities(activities)
    }
    fun updateActivity(activity: Activity) {
        return activityDao.updateUsers(activity)
    }
    suspend fun deleteActivity(activity: Activity) {
        return activityDao.deleteActivity(activity)
    }

    /** category */
    fun getAllCategories(): List<Category> = categoryDao.getAll()
    fun getCategory(categoryId: Long): Category = categoryDao.getCategory(categoryId)
    suspend fun insertCategory(category: Category) {
        return categoryDao.insertCategory(category)
    }
    suspend fun insertCategories(category: List<Category>) {
        return categoryDao.insertCategories(category)
    }
    suspend fun updateCategory(category: Category) {
        return categoryDao.updateCategory(category)
    }
    suspend fun deleteCategory(category: Category) {
        return categoryDao.deleteCategory(category)
    }

    /** activitiesJoined */
    fun getAllActivitiesJoined(): List<ActivityJoined> = activityJoinedDao.getAll()
    fun getActivityJoined(activityId: Long): ActivityJoined = activityJoinedDao.getActivityJoined(activityId)
    fun insertActivityJoined(activityJoined: ActivityJoined) {
        activityJoinedDao.insertActivityJoined(activityJoined)
    }
    fun insertActivitiesJoined(activitiesJoined: List<ActivityJoined>){
        activityJoinedDao.insertActivitiesJoined(activitiesJoined)
    }
    fun deleteActivityJoined(activityId: Long, custId: Long) {
        Log.e("Event_Repo", activityId.toString()+" = "+custId.toString())
        activityJoinedDao.deleteActivityJoined(activityId, custId)
    }
    fun getActivitiesJoinedByUser(userId: Long): List<Activity> = activityDao.getActivitiesJoinedByUser(userId)
}