package com.limerse.repurpose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Product (
    @SerializedName("id") val id: String,
    @SerializedName("product_id") val product_id: String,
    @SerializedName("unique_code") val unique_code: String,
    @SerializedName("name") val name: String,
    @SerializedName("balance") val balance: String,
    @SerializedName("ongoing_session") val ongoing_session: Int,
    @SerializedName("registered_owner_name") val registered_owner_name: String,
    @SerializedName("registered_owner_email") val registered_owner_email: String,
    @SerializedName("users") val users: ArrayList<User>,
    @SerializedName("type") val type: String
)