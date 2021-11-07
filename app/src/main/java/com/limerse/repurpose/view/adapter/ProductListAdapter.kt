package com.limerse.repurpose.view.adapter

import android.content.Context
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.BufferType
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limerse.repurpose.R
import com.limerse.repurpose.model.CenterRepository.Companion.centerRepository
import com.limerse.repurpose.model.entities.Money.Companion.rupees
import com.limerse.repurpose.model.entities.Product
import com.limerse.repurpose.util.ColorGenerator
import com.limerse.repurpose.util.Utils.vibrate
import com.limerse.repurpose.view.activities.ECartHomeActivity
import com.limerse.repurpose.view.customview.TextDrawable
import com.limerse.repurpose.view.customview.TextDrawable.IBuilder
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class ProductListAdapter(subcategoryKey: String?, context: Context, isCartlist: Boolean) : RecyclerView.Adapter<ProductListAdapter.VersionViewHolder>(), ItemTouchHelperAdapter {
    private var mDrawableBuilder: IBuilder? = null
    private var ImageUrl: String? = null
    private var productList: MutableList<Product?>? = ArrayList()
    private var clickListener: OnItemClickListener? = null
    private val context: Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VersionViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_product_list, viewGroup, false)
        return VersionViewHolder(view)
    }

    override fun onBindViewHolder(holder: VersionViewHolder, position: Int) {
        holder.itemName.text = productList!![position]!!.itemName
        holder.itemDesc.text = productList!![position]!!.itemShortDesc
        val sellCostString = (rupees(
            BigDecimal.valueOf(java.lang.Long.valueOf(productList!![position]!!.sellMRP))
        ).toString() + "  ")
        val buyMRP = rupees(
            BigDecimal.valueOf(
                java.lang.Long.valueOf(
                    productList!![position]!!.mRP
                )
            )
        ).toString()
        val costString = sellCostString + buyMRP
        holder.itemCost.setText(costString, BufferType.SPANNABLE)
        val spannable = holder.itemCost.text as Spannable
        spannable.setSpan(
            StrikethroughSpan(), sellCostString.length,
            costString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
            .endConfig().roundRect(10)
        ImageUrl = productList!![position]!!.imageURL
        Log.d("coolMan",ImageUrl!!)
        Glide.with(context).load(ImageUrl)
            .centerCrop().into(holder.imagView)
        holder.addItem.findViewById<View>(R.id.add_item).setOnClickListener { //current object
            val tempObj = productList!![position]


            //if current object is lready in shopping list
            if (centerRepository!!.listOfProductsInShoppingList.contains(tempObj)
            ) {


                //get position of current item in shopping list
                val indexOfTempInShopingList = centerRepository!!.listOfProductsInShoppingList
                    .indexOf(tempObj)

                // increase quantity of current item in shopping list
                if (tempObj!!.quantity.toInt() == 0) {
                    getContext()
                        .updateItemCount(true)
                }


                // update quanity in shopping list
                centerRepository!!.listOfProductsInShoppingList[indexOfTempInShopingList]
                    .quantity = (Integer
                    .valueOf(
                        tempObj
                            .quantity
                    ) + 1).toString()


                //update checkout amount
                getContext().updateCheckOutAmount(
                    BigDecimal
                        .valueOf(
                            java.lang.Long
                                .valueOf(
                                    productList!!.get(position)!!.sellMRP
                                )
                        ),
                    true
                )

                // update current item quanitity
                holder.quanitity.text = tempObj.quantity
            } else {
                getContext().updateItemCount(true)
                tempObj!!.quantity = 1.toString()
                holder.quanitity.text = tempObj.quantity
                centerRepository!!.listOfProductsInShoppingList.add(tempObj)
                getContext().updateCheckOutAmount(
                    BigDecimal
                        .valueOf(
                            java.lang.Long
                                .valueOf(
                                    productList!!.get(position)!!.sellMRP
                                )
                        ),
                    true
                )
            }
            vibrate(getContext())
        }
        holder.removeItem.setOnClickListener {
            val tempObj = productList!![position]
            if (centerRepository!!.listOfProductsInShoppingList
                    .contains(tempObj)
            ) {
                val indexOfTempInShopingList = centerRepository!!.listOfProductsInShoppingList
                    .indexOf(tempObj)
                if (Integer.valueOf(tempObj!!.quantity) != 0) {
                    centerRepository!!.listOfProductsInShoppingList[indexOfTempInShopingList]
                        .quantity = (Integer.valueOf(
                        tempObj
                            .quantity
                    ) - 1).toString()
                    getContext().updateCheckOutAmount(
                        BigDecimal.valueOf(
                            java.lang.Long.valueOf(
                                productList!!.get(position)!!.sellMRP
                            )
                        ),
                        false
                    )
                    holder.quanitity.text =
                        centerRepository!!.listOfProductsInShoppingList[indexOfTempInShopingList].quantity
                    vibrate(getContext())
                    if (Integer.valueOf(
                            centerRepository!!.listOfProductsInShoppingList[indexOfTempInShopingList].quantity
                        ) == 0
                    ) {
                        centerRepository!!.listOfProductsInShoppingList.removeAt(indexOfTempInShopingList)
                        notifyDataSetChanged()
                        getContext().updateItemCount(false)
                    }
                }
            }
        }
    }

    private fun getContext(): ECartHomeActivity {
        return context as ECartHomeActivity
    }

    override fun getItemCount(): Int {
        return if (productList == null) 0 else productList!!.size
    }

    fun SetOnItemClickListener(
        itemClickListener: OnItemClickListener?
    ) {
        clickListener = itemClickListener
    }

    override fun onItemDismiss(position: Int) {
        productList!!.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(productList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(productList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    inner class VersionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var itemName: TextView = itemView.findViewById<View>(R.id.item_name) as TextView
        var itemDesc: TextView = itemView.findViewById<View>(R.id.item_short_desc) as TextView
        var itemCost: TextView = itemView.findViewById<View>(R.id.item_price) as TextView
        var availability: TextView = itemView
            .findViewById<View>(R.id.iteam_avilable) as TextView
        var quanitity: TextView = itemView.findViewById<View>(R.id.iteam_amount) as TextView
        var addItem: TextView
        var removeItem: TextView
        var imagView: ImageView
        override fun onClick(v: View) {
            clickListener!!.onItemClick(v, position)
        }

        init {
            itemName.isSelected = true
            imagView = itemView.findViewById<View>(R.id.product_thumb) as ImageView
            addItem = itemView.findViewById<View>(R.id.add_item) as TextView
            removeItem = itemView.findViewById<View>(R.id.remove_item) as TextView
            itemView.setOnClickListener(this)
        }
    }

    init {
        productList = if (isCartlist) {
            centerRepository!!.listOfProductsInShoppingList
        } else {
            centerRepository!!.getMapOfProductsInCategory()[subcategoryKey]
        }
        this.context = context
    }
}