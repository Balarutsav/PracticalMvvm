package com.example.myapplication.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "order_data")
class OrderEntities (
    @SerializedName("business_name")
    var businessName: String?,
    @SerializedName("dealer_code")
    var dealerCode: String?,
    @SerializedName("nh")
    var nh: String?,
    @SerializedName("order_date")
    var orderDate: String?,
    @PrimaryKey
    @SerializedName("order_id")
    var orderId: Int,
    @SerializedName("order_on")
    var orderOn: String?,
    @SerializedName("order_remarks")
    var orderRemarks: String?,
    @SerializedName("primary_contact_email")
    var primaryContactEmail: String?,
    @SerializedName("primary_contact_mobile")
    var primaryContactMobile: String?,
    @SerializedName("primary_contact_name")
    var primaryContactName: String?,
    @SerializedName("sh")
    var sh: String?,
    @SerializedName("state")
    var state: String?,
    @SerializedName("territory")
    var territory: String?,
    @SerializedName("th")
    var th: String?,
    @SerializedName("user_id")
    var userId: Int
)