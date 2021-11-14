package com.myntra.repurpose.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.BufferType
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myntra.repurpose.R
import com.myntra.repurpose.model.CenterRepository.Companion.centerRepository
import com.myntra.repurpose.model.entities.Money.Companion.rupees
import com.myntra.repurpose.model.entities.Product
import com.myntra.repurpose.util.Utils.vibrate
import com.myntra.repurpose.view.activities.ECartHomeActivity
import com.myntra.repurpose.view.adapter.ShoppingListAdapter.ItemViewHolder
import com.myntra.repurpose.view.customview.ItemTouchHelperAdapter
import com.myntra.repurpose.view.customview.ItemTouchHelperViewHolder
import com.myntra.repurpose.view.customview.OnStartDragListener
import com.myntra.repurpose.view.fragment.MyCartFragment
import java.math.BigDecimal
import java.util.*

class ShoppingListAdapter(private val context: Context, private val mDragStartListener: OnStartDragListener) : RecyclerView.Adapter<ItemViewHolder>(), ItemTouchHelperAdapter {
    private var ImageUrl: String? = null
    private var productList: List<Product?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemName.text = productList[position]!!.itemName
        holder.itemDesc.text = productList[position]!!.itemShortDesc
        val sellCostString = (rupees(
            BigDecimal.valueOf(
                java.lang.Long.valueOf(
                    productList[position]!!.sellMRP
                )
            )
        ).toString()
                + "  ")
        val buyMRP = rupees(
            BigDecimal.valueOf(
                java.lang.Long.valueOf(
                    productList[position]!!.mRP
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

        ImageUrl = productList[position]!!.imageURL
        holder.quanitity.text = centerRepository!!.listOfProductsInShoppingList[position]!!.quantity
        Glide.with(context).load(ImageUrl)
            .centerCrop().into(holder.imagView)

        // Start a drag whenever the handle view it touched
        holder.imagView.setOnTouchListener { v, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder)
            }
            false
        }
//        holder.addItem.setOnClickListener {
//            centerRepository!!.listOfProductsInShoppingList[position]
//                .quantity = (
//                    Integer.valueOf(
//                        centerRepository!!.listOfProductsInShoppingList[position]!!.quantity
//                    ) + 1).toString()
//            holder.quanitity.text = centerRepository!!.listOfProductsInShoppingList[position]!!.quantity
//            vibrate(context)
//            (context as ECartHomeActivity).updateCheckOutAmount(
//                BigDecimal.valueOf(
//                    java.lang.Long.valueOf(
//                        centerRepository!!.listOfProductsInShoppingList[position]!!.sellMRP
//                    )
//                ), true
//            )
//        }
        holder.removeItem.setOnClickListener {
            if (Integer.valueOf(
                    centerRepository!!.listOfProductsInShoppingList[position]!!.quantity
                ) > 2
            ) {
                centerRepository!!.listOfProductsInShoppingList[position]
                    .quantity = (
                        Integer.valueOf(
                            centerRepository!!.listOfProductsInShoppingList[position]
                                .quantity
                        ) - 1).toString()
                holder.quanitity.text =
                    centerRepository!!.listOfProductsInShoppingList[position]!!.quantity
                (context as ECartHomeActivity).updateCheckOutAmount(
                    BigDecimal.valueOf(
                        java.lang.Long.valueOf(
                            centerRepository!!.listOfProductsInShoppingList[position]!!.sellMRP
                        )
                    ), false
                )
                vibrate(context)
            } else if (Integer.valueOf(
                    centerRepository!!.listOfProductsInShoppingList[position]!!.quantity
                ) == 1
            ) {
                (context as ECartHomeActivity).updateItemCount(false)
                context.updateCheckOutAmount(
                    BigDecimal.valueOf(
                        java.lang.Long.valueOf(
                            centerRepository!!.listOfProductsInShoppingList[position]!!.sellMRP
                        )
                    ), false
                )
                centerRepository!!.listOfProductsInShoppingList
                    .removeAt(position)
                if (Integer.valueOf(
                        context.itemCount
                    ) == 0
                ) {
                    MyCartFragment.updateMyCartFragment(false)
                }
                vibrate(context)
            }
        }
    }

    override fun onItemDismiss(position: Int) {
        (context as ECartHomeActivity).updateItemCount(false)
        context.updateCheckOutAmount(
            BigDecimal.valueOf(
                java.lang.Long.valueOf(
                    centerRepository!!.listOfProductsInShoppingList[position]
                        .sellMRP
                )
            ), false
        )
        centerRepository!!.listOfProductsInShoppingList.removeAt(position)
        if (Integer.valueOf(context.itemCount) == 0) {
            MyCartFragment.updateMyCartFragment(false)
        }
        vibrate(context)

        // productList.remove(position);
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(productList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun SetOnItemClickListener(
        itemClickListener: OnItemClickListener
    ) {
        clickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    /**
     * Simple example of a view holder that implements
     * [ItemTouchHelperViewHolder] and has a "handle" view that initiates
     * a drag event when touched.
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ItemTouchHelperViewHolder, View.OnClickListener {
        // public final ImageView handleView;
        var itemName: TextView = itemView.findViewById<View>(R.id.item_name) as TextView
        var itemDesc: TextView = itemView.findViewById<View>(R.id.item_short_desc) as TextView
        var itemCost: TextView = itemView.findViewById<View>(R.id.item_price) as TextView
        var quanitity: TextView = itemView.findViewById<View>(R.id.iteam_amount) as TextView
        var removeItem: TextView
        var imagView: ImageView
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        override fun onClick(v: View) {
            clickListener!!.onItemClick(v, position)
        }

        init {
            // handleView = (ImageView) itemView.findViewById(R.id.handle);
            itemName.isSelected = true
            imagView = itemView.findViewById<View>(R.id.product_thumb) as ImageView
            //addItem = itemView.findViewById<View>(R.id.add_item) as TextView
            removeItem = itemView.findViewById<View>(R.id.remove_item) as TextView
            itemView.setOnClickListener(this)
        }
    }

    companion object {
        private var clickListener: OnItemClickListener? = null
    }

    init {
        productList = centerRepository!!.listOfProductsInShoppingList
    }
}