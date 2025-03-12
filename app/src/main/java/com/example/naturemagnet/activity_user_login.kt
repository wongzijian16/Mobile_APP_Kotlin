package com.example.naturemagnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.naturemagnet.viewmodel.CustomerViewModel
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.ActivityUserLoginBinding

class activity_user_login : AppCompatActivity() {

    private lateinit var binding: ActivityUserLoginBinding
    private lateinit var cCustomerViewModel: CustomerViewModel
    private lateinit var prefManager: PrefManager
    private lateinit var email1: EditText
    private lateinit var password1: EditText
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var db : NatureMagnetDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_login)
        cCustomerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        init()

        registerHere()
        binding.loginButton.setOnClickListener(){clickLogin(it)}
    }

    private fun init(){
        prefManager = PrefManager(this)
        email1 = findViewById(R.id.email1)
        password1 = findViewById(R.id.password1)
    }

//    private fun checkLogin(){
//        if(clickLogin()){
//            if (prefManager.isLogin()!!) {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }

    fun clickLogin(view : View){
        email = email1.text.toString().trim()
        password = password1.text.toString().trim()



        if (email.isEmpty() || email == ""){
            email1.error = "Please enter the email"
            email1.requestFocus()
        }else if (password.isEmpty() || password == ""){
            password1.error = "Please enter the password"
            password1.requestFocus()
        }else{
            db = NatureMagnetDB.getInstance(application)!!
            val selectedCus = db.customerDao().loginValidation(email)

            if (selectedCus != null){
                if(selectedCus.custEmail == email && selectedCus.password == password){
                    prefManager.setLogin(true)
                    prefManager.setEmail(email)
                    prefManager.setId(selectedCus.custID)
                    Toast.makeText(
                        applicationContext,
                        "Successfully Log In",
                        Toast.LENGTH_LONG
                    ).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(
                        applicationContext,
                        "Please Enter Correct Email/Password",
                        Toast.LENGTH_LONG
                    ).show()
                }


            }else{
                Toast.makeText(
                    applicationContext,
                    "Please Enter Correct Email/Password",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun registerHere(){
        binding!!.registerlinkButton.setOnClickListener{
            startActivity(Intent(this, activity_user_register::class.java))
            finish()
        }
    }
}