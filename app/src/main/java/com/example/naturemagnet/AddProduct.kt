package com.example.naturemagnet

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentAddProductBinding
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.entity.Product

class AddProduct : Fragment() {

    lateinit var bitmap : Bitmap
    private lateinit var binding: FragmentAddProductBinding


    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val db = NatureMagnetDB.getInstance(application)!!.productDao()

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_product,
            container,
            false
        )

        binding.addProdImg.setOnClickListener {
            pickImageGallery()
        }

        binding.btnConfirmCreateProd.setOnClickListener {
            var successful = true;
            val productName = binding.inputProdName.text.toString().trim()
            val productPrice = binding.inputProdPrice.text.toString().trim()
            val productStock = binding.inputProdStock.text.toString().toInt()
            val productDescription = binding.inputProdDesc.text.toString().trim()
            var imgUploaded = bitmap

            if (productName.isEmpty()) {
                binding.inputProdName.error = "This field cannot be empty"
                successful = false
            }
            if (productPrice.isEmpty()) {
                binding.inputProdPrice.error = "This field cannot be empty"
                successful = false
            }
            if (productStock == 0) {
                binding.inputProdStock.error = "This field cannot be empty"
                successful = false
            }
            if (productDescription.isEmpty()) {
                binding.inputProdDesc.error = "This field cannot be empty"
                successful = false
            }
            if(imgUploaded == null){
                Toast.makeText(application, "Please pick an image", Toast.LENGTH_SHORT).show();
            }
            if(successful == true){
                var actualProduct = db.loadProductDetail(productName)
                if(actualProduct == null)
                {
                    db.insertProduct(Product(productName,
                        "RM $productPrice", productDescription, productStock, imgUploaded,1))
                    Toast.makeText(application, "The product has been added", Toast.LENGTH_LONG).show();
                    it.findNavController().navigate(R.id.action_adminAddProduct_to_fragment_admin_management)
                }
                else{
                    Toast.makeText(application, "The product has not been added. Please Try Again!", Toast.LENGTH_LONG)
                        .show();
                    it.findNavController().navigate(R.id.action_adminAddProduct_to_fragment_admin_management)
                }
            }
            if(successful == false){
                Toast.makeText(application, "New Product not added or has been existed. Please Try Again!", Toast.LENGTH_LONG)
                    .show();
                it.findNavController().navigate(R.id.action_adminAddProduct_to_fragment_admin_management)
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type ="image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            Glide.with(this)
                .asBitmap()
                .load(data?.data)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        @Nullable transition: Transition<in Bitmap?>?
                    ) {
                        binding.addProdImg.setImageBitmap(resource)
                        bitmap = resource
                    }

                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                })

        }
    }

}