package com.example.naturemagnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.naturemagnet.databinding.ActivityUserFirstpageBinding
import com.example.naturemagnet.databinding.ActivityUserLoginBinding
import com.example.naturemagnet.databinding.ActivityUserRegisterBinding

class activity_user_firstpage : AppCompatActivity() {

    private lateinit var binding: ActivityUserFirstpageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_firstpage)


        pleaseloginHere()


        pleaseregisterHere()
    }

    private fun pleaseloginHere(){
        binding!!.navigationloginButton.setOnClickListener{
            startActivity(Intent(this, activity_user_secondpage::class.java))
            finish()
        }
    }

    private fun pleaseregisterHere(){
        binding!!.navigationregisterButton.setOnClickListener{
            startActivity(Intent(this, activity_user_register::class.java))
            finish()
        }
    }
}