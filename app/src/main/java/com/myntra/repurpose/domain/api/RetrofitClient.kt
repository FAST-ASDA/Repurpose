package com.myntra.repurpose.domain.api

import com.myntra.repurpose.view.customview.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(token: String?) {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .method(original.method, original.body)

            if(token!=null){
                requestBuilder.addHeader("Authorization", "Token $token")
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

}