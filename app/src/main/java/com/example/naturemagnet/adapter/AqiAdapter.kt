package com.example.naturemagnet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.databinding.CardItemBinding
import com.example.naturemagnet.entity.AQI

class AqiAdapter(private val data: List<AQI>) :
    RecyclerView.Adapter<AqiAdapter.AqiAdapterViewHolder>() {

    inner class AqiAdapterViewHolder(val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AQI,position: Int) {
            binding.aqi = item
            binding.index.text = (position+1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AqiAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val aqiListBinding = CardItemBinding.inflate(inflater,parent,false)

        return AqiAdapterViewHolder(aqiListBinding)

    }

    override fun onBindViewHolder(holder: AqiAdapterViewHolder, position: Int) {
        holder.bind(data[position],position)
    }

    override fun getItemCount(): Int {
        return data.size
    }


}