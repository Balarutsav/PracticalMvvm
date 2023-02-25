package com.example.myapplication.data.remote

import com.example.myapplication.Network.ProductService
import com.example.myapplication.data.Model.Result
import com.example.myapplication.data.ProductsDataModel
import com.example.myapplication.utils.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class ProductRemoteDataSource   @Inject constructor(private val retrofit: Retrofit){



    suspend fun fetchProducts(): Result<ProductsDataModel> {
        val movieService = retrofit.create(ProductService::class.java);
        return getResponse(
            request = { movieService.getPopularMovies() },
            defaultErrorMessage = "Error fetching Movie Description")
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}