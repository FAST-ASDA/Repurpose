package com.myntra.repurpose.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.*
import android.widget.*
import android.widget.TextView.BufferType
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.myntra.repurpose.R
import com.myntra.repurpose.model.CenterRepository.Companion.centerRepository
import com.myntra.repurpose.model.entities.Money.Companion.rupees
import com.myntra.repurpose.util.ColorGenerator
import com.myntra.repurpose.util.Utils
import com.myntra.repurpose.util.Utils.AnimationType
import com.myntra.repurpose.util.Utils.switchContent
import com.myntra.repurpose.util.Utils.vibrate
import com.myntra.repurpose.view.activities.ECartHomeActivity
import com.myntra.repurpose.view.adapter.SimilarProductsPagerAdapter
import com.myntra.repurpose.view.customview.*
import com.myntra.repurpose.view.customview.TextDrawable.IBuilder
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.math.BigDecimal

class ProductDetailsFragment(private val subcategoryKey: String?, private var productListNumber: Int, private val isFromCart: Boolean) : Fragment() {
    private var itemImage: ImageView? = null
    private var itemSellPrice: TextView? = null
    private var itemName: TextView? = null
    private var itemdescription: TextView? = null
    private var mDrawableBuilder: IBuilder? = null
    private var drawable: TextDrawable? = null
    private val mColorGenerator = ColorGenerator.MATERIAL
    private var similarProductsPager: ClickableViewPager? = null
    private var topSellingPager: ClickableViewPager? = null
    private var mToolbar: Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frag_product_detail, container, false)
        mToolbar = rootView.findViewById<View>(R.id.htab_toolbar) as Toolbar
        if (mToolbar != null) {
            (activity as ECartHomeActivity?)!!.setSupportActionBar(mToolbar)
        }
        if (mToolbar != null) {
            (activity as ECartHomeActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            mToolbar!!.setNavigationIcon(R.drawable.ic_drawer)
        }
        mToolbar!!.setTitleTextColor(Color.WHITE)
        mToolbar!!.setNavigationOnClickListener {
            Utils.switchFragmentWithAnimation(
                R.id.frag_container,
                SellerProfileFragment(),
                (context as ECartHomeActivity?)!!, null,
                AnimationType.SLIDE_LEFT
            )
        }
        rootView!!.findViewById<View>(R.id.cool).setOnClickListener {
            Utils.switchFragmentWithAnimation(
                R.id.frag_container,
                SellerProfileFragment(),
                (context as ECartHomeActivity?)!!, null,
                AnimationType.SLIDE_LEFT
            )
        }
        (activity as ECartHomeActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        similarProductsPager = rootView
            .findViewById<View>(R.id.similar_products_pager) as ClickableViewPager
        topSellingPager = rootView
            .findViewById<View>(R.id.top_selleing_pager) as ClickableViewPager
        itemSellPrice = (rootView
            .findViewById<View>(R.id.category_discount) as TextView)
        itemName = (rootView.findViewById<View>(R.id.product_name) as TextView)
        itemdescription = (rootView
            .findViewById<View>(R.id.product_description) as TextView)
        itemImage = rootView.findViewById<View>(R.id.product_image) as ImageView
        fillProductData()
        rootView.findViewById<View>(R.id.okay).setOnClickListener {
            if (isFromCart) {

                //Update Quantity on shopping List
                centerRepository!!.listOfProductsInShoppingList[productListNumber]
                    .quantity = (
                        Integer.valueOf(
                            centerRepository
                            !!.listOfProductsInShoppingList[productListNumber]
                                .quantity
                        ) + 1).toString()


                //Update Ui
                vibrate((activity)!!)

                //Update checkout amount on screen
                (activity as ECartHomeActivity?)!!.updateCheckOutAmount(
                    BigDecimal.valueOf(
                        java.lang.Long
                            .valueOf(
                                centerRepository
                                !!.listOfProductsInShoppingList[productListNumber]
                                    .sellMRP
                            )
                    ), true
                )
            }
            else {

                // current object
                val tempObj =
                    (centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber])!!

                // if current object is lready in shopping list
                if (centerRepository!!.listOfProductsInShoppingList.contains(tempObj)
                ) {

                    // get position of current item in shopping list
                    val indexOfTempInShopingList =
                        centerRepository!!.listOfProductsInShoppingList
                            .indexOf(tempObj)

                    // increase quantity of current item in shopping
                    // list
                    if (tempObj.quantity.toInt() == 0) {
                        (context as ECartHomeActivity?)!!.updateItemCount(true)
                    }

                    // update quanity in shopping list
                    centerRepository!!.listOfProductsInShoppingList[indexOfTempInShopingList]
                        .quantity = (Integer
                        .valueOf(
                            tempObj
                                .quantity
                        ) + 1).toString()

                    // update checkout amount
                    (context as ECartHomeActivity?)!!.updateCheckOutAmount(
                        BigDecimal.valueOf(
                            java.lang.Long
                                .valueOf(
                                    centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!!.get(productListNumber)!!.sellMRP
                                )
                        ), true
                    )
                } else {
                    (context as ECartHomeActivity?)!!.updateItemCount(true)
                    tempObj.quantity = 1.toString()
                    centerRepository!!.listOfProductsInShoppingList.add(tempObj)
                    (context as ECartHomeActivity?)!!.updateCheckOutAmount(
                        BigDecimal.valueOf(java.lang.Long.valueOf(centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!!.get(productListNumber)!!.sellMRP)), true
                    )
                }
                vibrate((context)!!)
            }
        }

//        rootView.findViewById<View>(R.id.remove_item).setOnClickListener {
//            if (isFromCart) {
//                if (Integer.valueOf(
//                        centerRepository!!.listOfProductsInShoppingList[productListNumber].quantity
//                    ) > 2
//                ) {
//                    centerRepository!!.listOfProductsInShoppingList[productListNumber]
//                        .quantity = (
//                            Integer.valueOf(
//                                centerRepository!!.listOfProductsInShoppingList[productListNumber]
//                                    .quantity
//                            ) - 1).toString()
//                    quanitity!!.text = centerRepository!!.listOfProductsInShoppingList
//                        .get(productListNumber).quantity
//                    (activity as ECartHomeActivity?)!!.updateCheckOutAmount(
//                        BigDecimal.valueOf(
//                            java.lang.Long
//                                .valueOf(
//                                    centerRepository!!.listOfProductsInShoppingList[productListNumber]
//                                        .sellMRP
//                                )
//                        ), false
//                    )
//                    vibrate((activity)!!)
//                } else if (Integer.valueOf(
//                        centerRepository!!.listOfProductsInShoppingList[productListNumber].quantity
//                    ) == 1
//                ) {
//                    (activity as ECartHomeActivity?)!!.updateItemCount(false)
//                    (activity as ECartHomeActivity?)!!.updateCheckOutAmount(
//                        BigDecimal.valueOf(
//                            java.lang.Long
//                                .valueOf(
//                                    centerRepository!!.listOfProductsInShoppingList[productListNumber]
//                                        .sellMRP
//                                )
//                        ), false
//                    )
//                    centerRepository!!.listOfProductsInShoppingList
//                        .removeAt(productListNumber)
//                    if (Integer
//                            .valueOf(
//                                (activity as ECartHomeActivity?)!!.itemCount
//                            ) == 0
//                    ) {
//                        MyCartFragment.Companion.updateMyCartFragment(false)
//                    }
//                    vibrate((activity)!!)
//                }
//            } else {
//                val tempObj =
//                    (centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber])!!
//                if (centerRepository!!.listOfProductsInShoppingList.contains(tempObj)
//                ) {
//                    val indexOfTempInShopingList =
//                        centerRepository!!.listOfProductsInShoppingList
//                            .indexOf(tempObj)
//                    if (Integer.valueOf(tempObj.quantity) != 0) {
//                        centerRepository!!.listOfProductsInShoppingList[indexOfTempInShopingList]
//                            .quantity = (Integer.valueOf(
//                            tempObj
//                                .quantity
//                        ) - 1).toString()
//                        (context as ECartHomeActivity?)!!.updateCheckOutAmount(
//                            BigDecimal.valueOf(
//                                java.lang.Long
//                                    .valueOf(
//                                        centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!!.get(productListNumber)!!.sellMRP
//                                    )
//                            ),
//                            false
//                        )
//                        quanitity!!.text = centerRepository!!.listOfProductsInShoppingList[indexOfTempInShopingList]
//                            .quantity
//                        vibrate((context)!!)
//                        if (Integer.valueOf(
//                                centerRepository!!.listOfProductsInShoppingList[indexOfTempInShopingList]
//                                    .quantity
//                            ) == 0
//                        ) {
//                            centerRepository!!.listOfProductsInShoppingList
//                                .removeAt(indexOfTempInShopingList)
//                            (context as ECartHomeActivity?)!!.updateItemCount(false)
//                        }
//                    }
//                } else {
//                }
//            }
//        }
        rootView.isFocusableInTouchMode = true
        rootView.requestFocus()
        rootView.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK)
            ) {
                if (isFromCart) {
                    switchContent(
                        R.id.frag_container,
                        Utils.SHOPPING_LIST_TAG,
                        (((activity) as ECartHomeActivity?)!!),
                        AnimationType.SLIDE_UP
                    )
                } else {
                    switchContent(
                        R.id.frag_container,
                        Utils.PRODUCT_OVERVIEW_FRAGMENT_TAG,
                        (((activity) as ECartHomeActivity?)!!),
                        AnimationType.SLIDE_RIGHT
                    )
                }
            }
            true
        }
        if (isFromCart) {
            similarProductsPager!!.visibility = View.GONE
            topSellingPager!!.visibility = View.GONE
        } else {
            showRecomondation()
        }
        return rootView
    }

    private fun showRecomondation() {
        val mCustomPagerAdapter = SimilarProductsPagerAdapter(
            (activity)!!, (subcategoryKey)!!
        )
        similarProductsPager!!.adapter = mCustomPagerAdapter
        similarProductsPager!!.setOnItemClickListener(object: ClickableViewPager.OnItemClickListener{
            override fun onItemClick(position: Int) {
                productListNumber = position
                fillProductData()
                vibrate((activity)!!)
            }

        })
        topSellingPager!!.adapter = mCustomPagerAdapter
        topSellingPager!!.setOnItemClickListener(object: ClickableViewPager.OnItemClickListener{
            override fun onItemClick(position: Int) {
                productListNumber = position
                fillProductData()
                vibrate((activity)!!)
            }
        })
    }

    fun fillProductData() {
        if (!isFromCart) {


            //Fetch and display item from Gloabl Data Model
            itemName!!.text = centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber]!!.itemName
            itemdescription!!.text = centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber]!!.itemDetail
            val sellCostString = (rupees(
                BigDecimal.valueOf(
                    java.lang.Long.valueOf(
                        centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber]!!.sellMRP
                    )
                )
            ).toString()
                    + "  ")
            val buyMRP = rupees(
                BigDecimal.valueOf(
                    java.lang.Long.valueOf(
                        centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber]!!.mRP
                    )
                )
            ).toString()
            val costString = sellCostString + buyMRP
            itemSellPrice!!.setText(costString, BufferType.SPANNABLE)
            val spannable = itemSellPrice!!.text as Spannable
            spannable.setSpan(
                StrikethroughSpan(), sellCostString.length,
                costString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            mDrawableBuilder = TextDrawable.builder().beginConfig()
                .withBorder(4).endConfig().roundRect(10)
            drawable = mDrawableBuilder!!.build(
                java.lang.String.valueOf(
                    centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber]!!.itemName.get(0)
                ),
                mColorGenerator!!.getColor(
                    centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber]!!.itemName
                )
            )
            Picasso.get()
                .load(
                    centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!![productListNumber]!!.imageURL
                ).placeholder(drawable!!)
                .error(drawable!!).fit().centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(itemImage, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception) {
                        // Try again online if cache failed
                        Picasso.get()
                            .load(
                                centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]!!.get(productListNumber)!!.imageURL
                            )
                            .placeholder(drawable!!).error(drawable!!)
                            .fit().centerCrop().into(itemImage)
                    }
                })
            val label = LabelView(activity)
            label.text = centerRepository!!.getMapOfProductsInCategory()
                .get(subcategoryKey)!![productListNumber]!!.getDiscount()
            label.setBackgroundColor(-0x16e19d)
            label.setTargetView(itemImage!!, 10, LabelView.Gravity.RIGHT_TOP)
        } else {


            //Fetch and display products from Shopping list
            itemName!!.text = centerRepository!!.listOfProductsInShoppingList[productListNumber].itemName
            itemdescription!!.text = centerRepository!!.listOfProductsInShoppingList.get(productListNumber).itemDetail
            val sellCostString = (rupees(
                BigDecimal.valueOf(
                    centerRepository!!.listOfProductsInShoppingList[productListNumber].sellMRP.toLong()
                )
            ).toString()
                    + "  ")
            val buyMRP = rupees(
                BigDecimal.valueOf(
                    centerRepository!!.listOfProductsInShoppingList[productListNumber].mRP.toLong()
                )
            ).toString()
            val costString = sellCostString + buyMRP
            itemSellPrice!!.setText(costString, BufferType.SPANNABLE)
            val spannable = itemSellPrice!!.text as Spannable
            spannable.setSpan(
                StrikethroughSpan(), sellCostString.length,
                costString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            mDrawableBuilder = TextDrawable.builder().beginConfig()
                .withBorder(4).endConfig().roundRect(10)
            drawable = mDrawableBuilder!!.build(
                centerRepository!!.listOfProductsInShoppingList[productListNumber]
                    .itemName[0].toString(),
                mColorGenerator!!.getColor(
                    centerRepository!!.listOfProductsInShoppingList[productListNumber].itemName
                )
            )
            Picasso.get()
                .load(
                    centerRepository!!.listOfProductsInShoppingList[productListNumber]
                        .imageURL
                ).placeholder(drawable!!)
                .error(drawable!!).fit().centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(itemImage, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception) {
                        // Try again online if cache failed
                        Picasso.get()
                            .load(
                                centerRepository!!.listOfProductsInShoppingList[productListNumber]
                                    .imageURL
                            )
                            .placeholder(drawable!!).error(drawable!!)
                            .fit().centerCrop().into(itemImage)
                    }
                })
            val label = LabelView(activity)
            label.text = centerRepository!!.listOfProductsInShoppingList.get(productListNumber).getDiscount()
            label.setBackgroundColor(-0x16e19d)
            label.setTargetView(itemImage!!, 10, LabelView.Gravity.RIGHT_TOP)
        }
    }
}