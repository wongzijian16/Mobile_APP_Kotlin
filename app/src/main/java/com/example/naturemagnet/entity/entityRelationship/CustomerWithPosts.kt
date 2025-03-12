package com.example.naturemagnet.entity.entityRelationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.naturemagnet.entity.Customer
import com.example.naturemagnet.entity.Post


data class CustomerWithPosts (
    @Embedded val cust: Customer,
    @Relation(
        parentColumn = "custID",
        entityColumn = "custID"
    )
    val post: List<Post>
)