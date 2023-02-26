package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Entities.OrderEntities
import com.example.myapplication.data.Entities.OrderProductEntities
import com.example.myapplication.data.Model.ListType
import com.example.myapplication.data.Model.Result
import com.example.myapplication.data.OrderData
import com.example.myapplication.data.ProductsDataModel
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    lateinit var orderAdapter: OrderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, com.example.myapplication.R.layout.activity_main);
        setUpObserver()
        initSetUp()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(com.example.myapplication.R.menu.menu, menu)
        val searchViewItem: MenuItem = menu!!.findItem(R.id.search_bar)
        val searchView: SearchView = MenuItemCompat.getActionView(searchViewItem) as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {





                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {

                orderAdapter.filter.filter(newText);
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)

    }


    private fun initSetUp() {


        lifecycleScope.launch(Dispatchers.IO) {
            val list = mainViewModel.getOrderList()
            if (list.isEmpty()) {
                mainViewModel.getOrderByApi()
            } else {
                setUpOrder()
            }
        }
    }

    fun setUpObserver() {
        mainViewModel.status.observe(this@MainActivity) {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    Log.e(Companion.TAG, "setup observer success")
                    it.data?.let { it1 -> insertAllOder(it1) }
                }
                Result.Status.ERROR -> {
                    Log.e(TAG, "setUpObserver: error")
                }
                Result.Status.LOADING -> Log.e(TAG, "setUpObserver: loading")
            }
        }

    }

    fun setUpOrder() {
        lifecycleScope.launch(Dispatchers.IO) {

            val data = mainViewModel.getOrderList()
            val orderData = arrayListOf<ListType>()
            data.forEach {
                val data = OrderData(it, mainViewModel.getAllProductsByOrder(it.orderId))
                orderData.add(data)
            }
            withContext(Dispatchers.Main) {
                orderAdapter = OrderAdapter(orderData)
                binding.rvOrder.adapter = orderAdapter
            }
        }
    }

    private fun insertAllOder(data: ProductsDataModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            async { mainViewModel.insertAllOrder(getOrderList(data)) }.await()
            setUpOrder()


        }
    }

    private fun getOrderList(data: ProductsDataModel): List<OrderEntities> {
        val gson = Gson()
        val list = arrayListOf<OrderEntities>()
        data.record.order.forEach {
            val data = gson.toJson(it)
            val orderEntities = gson.fromJson(data, OrderEntities::class.java)
            list.add(orderEntities)
            mainViewModel.insertAllProducts(getOrderProductList(it.orderProducts, it.orderId))
        }
        return list

    }

    private fun getOrderProductList(
        data: List<ProductsDataModel.Record.Order.OrderProduct>, orderId: Int
    ): List<OrderProductEntities> {
        val gson = Gson()
        val list = arrayListOf<OrderProductEntities>()
        data.forEach {
            val data = gson.toJson(it)

            val orderEntities = gson.fromJson(data, OrderProductEntities::class.java)
            orderEntities.orderId = orderId
            list.add(orderEntities)
        }
        return list

    }

    companion object {
        const val TAG = "MainActivity"
    }
}