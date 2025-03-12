package com.example.naturemagnet.entity.entityRelationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.entity.Customer
import com.example.naturemagnet.entity.NewsSaved

data class CustomerWithNewsSaved(
    @Embedded val cust: Customer,
    @Relation(
        parentColumn = "custID",
        entityColumn = "custID"
    )
    val newsSaved: List<NewsSaved>
)