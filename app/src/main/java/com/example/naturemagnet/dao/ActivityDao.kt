package com.example.naturemagnet.dao

import androidx.room.*
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.entity.Customer
import com.example.naturemagnet.entity.entityRelationship.CustomerWithActivities

@Dao
interface ActivityDao {
    @Query("SELECT * FROM Activity")
    fun getAll(): List<Activity>

    @Query("SELECT * FROM Activity WHERE Activity.activityID == :activityId")
    fun getActivity(activityId: Long): Activity

    @Query(
    "SELECT * FROM Activity " +
            "INNER JOIN ActivityJoined ON ActivityJoined.activityID = Activity.activityID " +
            "WHERE ActivityJoined.custID == :userId"
    )
    fun getActivitiesJoinedByUser(userId: Long): List<Activity>

    @Insert
    fun insertActivity(activity: Activity)

    @Insert
    fun insertActivities(activity: List<Activity>)

    @Update
    fun updateUsers(activity: Activity)

    @Delete
    fun deleteActivity(activity: Activity)

    //Example - Intermediate data class
//    @Transaction
//    @Query("SELECT * FROM Customer WHERE custID = :custID")
//    fun getCustomerWithActivities(custID: Long): List<CustomerWithActivities>

    //Example - Multimap return types
//    @Query("SELECT * FROM Customer JOIN Activity ON Customer.custID = Activity.custID")
//    fun loadUserAndBookNames(): Map<Customer, List<Activity>>
}