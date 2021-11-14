package com.myntra.repurpose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class User(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("phone") var phone: String,
)