package com.example.naturemagnet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.naturemagnet.ViewModel.ProductViewModel
import com.example.naturemagnet.databinding.FragmentAdminManagementComponentBinding

class AdminManagementComponent : Fragment() {

    private lateinit var binding: FragmentAdminManagementComponentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_admin_management_component,
            container,
            false
        )

        // Inflate the layout for this fragment
        return binding.root
    }
}