//package com.example.naturemagnet
//
//import android.content.Context
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import androidx.room.ColumnInfo
//import androidx.room.PrimaryKey
//import androidx.room.Room
//import androidx.test.InstrumentationRegistry.getContext
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import com.example.naturemagnet.database.NatureMagnetDB
//import com.example.naturemagnet.entity.Customer
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//
///**
// * Instrumented test, which will execute on an Android device.
// *
// * See [testing documentation](http://d.android.com/tools/testing).
// */
//@RunWith(AndroidJUnit4::class)
//class ExampleInstrumentedTest  {
//    private lateinit var db : NatureMagnetDB
//    val custTest = db.customerDao()
//
//    @Test
//    fun insertCustomer(){
//        val customers = listOf(
//            Customer("jiahie","jiahielow@gmail.com","0153134124","123asd","jalanMalaysia"),
//            Customer("kotlin","kotlin@gmail.com","0153123124","123asd","kotlinMalaysia"),
//        )
//
//        customers.forEach{custTest?.insertCustomer(it)}
//
//    }
//
//
//
//
//}