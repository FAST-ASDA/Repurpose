package com.limerse.repurpose.view.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.limerse.repurpose.R
import com.limerse.repurpose.view.activities.ECartHomeActivity
import com.limerse.slider.ImageCarousel
import com.limerse.slider.listener.CarouselListener
import com.limerse.slider.model.CarouselGravity
import com.limerse.slider.model.CarouselItem
import com.limerse.slider.model.CarouselType
import com.limerse.slider.utils.dpToPx
import com.limerse.slider.utils.spToPx

class SellerProfileFragment : Fragment() {
    private var mToolbar: Toolbar? = null
    private var carousel1: ImageCarousel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frag_seller_profile, container, false)
        mToolbar = rootView.findViewById<View>(R.id.htab_toolbar) as Toolbar
        carousel1 = rootView.findViewById<View>(R.id.carousel1) as ImageCarousel
        if (mToolbar != null) {
            (activity as ECartHomeActivity?)!!.setSupportActionBar(mToolbar)
        }
        if (mToolbar != null) {
            (activity as ECartHomeActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            mToolbar!!.setNavigationIcon(R.drawable.ic_drawer)
        }
        mToolbar!!.setTitleTextColor(Color.WHITE)
        mToolbar!!.title = "akshaaatt"
        mToolbar!!.setNavigationOnClickListener {
            (activity as ECartHomeActivity?)!!.getmDrawerLayout()!!.openDrawer(GravityCompat.START)
        }
        (activity as ECartHomeActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fetchLocalData()
        return rootView
    }

    private fun fetchLocalData() {
        carousel1?.apply {
            registerLifecycle(lifecycle)

            showTopShadow = true
            topShadowAlpha = 0.15f // 0 to 1, 1 means 100%
            topShadowHeight = 32.dpToPx(context) // px value of dp

            showBottomShadow = true
            bottomShadowAlpha = 0.6f // 0 to 1, 1 means 100%
            bottomShadowHeight = 64.dpToPx(context) // px value of dp

            showCaption = true
            captionMargin = 0.dpToPx(context) // px value of dp
            captionTextSize = 14.spToPx(context) // px value of sp

            showIndicator = true
            indicatorMargin = 0.dpToPx(context) // px value of dp

            imageScaleType = ImageView.ScaleType.CENTER_CROP

            carouselBackground = ColorDrawable(Color.parseColor("#333333"))

            carouselPadding = 0.dpToPx(context)
            carouselPaddingStart = 0.dpToPx(context)
            carouselPaddingTop = 0.dpToPx(context)
            carouselPaddingEnd = 0.dpToPx(context)
            carouselPaddingBottom = 0.dpToPx(context)

            showNavigationButtons = true
            previousButtonLayout = R.layout.previous_button_layout
            previousButtonId = R.id.btn_previous
            previousButtonMargin = 4.dpToPx(context) // px value of dp
            nextButtonLayout = R.layout.next_button_layout
            nextButtonId = R.id.btn_next
            nextButtonMargin = 4.dpToPx(context) // px value of dp

            carouselType = CarouselType.BLOCK
            carouselGravity = CarouselGravity.CENTER

            scaleOnScroll = false
            scalingFactor = .15f // 0 to 1; 1 means 100
            autoWidthFixing = true
            autoPlay = true
            autoPlayDelay = 3000 // Milliseconds
            infiniteCarousel = true
            touchToPause = true

            carouselListener = object : CarouselListener {
                override fun onClick(position: Int, carouselItem: CarouselItem) {

                }

                override fun onLongClick(position: Int, carouselItem: CarouselItem) {}
            }
        }

        val listOne = mutableListOf<CarouselItem>()
        val three = listOf(
            "https://images.unsplash.com/photo-1611312449408-fcece27cdbb7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=738&q=80" to "Anikash Says: \"The quality has been really great and totally loved shopping!\"",
            "https://images.unsplash.com/photo-1605518216938-7c31b7b14ad0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2018&q=80" to "Shiva Says: \"With Myntra backing this product, I trusted it.\"",
            "https://images.unsplash.com/photo-1582897085656-c636d006a246?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80" to "Divyansh Says: \"At first I didn't believe it. But again, Hey my new shoes!\"",
            "https://images.unsplash.com/photo-1594938298603-c8148c4dae35?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1160&q=80" to "Anikash Says: \"Marvelous that my size is available!\"",
        )

        for (item in three) {
            listOne.add(
                CarouselItem(
                    imageUrl = item.first,
                    caption = item.second,
                )
            )
        }
       carousel1!!.setData(listOne)
    }

}