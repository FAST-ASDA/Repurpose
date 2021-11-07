
package com.limerse.repurpose.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limerse.repurpose.R
import com.limerse.repurpose.model.CenterRepository.Companion.centerRepository
import com.limerse.repurpose.model.entities.ProductCategoryModel
import com.limerse.repurpose.util.ColorGenerator
import com.limerse.repurpose.view.customview.LabelView
import com.limerse.repurpose.view.customview.TextDrawable
import com.limerse.repurpose.view.customview.TextDrawable.IBuilder
import java.util.*


class CategoryListAdapter(context: Context) :
    RecyclerView.Adapter<CategoryListAdapter.VersionViewHolder>() {
    var clickListener: OnItemClickListener? = null
    private val mColorGenerator = ColorGenerator.MATERIAL
    private var mDrawableBuilder: IBuilder? = null
    private var drawable: TextDrawable? = null
    private var ImageUrl: String? = null
    private val context: Context
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): VersionViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_category_list, viewGroup, false
        )
        return VersionViewHolder(view)
    }

    override fun onBindViewHolder(
        versionViewHolder: VersionViewHolder,
        categoryIndex: Int
    ) {
        versionViewHolder.itemName.text = categoryList!![categoryIndex]
            .productCategoryName
        versionViewHolder.itemDesc.text =
            categoryList!![categoryIndex]
                .productCategoryDescription
        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
            .endConfig().roundRect(10)
        drawable = mDrawableBuilder!!.build(
            categoryList!![categoryIndex].productCategoryName[0].toString(),
            mColorGenerator!!.getColor(
                categoryList!![categoryIndex]
                    .productCategoryName
            )
        )
        ImageUrl = categoryList!![categoryIndex].productCategoryImageUrl
        Glide.with(context).load(ImageUrl).placeholder(drawable)
            .error(drawable)
            .centerCrop().into(versionViewHolder.imagView)
        val label = LabelView(context)
        label.text = categoryList!![categoryIndex]
            .productCategoryDiscount
        label.setBackgroundColor(-0x16e19d)
        label.setTargetView(
            versionViewHolder.imagView, 10,
            LabelView.Gravity.RIGHT_TOP
        )
    }

    override fun getItemCount(): Int {
        return if (categoryList == null) 0 else categoryList!!.size
    }

    fun SetOnItemClickListener(
        itemClickListener: OnItemClickListener?
    ) {
        clickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    inner class VersionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var itemName: TextView = itemView.findViewById<View>(R.id.item_name) as TextView
        var itemDesc: TextView = itemView.findViewById<View>(R.id.item_short_desc) as TextView
        var itemCost: TextView? = null
        var availability: TextView? = null
        var quanitity: TextView? = null
        var addItem: TextView? = null
        var removeItem: TextView? = null
        var imagView: ImageView
        override fun onClick(v: View) {
            clickListener!!.onItemClick(v, position)
        }

        init {
            itemName.isSelected = true
            imagView = itemView.findViewById<View>(R.id.imageView) as ImageView
            itemView.setOnClickListener(this)
        }
    }

    companion object {
        var categoryList: List<ProductCategoryModel>? = ArrayList()
    }

    init {
        categoryList = centerRepository!!.listOfCategory
        this.context = context
    }
}