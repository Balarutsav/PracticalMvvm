package com.example.myapplication.data

import com.example.myapplication.data.Entities.OrderEntities
import com.example.myapplication.data.Entities.OrderProductEntities

data class OrderData(val orderEntities: OrderEntities,val list:List<OrderProductEntities>)
