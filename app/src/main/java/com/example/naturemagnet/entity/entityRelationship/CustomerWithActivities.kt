package com.example.naturemagnet.entity.entityRelationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.entity.Customer

//one customer can have many activities
data class CustomerWithActivities(
    @Embedded val cust: Customer,
    @Relation(
        parentColumn = "custID",
        entityColumn = "custID"
    )
    val activity: List<Activity>
)
