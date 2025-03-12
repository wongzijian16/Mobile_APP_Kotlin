package com.example.naturemagnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.naturemagnet.databinding.ActivityUserSecondpageBinding

class activity_user_secondpage : AppCompatActivity() {

    private lateinit var binding: ActivityUserSecondpageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_secondpage)

        customerloginHere()

        adminloginHere()


    }

    private fun customerloginHere(){
        binding!!.navigationcustomerloginButton.setOnClickListener{
            startActivity(Intent(this, activity_user_login::class.java))
            finish()
        }
    }

    private fun adminloginHere(){
        binding!!.navigationadminloginButton.setOnClickListener{
            startActivity(Intent(this, activity_user_adminlogin::class.java))
            finish()
        }
    }
}