package com.example.naturemagnet.adapter

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.naturemagnet.databinding.FragmentProductBinding
import com.example.naturemagnet.databinding.FragmentShopBinding
import com.example.naturemagnet.entity.Product

class ProductAdapter(private val data: List<Product>,
                     private val productClickListener: ProductClickListener,val application: Application) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>()  {

    inner class MyViewHolder(val binding: FragmentProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:Product){
            binding.prodShopComp = item
//            val productName = binding.prodName.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = FragmentProductBinding.inflate(inflater,parent,false)
        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.prodName.text = data[position].prodName
        Glide.with(application).load(data[position].prodImage).into(holder.binding.shopImage)
//        holder.binding.shopImage.setImageBitmap(data[position].prodImage)
        holder.binding.root.setOnClickListener{
            productClickListener.onProductClick(it, data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}