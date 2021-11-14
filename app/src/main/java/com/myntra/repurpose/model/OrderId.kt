package com.myntra.repurpose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderId(@SerializedName("order_id") val order_id: String)
