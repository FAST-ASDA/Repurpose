package com.limerse.repurpose.view.activities

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.limerse.repurpose.R
import com.limerse.repurpose.domain.PaymentSheetViewModel
import com.limerse.repurpose.model.CenterRepository.Companion.centerRepository
import com.limerse.repurpose.model.entities.Money.Companion.rupees
import com.limerse.repurpose.model.entities.Product
import com.limerse.repurpose.util.PreferenceHelper
import com.limerse.repurpose.util.TinyDB
import com.limerse.repurpose.util.Utils
import com.limerse.repurpose.util.Utils.AnimationType
import com.limerse.repurpose.util.Utils.switchContent
import com.limerse.repurpose.util.Utils.switchFragmentWithAnimation
import com.limerse.repurpose.util.Utils.vibrate
import com.limerse.repurpose.view.fragment.HomeFragment
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

class ECartHomeActivity : AppCompatActivity() {
    var itemCount = 0
    private var checkoutAmount = BigDecimal(BigInteger.ZERO)
    private var mDrawerLayout: DrawerLayout? = null
    private var checkOutAmount: TextView? = null
    private var itemCountTextView: TextView? = null
    private var offerBanner: TextView? = null
    var progressBar: ProgressBar? = null
    private var mNavigationView: NavigationView? = null
    private var paymentSheet: PaymentSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecart)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        centerRepository!!.setListOfProductsInShoppingList(
            TinyDB(applicationContext).getListObject(
                PreferenceHelper.MY_CART_LIST_LOCAL, Product::class.java
            )
        )
        itemCount = centerRepository!!.listOfProductsInShoppingList
            .size

        //	makeFakeVolleyJsonArrayRequest();
        offerBanner = findViewById<View>(R.id.new_offers_banner) as TextView
        itemCountTextView = findViewById<View>(R.id.item_count) as TextView
        itemCountTextView!!.isSelected = true
        itemCountTextView!!.text = itemCount.toString()
        checkOutAmount = findViewById<View>(R.id.checkout_amount) as TextView
        checkOutAmount!!.isSelected = true
        checkOutAmount!!.text = rupees(checkoutAmount).toString()
        offerBanner!!.isSelected = true
        mDrawerLayout = findViewById<View>(R.id.nav_drawer) as DrawerLayout
        mNavigationView = findViewById<View>(R.id.nav_view) as NavigationView
        progressBar = findViewById<View>(R.id.loading_bar) as ProgressBar
        checkOutAmount!!.setOnClickListener {
            vibrate(applicationContext)
            switchContent(
                R.id.frag_container,
                Utils.SHOPPING_LIST_TAG, this@ECartHomeActivity,
                AnimationType.SLIDE_UP
            )
        }
        if (itemCount != 0) {
            for (product in centerRepository!!.listOfProductsInShoppingList) {
                updateCheckOutAmount(
                    BigDecimal.valueOf(java.lang.Long.valueOf(product.sellMRP)),
                    true
                )
            }
        }
        findViewById<View>(R.id.item_counter).setOnClickListener {
            vibrate(applicationContext)
            switchContent(
                R.id.frag_container,
                Utils.SHOPPING_LIST_TAG,
                this@ECartHomeActivity, AnimationType.SLIDE_UP
            )
        }
        findViewById<View>(R.id.checkout).setOnClickListener {
            vibrate(applicationContext)
            prepareCheckout { customerConfig, clientSecret ->
                paymentSheet!!.presentWithPaymentIntent(
                    clientSecret,
                    PaymentSheet.Configuration(
                        merchantDisplayName = merchantName,
                        customer = customerConfig,
                        googlePay = googlePayConfig,
                        allowsDelayedPaymentMethods = true
                    )
                )
            }
        }
        switchFragmentWithAnimation(
            R.id.frag_container,
            HomeFragment(), this, Utils.HOME_FRAGMENT,
            AnimationType.SLIDE_UP
        )
        toggleBannerVisibility()
        mNavigationView!!.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.home -> {
                    mDrawerLayout!!.closeDrawers()
                    switchContent(
                        R.id.frag_container,
                        Utils.HOME_FRAGMENT,
                        this@ECartHomeActivity,
                        AnimationType.SLIDE_LEFT
                    )
                    true
                }
                R.id.my_cart -> {
                    mDrawerLayout!!.closeDrawers()
                    switchContent(
                        R.id.frag_container,
                        Utils.SHOPPING_LIST_TAG,
                        this@ECartHomeActivity,
                        AnimationType.SLIDE_LEFT
                    )
                    true
                }
                R.id.contact_us -> {
                    mDrawerLayout!!.closeDrawers()
                    switchContent(
                        R.id.frag_container,
                        Utils.CONTACT_US_FRAGMENT,
                        this@ECartHomeActivity,
                        AnimationType.SLIDE_LEFT
                    )
                    true
                }
                R.id.settings -> {
                    mDrawerLayout!!.closeDrawers()
                    switchContent(
                        R.id.frag_container,
                        Utils.SETTINGS_FRAGMENT_TAG,
                        this@ECartHomeActivity,
                        AnimationType.SLIDE_LEFT
                    )
                    true
                }
                else -> true
            }
        }
    }

    fun updateItemCount(ifIncrement: Boolean) {
        if (ifIncrement) {
            itemCount++
            itemCountTextView!!.text = itemCount.toString()
        } else {
            itemCountTextView!!.text = (if (itemCount <= 0) 0.toString() else itemCount.toString())
        }
        toggleBannerVisibility()
    }

    fun updateCheckOutAmount(amount: BigDecimal?, increment: Boolean) {
        if (increment) {
            checkoutAmount = checkoutAmount.add(amount)
        } else {
            if (checkoutAmount.signum() == 1) checkoutAmount = checkoutAmount.subtract(amount)
        }
        checkOutAmount!!.text = rupees(checkoutAmount).toString()
    }

    override fun onPause() {
        super.onPause()

        // Store Shopping Cart in DB
        TinyDB(applicationContext).putListObject(
            PreferenceHelper.MY_CART_LIST_LOCAL, centerRepository!!.listOfProductsInShoppingList
        )
    }

    /*
     * Toggles Between Offer Banner and Checkout Amount. If Cart is Empty SHow
     * Banner else display total amount and item count
     */
    private fun toggleBannerVisibility() {
        if (itemCount == 0) {
            findViewById<View>(R.id.checkout_item_root).visibility = View.GONE
            findViewById<View>(R.id.new_offers_banner).visibility = View.VISIBLE
        } else {
            findViewById<View>(R.id.checkout_item_root).visibility = View.VISIBLE
            findViewById<View>(R.id.new_offers_banner).visibility = View.GONE
        }
    }

    /*
     * Get Navigation drawer
     */
    fun getmDrawerLayout(): DrawerLayout? {
        return mDrawerLayout
    }

    companion object {
        const val merchantName = "REDALCK"
        const val backendUrl = "https://stripe-server-akshaaatt.herokuapp.com/checkout"
        val googlePayConfig = PaymentSheet.GooglePayConfiguration(
            environment = PaymentSheet.GooglePayConfiguration.Environment.Test,
            countryCode = "US"
        )
    }

    private val viewModel: PaymentSheetViewModel by lazy {
        PaymentSheetViewModel(application)
    }

    private fun prepareCheckout(onSuccess: (PaymentSheet.CustomerConfiguration?, String) -> Unit) {
        viewModel.prepareCheckout(backendUrl)

        viewModel.exampleCheckoutResponse.observe(this) { checkoutResponse ->
            PaymentConfiguration.init(this, checkoutResponse.publishableKey)
            onSuccess(checkoutResponse.makeCustomerConfig(), checkoutResponse.paymentIntent)
            viewModel.exampleCheckoutResponse.removeObservers(this)
        }
    }

    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        viewModel.status.value = paymentResult.toString()
    }

}