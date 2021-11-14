package com.myntra.repurpose.view.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.myntra.repurpose.R
import com.myntra.repurpose.model.CenterRepository.Companion.centerRepository
import com.myntra.repurpose.model.entities.Money.Companion.rupees
import com.myntra.repurpose.util.ColorGenerator
import com.myntra.repurpose.view.customview.LabelView
import com.myntra.repurpose.view.customview.TextDrawable
import com.myntra.repurpose.view.customview.TextDrawable.IBuilder
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.math.BigDecimal


class SimilarProductsPagerAdapter(
    /**
     * The m context.
     */
    var mContext: Context, productCategory: String
) : PagerAdapter() {
    /**
     * The m layout inflater.
     */
    var mLayoutInflater: LayoutInflater = mContext
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val productCategory: String = productCategory
    private var imageView: ImageView? = null
    private var mDrawableBuilder: IBuilder? = null
    private var drawable: TextDrawable? = null
    private val mColorGenerator = ColorGenerator.MATERIAL

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    override fun getCount(): Int {
        return if (null != centerRepository!!.getMapOfProductsInCategory()
            && null != centerRepository!!.getMapOfProductsInCategory()[productCategory]
        ) {
            centerRepository!!.getMapOfProductsInCategory()[productCategory]!!.size
        } else 0
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
     * java.lang.Object)
     */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as FrameLayout
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup
     * , int)
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(
            R.layout.item_category_list, container,
            false
        )
        imageView = itemView.findViewById<View>(R.id.imageView) as ImageView
        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
            .endConfig().roundRect(10)
        drawable = mDrawableBuilder!!.build(
            centerRepository!!.getMapOfProductsInCategory()[productCategory]!![position]!!.itemName[0].toString(),
            mColorGenerator!!.getColor(
                centerRepository!!.getMapOfProductsInCategory()[productCategory]!![position]!!.itemName
            )
        )
        val ImageUrl =
            centerRepository!!.getMapOfProductsInCategory()[productCategory]!![position]!!.imageURL
        Picasso.get().load(ImageUrl).placeholder(drawable!!)
            .error(drawable!!).fit().centerCrop()
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(imageView, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception) {
                    // Try again online if cache failed
                    Picasso.get().load(ImageUrl)
                        .placeholder(drawable!!).error(drawable!!).fit()
                        .centerCrop().into(imageView)
                }
            })
        (itemView.findViewById<View>(R.id.item_name) as TextView).text =
            centerRepository!!.getMapOfProductsInCategory()[productCategory]!![position]!!.itemName
        (itemView.findViewById<View>(R.id.item_short_desc) as TextView).text =
            centerRepository!!.getMapOfProductsInCategory()[productCategory]!![position]!!.itemDetail
        (itemView.findViewById<View>(R.id.category_discount) as TextView).text = rupees(
            BigDecimal.valueOf(
                centerRepository!!.getMapOfProductsInCategory()[productCategory]!![position]!!.sellMRP.toLong()
            )
        ).toString()
        val label = LabelView(mContext)
        label.text = centerRepository!!.getMapOfProductsInCategory()[productCategory]!![position]!!.getDiscount()
        label.setBackgroundColor(-0x16e19d)
        label.setTargetView(
            itemView.findViewById(R.id.imageView), 10,
            LabelView.Gravity.RIGHT_TOP
        )
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }



    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#instantiateItem(android.view.View,
     * int)
     */
    override fun instantiateItem(arg0: View, arg1: Int): Any {
        return "null"
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#saveState()
     */
    override fun saveState(): Parcelable? {
        // TODO Auto-generated method stub
        return null
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getPageWidth(int)
     */
    override fun getPageWidth(position: Int): Float {
        return 0.5f
    }
}