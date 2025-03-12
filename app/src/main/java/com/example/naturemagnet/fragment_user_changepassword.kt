package com.example.naturemagnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.naturemagnet.dao.CustomerDao
import com.example.naturemagnet.viewmodel.CustomerViewModel
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentUserChangepasswordBinding
import com.example.naturemagnet.entity.Customer


class fragment_user_changepassword : Fragment() {

    private lateinit var cCustomerViewModel: CustomerViewModel
    private lateinit var binding: FragmentUserChangepasswordBinding
    private lateinit var prefManager: PrefManager
    private lateinit var db : NatureMagnetDB
    private lateinit var customer : Customer
    private lateinit var cCustomerDao: CustomerDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_changepassword, container, false)


        cCustomerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)

        binding.confirmButton.setOnClickListener{
            changePassword()
            findNavController().navigate(R.id.fragment_user_main)
        }
        val application = requireNotNull(this.activity).application

        prefManager = PrefManager(this.requireActivity())
        db = NatureMagnetDB.getInstance(application)!!
        cCustomerDao = db.customerDao()
        customer = cCustomerDao.loginValidation(prefManager.getEmail().toString())


        return binding.root
    }

    private fun changePassword() {
        val password = binding.newPasswordText.text.toString()
        val confirmpassword = binding.confirmPasswordText.text.toString()


        if (compare(password, confirmpassword)) {

            cCustomerDao.updateCust1(password, prefManager.getId()!!)


            Toast.makeText(
                context,
                "Successfully Change Password",
                Toast.LENGTH_LONG
            ).show()


        }
    }

    private fun compare(password:String, confirmpassword: String):Boolean{
        return if(confirmpassword != password){
            Toast.makeText(context, "Password not same", Toast.LENGTH_LONG).show()
            false
        }else{
            true
        }

    }


}