package com.example.myapplication.Network

import com.example.myapplication.data.ProductsDataModel
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("b/63ac11e501a72b59f23adceb")
    suspend fun getPopularMovies() : Response<ProductsDataModel>

}