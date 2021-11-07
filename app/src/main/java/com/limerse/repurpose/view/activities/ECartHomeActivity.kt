package com.limerse.repurpose.view.activities

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.limerse.repurpose.R
import com.limerse.repurpose.domain.PaymentSheetViewModel
import com.limerse.repurpose.domain.helper.Connectivity.isConnected
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
//            showPurchaseDialog()
            prepareCheckout { customerConfig, clientSecret ->
                paymentSheet!!.presentWithPaymentIntent(
                    clientSecret,
                    PaymentSheet.Configuration(
                        merchantDisplayName = merchantName,
                        customer = customerConfig,
                        googlePay = googlePayConfig,
                        // Set `allowsDelayedPaymentMethods` to true if your
                        // business can handle payment methods that complete payment
                        // after a delay, like SEPA Debit and Sofort.
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

    override fun onResume() {
        super.onResume()

        // Show Offline Error Message
        if (!isConnected(applicationContext)) {
            val dialog = Dialog(this@ECartHomeActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.connection_dialog)
            val dialogButton = dialog
                .findViewById<View>(R.id.dialogButtonOK) as Button

            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    /*
     * Toggles Between Offer Banner and Checkout Amount. If Cart is Empty SHow
     * Banner else display total amount and item count
     */
    fun toggleBannerVisibility() {
        if (itemCount == 0) {
            findViewById<View>(R.id.checkout_item_root).visibility = View.GONE
            findViewById<View>(R.id.new_offers_banner).visibility = View.VISIBLE
        } else {
            findViewById<View>(R.id.checkout_item_root).visibility = View.VISIBLE
            findViewById<View>(R.id.new_offers_banner).visibility = View.GONE
        }
    }

    /*
     * get total checkout amount
     */
    fun getCheckoutAmount(): BigDecimal {
        return checkoutAmount
    }

    /*
     * Get Navigation drawer
     */
    fun getmDrawerLayout(): DrawerLayout? {
        return mDrawerLayout
    }

    private fun showPurchaseDialog() {
        val exitScreenDialog = AlertDialog.Builder(this@ECartHomeActivity, R.style.PauseDialog)
        exitScreenDialog.setTitle("Order Confirmation").setMessage("Would you like to place this order ?")
        exitScreenDialog.setCancelable(true)
        exitScreenDialog.setPositiveButton(
            "Place Order"
        ) { dialog, id -> //finish();
            dialog.cancel()
            val productId = ArrayList<String>()
            for (productFromShoppingList in centerRepository!!.listOfProductsInShoppingList) {

                //add product ids to array
                productId.add(productFromShoppingList.productId)
            }

            //pass product id array to Apriori ALGO
            centerRepository!!.addToItemSetList(HashSet(productId))

            //clear all list item
            centerRepository!!.listOfProductsInShoppingList.clear()
            toggleBannerVisibility()
            itemCount = 0
            itemCountTextView!!.text = 0.toString()
            checkoutAmount = BigDecimal(BigInteger.ZERO)
            checkOutAmount!!.text = rupees(checkoutAmount).toString()
        }
        exitScreenDialog.setNegativeButton(
            "Cancel"
        ) { dialog, id -> dialog.cancel() }
        exitScreenDialog.setOnDismissListener {
            Snackbar.make(
                this@ECartHomeActivity.window.decorView.findViewById(android.R.id.content),
                "Order Placed Successfully, Happy Shopping !!",
                Snackbar.LENGTH_LONG
            )
                .setAction("View Apriori Output") {

                }
                .show()
        }
        val alert11 = exitScreenDialog.create()
        alert11.show()
    }

    companion object {
        const val MINIMUM_SUPPORT = 0.1
        private val TAG = ECartHomeActivity::class.java.simpleName
        const val merchantName = "REDALCK"
        const val backendUrl = "https://stripe-server-akshaaatt.herokuapp.com/checkout"
        val googlePayConfig = PaymentSheet.GooglePayConfiguration(
            environment = PaymentSheet.GooglePayConfiguration.Environment.Test,
            countryCode = "US"
        )
    }

    val viewModel: PaymentSheetViewModel by lazy {
        PaymentSheetViewModel(application)
    }

    fun prepareCheckout(onSuccess: (PaymentSheet.CustomerConfiguration?, String) -> Unit) {
        viewModel.prepareCheckout(backendUrl)

        viewModel.exampleCheckoutResponse.observe(this) { checkoutResponse ->
            // Init PaymentConfiguration with the publishable key returned from the backend,
            // which will be used on all Stripe API calls
            PaymentConfiguration.init(this, checkoutResponse.publishableKey)

            onSuccess(
                checkoutResponse.makeCustomerConfig(),
                checkoutResponse.paymentIntent
            )

            viewModel.exampleCheckoutResponse.removeObservers(this)
        }
    }

    fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        viewModel.status.value = paymentResult.toString()
    }

}