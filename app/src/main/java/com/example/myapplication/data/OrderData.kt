package com.example.myapplication.data

import com.example.myapplication.data.Entities.OrderEntities
import com.example.myapplication.data.Entities.OrderProductEntities
import com.example.myapplication.data.Model.ListType

data class OrderData(val orderEntities: OrderEntities, val list:List<OrderProductEntities>,
                     override val viewType: Int = TYPE_NORMAL
):ListType()
