package com.limerse.repurpose.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.limerse.animations.Animations
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.limerse.repurpose.R
import com.limerse.repurpose.model.Token
import com.limerse.repurpose.domain.api.RetrofitClient
import com.limerse.repurpose.utils.sharedPrefFile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    private val signInCode = 100
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            checkLogin()
        }, 4000)
    }

    private fun checkLogin() {
        if(sharedPreferences.getString("token",null) == null) {
            signInToGoogle()
        }
        else{
            startActivity(Intent(applicationContext, ECartHomeActivity::class.java))
            Animations.animateQuick(this)
            finish()
        }
    }

    private fun signInToGoogle() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("709383810699-jv2l1dvel13kjsbmmqvqiduftqu8cjfm.apps.googleusercontent.com")
            .requestProfile()
            .requestEmail()
            .build()

        val signInClient = GoogleSignIn.getClient(this, signInOptions)
        signInClient.silentSignIn().addOnCompleteListener(this) { task ->
            if (!task.isSuccessful || sharedPreferences.getString("token",null) == null) {
                startActivityForResult(signInClient.signInIntent, signInCode)
            }
            else {
                checkLogin()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != signInCode) {
            return
        }
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data) ?: return
        if (result.isSuccess) {

            RetrofitClient(null).instance
                .login(result.signInAccount?.displayName!!,
                    result.signInAccount?.displayName!!,
                    result.signInAccount?.email!!,
                    result.signInAccount!!.idToken!!
                )
                .enqueue(object: Callback<Token> {
                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        println(response.message())
                        sharedPreferences.edit().putString("token",response.body()?.token).apply()
                        checkLogin()
                    }
                })
            sharedPreferences.edit().putString("token","abc").apply()
            checkLogin()
        }
    }

    override fun onBackPressed() {
        checkLogin()
    }
}