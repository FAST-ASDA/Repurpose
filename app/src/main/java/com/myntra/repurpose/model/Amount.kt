package com.myntra.repurpose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Amount(
    @SerializedName("amount") val amount: String,
    @SerializedName("payment") val payment: String
)









