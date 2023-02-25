package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Entities.OrderProductEntities
import com.example.myapplication.databinding.ItemOrderProductBinding

class ProductsAdapter(val items: List<OrderProductEntities>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)
        holder.setData(data)
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: OrderProductEntities) {
            binding.tvQty.text = data.qty.toString()
            binding.tvName.text = data.productName
            binding.tvUnit.text = data.unit
            binding.tvSize.text = data.size

        }
    }

}

