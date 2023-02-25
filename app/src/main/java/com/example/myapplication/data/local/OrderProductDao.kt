package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.Entities.OrderProductEntities


@Dao
interface OrderProductDao {

/*
    @Query("SELECT * FROM orderProduct")
    fun getAllOrder() : LiveData<List<OrderProductEntities>>


*/

    @Query("SELECT * FROM order_product_data WHERE orderId = :id")
    fun getOrderProduct(id: Int): List<OrderProductEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<OrderProductEntities>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: OrderProductEntities)


}