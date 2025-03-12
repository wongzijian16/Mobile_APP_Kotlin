package com.example.naturemagnet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.naturemagnet.ViewModel.ProductViewModel
import com.example.naturemagnet.adapter.ProductAdapter
import com.example.naturemagnet.adapter.ProductClickListener
import com.example.naturemagnet.dao.ProductDao
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentShopBinding
import com.example.naturemagnet.entity.Product

class fragment_shop : Fragment(), ProductClickListener {

    private lateinit var binding: FragmentShopBinding
    private lateinit var prodDao: ProductDao
    lateinit var prodList : List<Product>
    lateinit var listener: ProductClickListener
    private val sharedViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shop,
            container,
            false
        )
        listener = this
        val application = requireNotNull(this.activity).application
        val manager = GridLayoutManager(application, 2)
        prodDao = NatureMagnetDB.getInstance(application)!!.productDao()
        prodList = prodDao.getProdAll()
        binding.recyclerView.apply{
            adapter = ProductAdapter(prodList, listener,application)
            layoutManager = manager
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onProductClick(view: View, product: Product) {
        Log.e("Product Detail", "Product Detail")
        val forProdDetail = MutableLiveData<Product>(product)
        sharedViewModel.setProduct(forProdDetail)
        view.findNavController().navigate(R.id.fragment_prodDetail)

    }
}