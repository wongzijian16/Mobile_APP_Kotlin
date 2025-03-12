package com.example.naturemagnet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.naturemagnet.ViewModel.ProductViewModel
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentProdDetailBinding

class fragment_prodDetail : Fragment() {

    lateinit var binding : FragmentProdDetailBinding
    private val sharedViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_prod_detail,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val db = NatureMagnetDB.getInstance(application)!!.productDao()

        binding.prodDetail = sharedViewModel.product.value
        binding.imgFull.setImageBitmap(sharedViewModel.product.value?.prodImage)

        Log.e("Tag", sharedViewModel.product.value?.prodName.toString())

        return binding.root
    }
}