package com.example.naturemagnet.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.naturemagnet.databinding.FragmentAdminManagementComponentBinding
import com.example.naturemagnet.entity.Product

class ProdAdminAdapter (private val data: List<Product>,
                        private val productClickListener: ProductClickListener, val application:Application) : RecyclerView.Adapter<ProdAdminAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: FragmentAdminManagementComponentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Product){
            binding.prodAdminComp = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = FragmentAdminManagementComponentBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        Glide.with(application).load(data[position].prodImage).into(holder.binding.imageView)
        holder.binding.root.setOnClickListener{
            productClickListener.onProductClick(it, data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}