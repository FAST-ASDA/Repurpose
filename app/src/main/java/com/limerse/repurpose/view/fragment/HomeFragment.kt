
package com.limerse.repurpose.view.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.limerse.repurpose.R
import com.limerse.repurpose.domain.api.ProductCategoryLoaderTask
import com.limerse.repurpose.util.Utils.AnimationType
import com.limerse.repurpose.util.Utils.switchFragmentWithAnimation
import com.limerse.repurpose.view.activities.ECartHomeActivity

class HomeFragment : Fragment() {
    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var appBarLayout: AppBarLayout? = null
    private var recyclerView: RecyclerView? = null
    private var mutedColor = R.attr.colorPrimary
    /**
     * The double back to exit pressed once.
     */
    private var doubleBackToExitPressedOnce = false
    private val mRunnable = Runnable { doubleBackToExitPressedOnce = false }
    private val mHandler = Handler()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_product_category, container, false)
        view.findViewById<View>(R.id.search_item).setOnClickListener {
            switchFragmentWithAnimation(
                R.id.frag_container,
                SearchProductFragment(),
                (activity as ECartHomeActivity?)!!, null,
                AnimationType.SLIDE_UP
            )
        }
        val toolbar = view.findViewById<View>(R.id.anim_toolbar) as Toolbar
        (activity as ECartHomeActivity?)!!.setSupportActionBar(toolbar)
        (activity as ECartHomeActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_drawer)
        toolbar.setNavigationOnClickListener {
            (activity as ECartHomeActivity?)!!.getmDrawerLayout()!!.openDrawer(GravityCompat.START)
        }
        collapsingToolbar = view.findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        appBarLayout = view.findViewById<View>(R.id.appbar) as AppBarLayout
        val bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.nav_header_bg
        )
        Palette.from(bitmap).generate { palette ->
            mutedColor = palette!!.getMutedColor(R.color.black)
            collapsingToolbar!!.setContentScrimColor(mutedColor)
            collapsingToolbar!!.setStatusBarScrimColor(R.color.black_trans80)
        }
        var isShow = true
        var scrollRange = -1
        appBarLayout!!.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = barLayout?.totalScrollRange!!
            }
            when {
                scrollRange + verticalOffset == 0 -> {
                    collapsingToolbar!!.title = "Repurpose"
                    isShow = true
                }
                isShow -> {
                    collapsingToolbar!!.title = " "
                    isShow = false
                }
            }
        })

        recyclerView = view.findViewById<View>(R.id.scrollableview) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        ProductCategoryLoaderTask(recyclerView, requireActivity()).execute()

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP
                && keyCode == KeyEvent.KEYCODE_BACK
            ) {
                if (doubleBackToExitPressedOnce) {
                    // super.onBackPressed();
                    mHandler.removeCallbacks(mRunnable)
                    requireActivity().finish()
                    return@OnKeyListener true
                }
                doubleBackToExitPressedOnce = true
                Toast.makeText(
                    activity,
                    "Please click BACK again to exit",
                    Toast.LENGTH_SHORT
                ).show()
                mHandler.postDelayed(mRunnable, 2000)
            }
            true
        })
        return view
    }
}