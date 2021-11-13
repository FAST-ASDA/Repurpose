package com.limerse.repurpose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data (
    @SerializedName("msg") val msg: String,
    @SerializedName("success") val success: String,
    @SerializedName("data") val data: UserCredentials,
)









