package com.example.myapplication.data


import com.example.myapplication.data.Entities.OrderEntities
import com.example.myapplication.data.Entities.OrderProductEntities
import com.example.myapplication.data.Model.Result
import com.example.myapplication.data.local.OrderDao
import com.example.myapplication.data.local.OrderProductDao
import com.example.myapplication.data.remote.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepo @Inject constructor(
    private val movieRemoteDataSource: ProductRemoteDataSource,
    private val orderDao: OrderDao,
    private val orderProductDao: OrderProductDao
) {


    suspend fun insertAllOrder(list: List<OrderEntities>) {
        orderDao.insertAll(list)
    }


    suspend fun insertAllOrderProduct(list: List<OrderProductEntities>) {
        orderProductDao.insertAll(list)
    }


    suspend fun getAllOrder(): List<OrderEntities> {
      return  orderDao.getAllOrder()
    }



    suspend fun getAllProducts(orderId:Int): List<OrderProductEntities> {
        return  orderProductDao.getOrderProduct(orderId)
    }

    suspend fun fetchTrendingMovies(): Result<ProductsDataModel> {
        return movieRemoteDataSource.fetchProducts()
    }

}