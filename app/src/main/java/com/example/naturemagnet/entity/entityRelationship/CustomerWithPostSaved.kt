package com.example.naturemagnet.entity.entityRelationship

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.naturemagnet.entity.Customer
import com.example.naturemagnet.entity.Post
import com.example.naturemagnet.entity.PostSaved

data class CustomerWithPostSaved(

    @Embedded val cust: Customer,
    @Relation(
        parentColumn = "custID",
        entityColumn = "postID",
        associateBy = Junction(PostSaved::class)
    )
    val post: List<Post>
)
