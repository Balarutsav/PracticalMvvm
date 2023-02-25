package com.example.myapplication.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "order_product_data")
class OrderProductEntities (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var orderId:Int,
    @SerializedName("product_id")
    var productId: Int?,
    @SerializedName("product_name")
    var productName: String?,
    @SerializedName("qty")
    var qty: Int?,
    @SerializedName("revision_order_remark")
    var revisionOrderRemark: String?,
    @SerializedName("size")
    var size: String?,
    @SerializedName("unit")
    var unit: String?
)