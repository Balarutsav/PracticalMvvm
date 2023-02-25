package com.example.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Entities.OrderEntities
import com.example.myapplication.data.Entities.OrderProductEntities
import com.example.myapplication.data.Model.Result
import com.example.myapplication.data.ProductRepo
import com.example.myapplication.data.ProductsDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val productRepo: ProductRepo) :
    ViewModel() {

    private val _status: MediatorLiveData<com.example.myapplication.data.Model.Result<ProductsDataModel>> by lazy {
        MediatorLiveData<Result<ProductsDataModel>>()
    }

    val status: LiveData<Result<ProductsDataModel>> get() = _status


    suspend fun getOrderByApi() {
        val result = productRepo.fetchTrendingMovies()
        withContext(Dispatchers.Main) {
            _status.value = result
        }
    }

    suspend fun getAllProductsByOrder(orderId:Int): List<OrderProductEntities> {
      return  productRepo.getAllProducts(orderId = orderId)
    }

  suspend  fun insertAllOrder(list: List<OrderEntities>) {

            productRepo.insertAllOrder(list)

    }

   suspend fun getOrderList(): List<OrderEntities> {
   return     productRepo.getAllOrder()
    }

    fun insertAllProducts(list: List<OrderProductEntities>) {
        viewModelScope.launch {
            productRepo.insertAllOrderProduct(list)
        }
    }


}