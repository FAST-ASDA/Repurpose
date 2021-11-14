package com.myntra.repurpose.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserCredentials(
    @SerializedName("token") val token: String,
    @SerializedName("userId") val userId: String
)









