package com.example.naturemagnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.naturemagnet.viewmodel.CustomerViewModel
import com.example.naturemagnet.databinding.ActivityUserRegisterBinding
import com.example.naturemagnet.entity.Customer

class activity_user_register : AppCompatActivity() {

    private lateinit var cCustomerViewModel: CustomerViewModel
    private lateinit var binding: ActivityUserRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_register)
        cCustomerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)

        binding.registerButton.setOnClickListener(){
            insertNewCustomer()
        }
        binding.loginlinkButton.setOnClickListener(){
            loginHere()
        }


    }

    private fun insertNewCustomer() {
            binding.apply {
                val name = cName.text.toString()
                val email = cEmail.text.toString()
                val phone = cPhone.text.toString()
                val address = cAddress.text.toString()
                val password = cPassword.text.toString()
                val confirmpassword = cConfirmpassword.text.toString()

                if (validation(name, email, phone, address, password, confirmpassword)) {
                    if(compare(password, confirmpassword)) {
                        val customer = Customer(name, email, phone, password,address)

                        cCustomerViewModel.insertCustomer(customer)

                        Toast.makeText(
                            applicationContext,
                            "Successfully register an account",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(applicationContext, activity_user_firstpage::class.java)
                        startActivity(intent)


                    }

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please Enter Correct Information",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun validation(
        name: String,
        email: String,
        phone: String,
        address: String,
        password: String,
        confirmpassword: String
    ): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(address) || TextUtils.isEmpty(password) || TextUtils.isEmpty(
            confirmpassword
        ))
    }

    private fun compare(password:String, confirmpassword: String):Boolean{
        return if(confirmpassword != password){
            Toast.makeText(applicationContext, "Password not same", Toast.LENGTH_LONG).show()
            false
        }else{
            true
        }

    }

    private fun loginHere(){
        binding!!.loginlinkButton.setOnClickListener{
            startActivity(Intent(this, activity_user_login::class.java))
            finish()
        }
    }
}