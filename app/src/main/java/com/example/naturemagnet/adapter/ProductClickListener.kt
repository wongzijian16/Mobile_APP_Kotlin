package com.example.naturemagnet.adapter

import android.view.View
import com.example.naturemagnet.entity.Product

interface ProductClickListener {
    fun onProductClick(view: View, product: Product)
}