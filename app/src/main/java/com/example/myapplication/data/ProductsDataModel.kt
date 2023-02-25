package com.example.myapplication.data


import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity
data class ProductsDataModel(
    @SerializedName("metadata")
    var metadata: Metadata,
    @SerializedName("record")
    var record: Record
) {
    data class Metadata(
        @SerializedName("createdAt")
        var createdAt: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("private")
        var `private`: Boolean
    )

    data class Record(
        @SerializedName("order")
        var order: List<Order>,
        @SerializedName("success")
        var success: Boolean,
        @SerializedName("total_count")
        var totalCount: Int
    ) {


        data class Order(
            @SerializedName("business_name")
            var businessName: String,
            @SerializedName("dealer_code")
            var dealerCode: String,
            @SerializedName("nh")
            var nh: String,
            @SerializedName("order_date")
            var orderDate: String,
            @SerializedName("order_id")
            var orderId: Int,
            @SerializedName("order_on")
            var orderOn: String,
            @SerializedName("order_products")
            var orderProducts: List<OrderProduct>,
            @SerializedName("order_remarks")
            var orderRemarks: String,
            @SerializedName("primary_contact_email")
            var primaryContactEmail: String,
            @SerializedName("primary_contact_mobile")
            var primaryContactMobile: String,
            @SerializedName("primary_contact_name")
            var primaryContactName: String,
            @SerializedName("sh")
            var sh: String,
            @SerializedName("state")
            var state: String,
            @SerializedName("territory")
            var territory: String,
            @SerializedName("th")
            var th: String,
            @SerializedName("user_id")
            var userId: Int
        ) {
            data class OrderProduct(
                @SerializedName("product_id")
                var productId: Int,
                @SerializedName("product_name")
                var productName: String,
                @SerializedName("qty")
                var qty: Int,
                @SerializedName("revision_order_remark")
                var revisionOrderRemark: String,
                @SerializedName("size")
                var size: String,
                @SerializedName("unit")
                var unit: String
            )
        }
    }
}