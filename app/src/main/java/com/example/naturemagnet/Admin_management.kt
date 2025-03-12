package com.example.naturemagnet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.ViewModel.ProductViewModel
import com.example.naturemagnet.adapter.ProdAdminAdapter
import com.example.naturemagnet.adapter.ProductClickListener
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentAdminManagementBinding
import com.example.naturemagnet.entity.Product


class Admin_management : Fragment(), ProductClickListener {

    private lateinit var binding: FragmentAdminManagementBinding
    private lateinit var manager: RecyclerView.LayoutManager
    lateinit var prodList : List<Product>
    lateinit var listener: ProductClickListener
    private lateinit var db : NatureMagnetDB
    private val sharedViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_admin_management,
            container,
            false
        )
        listener = this
        val application = requireNotNull(this.activity).application
        manager = LinearLayoutManager(application)
        db = NatureMagnetDB.getInstance(application)!!
        prodList = db.productDao().getProdAll()
        binding.recyclerView.apply{
            adapter = ProdAdminAdapter(prodList, listener,application)
            layoutManager = manager
        }

        binding.addProdBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_admin_management_to_addProduct)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onProductClick(view: View, product: Product) {
        Log.e("Product Detail", "Product Detail")
        val forProdDetail = MutableLiveData<Product>(product)
        sharedViewModel.setProduct(forProdDetail)
        view.findNavController().navigate(R.id.adminEditProduct)
    }

}