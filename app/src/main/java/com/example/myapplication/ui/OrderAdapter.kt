package com.example.myapplication.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.DateModel
import com.example.myapplication.data.Model.ListType
import com.example.myapplication.data.OrderData
import com.example.myapplication.databinding.ItemOrderBinding
import com.example.myapplication.databinding.RawDateBinding
import com.example.myapplication.utils.Utils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class OrderAdapter(val items: ArrayList<ListType>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var filterList: List<ListType>? = null

    init {

        filterList = getDateViseFilter(items as List<OrderData>)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ListType.TYPE_DATE) {
            return DateViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.raw_date,
                    parent,
                    false
                )
            )

        } else {
            return ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_order,
                    parent,
                    false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val data = filterList?.get(position)
            if (data != null) {
                if (data is OrderData) {
                    holder.setData(data as OrderData)
                } else {

                }
            }
        } else if (holder is DateViewHolder) {
            val data = filterList?.get(position)
            if (data != null) {
                if (data is DateModel) {
                    holder.bindView(data)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return filterList?.size ?: 0
    }


    internal class DateViewHolder constructor(rawDateBinding: RawDateBinding) :
        RecyclerView.ViewHolder(rawDateBinding.getRoot()) {
        var dateBinding: RawDateBinding

        init {
            dateBinding = rawDateBinding
        }

        /* access modifiers changed from: package-private */
        fun bindView(datePojo: DateModel) {
            val date: Date? =
                Utils.getDate(datePojo.date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

            dateBinding.tvSpinner.text = date?.let { Utils.getDateFormat(it,"dd MMMM yyyy") }
            dateBinding.tvSpinner.setPadding(40, 15, 40, 15)

        }
    }


    inner class ViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: OrderData) {
            data.orderEntities.let {
                binding.tvBusinessName.text = it.businessName
                binding.tvState.text = it.state
                binding.tvEmail.text = it.primaryContactEmail
                binding.tvName.text = it.primaryContactName
                binding.tvMobileNumber.text = it.primaryContactMobile
                binding.tvTerritory.text=it.territory

            }
            val adapter = ProductsAdapter(data.list)
            binding.rvProduct.adapter = adapter


        }
    }

    override fun getItemViewType(position: Int): Int {
        return filterList?.get(position)?.viewType ?: ListType.TYPE_NORMAL
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filterList = if (charString.isEmpty()) {
                    items
                } else {
                    val filteredList: MutableList<ListType> = ArrayList()
                    for (movie in items) {
                        val data = movie as OrderData
                        if (data != null) {
                            if (movie.orderEntities.businessName?.toLowerCase()
                                    ?.contains(charString.lowercase(Locale.getDefault())) == true
                                || movie.orderEntities.primaryContactMobile?.startsWith(charString) == true
                            ) {
                                filteredList.add(movie)
                            }
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filterList = getDateViseFilter(filterResults.values as List<OrderData>)
                notifyDataSetChanged()

            }
        }
    }


    fun getDateViseFilter(list: List<OrderData>): List<ListType> {
        val dateList = arrayListOf<String>()
        if (list.isNotEmpty()) {
            val date: Date? =
                Utils.getDate(list[0].orderEntities.orderDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val dataModel = list[0].orderEntities.orderDate?.let { DateModel(it) }

            val arrayList = ArrayList<ListType>()
            if (dataModel != null) {
                arrayList.add(dataModel)
            }
            for (i in list.indices) {
                val orderEntties = list[i]
                val date2: Date? =

                    Utils.getDate(list[i].orderEntities.orderDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                Log.e("Order Adapter ", "getDateViseFilter: ${list[i].orderEntities.orderDate}")
                if (i != 0 && !compareDateWasChange(date, date2)) {
                    val date = orderEntties.orderEntities.orderDate?.let { DateModel(it) }
                    if (date != null) {
                        if (!dateList.contains(date2?.let { Utils.getDateFormat(it) })) {
                            date2?.let { Utils.getDateFormat(it) }?.let { dateList.add(it) }
                            arrayList.add(date)
                        }
                    }
                }
                if (orderEntties.viewType != 2) {

                }
                arrayList.add(orderEntties)

            }
            return arrayList
        } else {
            return list
        }

    }


    /* access modifiers changed from: package-private */
    fun compareDateWasChange(date: Date?, date2: Date?): Boolean {

        var date = date
        var date2 = date2
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val dateString: String = simpleDateFormat.format(date)
            val dateString2: String = simpleDateFormat.format(date2)
            date = simpleDateFormat.parse(simpleDateFormat.format(date))

            Log.e("Order data", "compareDateWasChange 1 : ${dateString}")
            Log.e("Order data", "compareDateWasChange 2 : ${dateString2}")
            date2 = simpleDateFormat.parse(simpleDateFormat.format(date2))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (date != null) {
            return if (date.compareTo(date2) < 0) {
                Log.e("Order data", "compareDateWasChange: Today Date is Lesser than my Date")
                false
            } else if (date.compareTo(date2) > 0) {
                Log.e("Order data", "compareDateWasChange: Today Date is Greater than my date")
                false
            } else {
                Log.e("Order data", "compareDateWasChange: Both Dates are equal")
                true
            }
        } else {
            return false
        }

    }

}

