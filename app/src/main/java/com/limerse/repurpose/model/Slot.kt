package com.limerse.repurpose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Slot(
    @SerializedName("number") val number: Int,
    @SerializedName("priority") val priority: Int,
    @SerializedName("lot") val lot: Int,
    @SerializedName("is_assigned") val is_assigned: String,
    @SerializedName("is_occupied") val is_occupied: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("type") val type: String,
    @SerializedName("product")val product: Int
)