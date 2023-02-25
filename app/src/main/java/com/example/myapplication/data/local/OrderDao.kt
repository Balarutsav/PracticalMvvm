package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.Entities.OrderEntities


@Dao
interface OrderDao {


    @Query("SELECT * FROM order_data")
    suspend fun getAllOrder():List<OrderEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<OrderEntities>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: OrderEntities)


}