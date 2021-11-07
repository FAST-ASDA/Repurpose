package com.limerse.repurpose.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.limerse.repurpose.retrofit.RetrofitClient
import com.stripe.android.paymentsheet.PaymentSheet
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentSheetViewModel(application: Application) : AndroidViewModel(application) {
    private val inProgress = MutableLiveData<Boolean>()
    val status = MutableLiveData<String>()
    val exampleCheckoutResponse = MutableLiveData<ExampleCheckoutResponse>()

    fun prepareCheckout(backendUrl: String) {
        inProgress.postValue(true)


        RetrofitClient(null).instance
            .stripeIntent(backendUrl)
            .enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    status.postValue("${status.value}\n\nPreparing checkout failed\n" +
                            "${t.message}")
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    exampleCheckoutResponse.postValue(
                        Gson().fromJson(response.body()!!.string(), ExampleCheckoutResponse::class.java)
                    )
                    inProgress.postValue(false)
                }
            })
    }

    data class ExampleCheckoutResponse(val publishableKey: String, val paymentIntent: String, val customer: String? = null, val ephemeralKey: String? = null) {
        fun makeCustomerConfig() =
            when {
                customer != null && ephemeralKey != null -> {
                    PaymentSheet.CustomerConfiguration(id = customer, ephemeralKeySecret = ephemeralKey)
                }
                else -> {
                    null
                }
            }
    }
}
