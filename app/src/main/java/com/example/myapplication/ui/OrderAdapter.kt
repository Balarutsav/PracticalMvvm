package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.OrderData
import com.example.myapplication.databinding.ItemOrderBinding
import java.util.*


class OrderAdapter(val items: ArrayList<OrderData>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>(), Filterable {
    private var filterList: List<OrderData>? = null

    init {

        filterList = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = filterList?.get(position)
        if (data != null) {
            holder.setData(data)
        }
    }


    override fun getItemCount(): Int {
        return filterList?.size ?: 0
    }

    inner class ViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: OrderData) {
            data.orderEntities.let {
                binding.tvBusinessName.text = it.businessName
                binding.tvState.text = it.state
                binding.tvPrimaryDetails.text = it.primaryContactEmail
            }
            val adapter = ProductsAdapter(data.list)
            binding.rvProduct.adapter = adapter


        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filterList = if (charString.isEmpty()) {
                    items
                } else {
                    val filteredList: MutableList<OrderData> = ArrayList()
                    for (movie in items) {
                        if (movie.orderEntities.businessName?.toLowerCase()
                                ?.contains(charString.lowercase(Locale.getDefault())) == true
                            || movie.orderEntities.primaryContactMobile?.startsWith(charString) == true
                        ) {
                            filteredList.add(movie)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filterList = filterResults.values as List<OrderData>
                notifyDataSetChanged()

            }
        }
    }

}

