package com.example.naturemagnet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.naturemagnet.viewmodel.CustomerViewModel
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentUserMainBinding
import com.example.naturemagnet.entity.Customer


class fragment_user_main : Fragment() {

    private lateinit var cCustomerViewModel: CustomerViewModel
    private lateinit var binding: FragmentUserMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var email : String
    private lateinit var phone : String
    private lateinit var address : String
    private lateinit var db : NatureMagnetDB
    private lateinit var customer : Customer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_main, container, false)


        cCustomerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)

        val application = requireNotNull(this.activity).application

        binding.logoutButton.setOnClickListener{
            clickLogout(it)
        }
        prefManager = PrefManager(this.requireActivity())
        db = NatureMagnetDB.getInstance(application)!!


        setupUI()

        binding.editprofileButton.setOnClickListener{
            findNavController().navigate(R.id.fragment_user_editprofile)
        }
        binding.changepasswordButton.setOnClickListener{
            findNavController().navigate(R.id.fragment_user_changepassword)
        }
//        binding.deleteaccountButton.setOnClickListener{
//            findNavController().navigate(R.id.fragment_user_deleteaccount)
//        }
        return binding.root

    }

    private fun init(){

    }

//    private fun checkLogin(){
//        if(prefManager.isLogin() == false){
//            val intent = Intent(activity, activity_user_login::class.java)
//            startActivity(intent)
//        }
//    }

    private fun setupUI(){
        customer = db.customerDao().loginValidation(prefManager.getEmail().toString())
        binding.nameText.text = customer.custName
        binding.emailText.text = customer.custEmail
        binding.phoneText.text =customer.phone
        binding.addressText.text = customer.address
    }

    fun clickLogout(view: View){
        prefManager.removeData()
        val intent = Intent(activity, activity_user_firstpage::class.java)
        startActivity(intent)
    }

}