package com.example.naturemagnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.naturemagnet.viewmodel.CustomerViewModel
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.ActivityUserAdminloginBinding

class activity_user_adminlogin : AppCompatActivity() {

    private lateinit var binding: ActivityUserAdminloginBinding
    private lateinit var cCustomerViewModel: CustomerViewModel
    private lateinit var prefManager: PrefManager
    private lateinit var email2: EditText
    private lateinit var password2: EditText
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var db : NatureMagnetDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_adminlogin)
        cCustomerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        init()

        binding.loginButton.setOnClickListener{
            clickLogin(it)
        }
    }

    private fun init(){
        prefManager = PrefManager(this)
        email2 = findViewById(R.id.email2)
        password2 = findViewById(R.id.password2)
    }

    fun clickLogin(view : View){
        email = email2.text.toString().trim()
        password = password2.text.toString().trim()



        if (email.isEmpty() || email == ""){
            email2.error = "Please enter the email"
            email2.requestFocus()
        }else if (password.isEmpty() || password == ""){
            password2.error = "Please enter the password"
            password2.requestFocus()
        }else{
            db = NatureMagnetDB.getInstance(application)!!
            val selectedAdmin = db.customerDao().loginValidation1(email)

            Log.e("Selected Cus", selectedAdmin.toString())

            if (selectedAdmin != null){
                if(selectedAdmin.adminEmail == email && selectedAdmin.password == password){
                    prefManager.setLogin(true)
                    prefManager.setEmail1(email)
                    prefManager.setId(selectedAdmin.adminID)
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
                        "Please Enter Correct Email/Password 1",
                        Toast.LENGTH_LONG
                    ).show()
                }


            }else{
                Toast.makeText(
                    applicationContext,
                    "Please Enter Correct Email/Password 2",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}