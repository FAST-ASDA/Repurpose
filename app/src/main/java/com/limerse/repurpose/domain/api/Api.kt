package com.limerse.repurpose.domain.api

import com.limerse.repurpose.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    //Login
    @FormUrlEncoded
    @POST("/api/user/login")
    fun login(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("googleId") googleId: String,
    ):Call<Data>

    //Add a Product
    @FormUrlEncoded
    @POST("/api/v1/accounts/products/add/")
    fun addProduct(@Field("unique_code") unique_code: String,@Field("plate_number")plate_number: String):Call<Void>

    //Update Phone
    @FormUrlEncoded
    @PUT("/api/v1/accounts/user-details/")
    fun updatePhone(@Field("phone") phone: String): Call<User>

    //Stripe Intent
    @POST
    fun stripeIntent(@Url url: String): Call<ResponseBody>

    //Update Photo
    @Multipart
    @PUT("/api/v1/accounts/user-details/")
    fun updatePhoto(@Part photo: MultipartBody.Part): Call<User>

    //Top Up (Order) with response of order id
    @FormUrlEncoded
    @POST("/api/v1/payments/order/")
    fun order(@Field("product") product: String, @Field("amount") amount: String):Call<OrderId>

    //Top Up (Capture) with response of Okay
    @FormUrlEncoded
    @POST("/api/v1/payments/capture/")
    fun capture(@Field("razorpay_payment_id") razorpay_payment_id: String,
                @Field("razorpay_order_id") razorpay_order_id: String,
                @Field("razorpay_signature") razorpay_signature: String): Call<Amount>


    //Fetch User Details
    @GET("/api/v1/accounts/user-details/")
    fun fetchUser(): Call<User>

    //Products List associated with the token
    @GET("/api/v1/accounts/products/")
    fun products(): Call<ArrayList<Product>>

    //Product
    @GET("/api/v1/accounts/products/{}")
    fun product(): Call<Product>
}