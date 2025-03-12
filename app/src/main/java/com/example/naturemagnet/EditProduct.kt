package com.example.naturemagnet

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.naturemagnet.ViewModel.ProductViewModel
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentAdminManagementBinding
import com.example.naturemagnet.databinding.FragmentEditProductBinding
import java.io.ByteArrayOutputStream

class EditProduct : Fragment() {

    private lateinit var binding: FragmentEditProductBinding
    lateinit var bitmap : Bitmap
    private val sharedViewModel: ProductViewModel by activityViewModels()

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_product,
            container,
            false
        )

        binding.editProd = sharedViewModel.product.value
        binding.inputProdName.setText(sharedViewModel.product.value?.prodName)
        binding.inputProdPrice.setText(sharedViewModel.product.value?.prodPrice)
        binding.inputProdStock.setText(sharedViewModel.product.value?.prodQuantityStock.toString())
        binding.inputProdDesc.setText(sharedViewModel.product.value?.prodDescription)
        binding.loadNewProdImg.setImageBitmap(sharedViewModel.product.value?.prodImage)

        val application = requireNotNull(this.activity).application
        val db = NatureMagnetDB.getInstance(application)!!.productDao()

        binding.btnUpdProdName.setOnClickListener{
           val editName = binding.inputProdName.text.toString().trim()

            var actualProduct = db.loadProductDetail(editName)
            if(editName.isEmpty() || actualProduct != null)
            {
                Toast.makeText(application, "Please Enter a New Name", Toast.LENGTH_SHORT).show();
            }
            else{
                db.replaceNewProdName(editName, sharedViewModel.product.value?.prodName.toString())
                Toast.makeText(application, "Name has Updated Successfully", Toast.LENGTH_SHORT).show();
            }

        }

        binding.btnUpdProdPrice.setOnClickListener{
            val editPrice = binding.inputProdPrice.text.toString().trim()
            if(editPrice.isEmpty())
            {
                Toast.makeText(application, "Please Enter a Price", Toast.LENGTH_SHORT).show();
            }
            else{
                db.replaceNewProdPrice(editPrice, sharedViewModel.product.value?.prodPrice.toString())
                Toast.makeText(application, "Price has Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }

        binding.btnUpdProdStock.setOnClickListener{
            val editStock = binding.inputProdStock.text.toString().trim()
            if(editStock.isEmpty())
            {
                Toast.makeText(application, "Please Enter a Stock Quantity", Toast.LENGTH_SHORT).show();
            }
            else{
                db.replaceNewProdStock((editStock.toInt()), sharedViewModel.product.value?.prodQuantityStock.toString().toInt())
                Toast.makeText(application, "Stock Quantity has Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }

        binding.btnUpdProdDesc.setOnClickListener{
            val editDesc = binding.inputProdDesc.text.toString().trim()
            if(editDesc.isEmpty())
            {
                Toast.makeText(application, "Please Enter a Description", Toast.LENGTH_SHORT).show();
            }
            else{
                db.replaceNewProdDesc(editDesc, sharedViewModel.product.value?.prodDescription.toString())
                Toast.makeText(application, "Description has Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }

        binding.loadNewProdImg.setOnClickListener{
            pickImageGallery()
            binding.btnUpdNewImg.visibility = View.VISIBLE
        }

        binding.btnUpdNewImg.setOnClickListener{
            var imgUploaded = bitmap
            var prodName = binding.inputProdName.text.toString()
            var actualProduct = db.loadProductDetail(prodName)
            if(prodName.isEmpty() || actualProduct == null)
            {
                Toast.makeText(application, "Image has Updated Unsuccessfully. Error: Product Not Found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                db.replaceNewProdImage(imgUploaded, prodName)
                Toast.makeText(application, "Image has Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }

        binding.btnDelProd.setOnClickListener{
            val editName = binding.inputProdName.text.toString().trim()
            if(editName.isEmpty())
            {
                Toast.makeText(application, "Product Name should not be Empty", Toast.LENGTH_SHORT).show();
            }
            else{
                db.queDelProduct(editName)
                Toast.makeText(application, "Product " + binding.inputProdName.text.toString() + " has Successfully Deleted", Toast.LENGTH_LONG).show();
                it.findNavController().navigate(R.id.action_adminEditProduct_to_fragment_admin_management)
            }
        }

        return binding.root
    }

    fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type ="image/*"
        startActivityForResult(intent, AddProduct.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == AddProduct.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Glide.with(this)
                .asBitmap()
                .load(data?.data)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        @Nullable transition: Transition<in Bitmap?>?
                    ) {
                        binding.loadNewProdImg.setImageBitmap(resource)
                        bitmap = resource
                    }
                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                })

        }
    }

}