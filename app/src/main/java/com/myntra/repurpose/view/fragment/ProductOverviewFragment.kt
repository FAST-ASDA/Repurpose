package com.myntra.repurpose.view.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.myntra.repurpose.R
import com.myntra.repurpose.domain.mock.FakeWebServer
import com.myntra.repurpose.model.CenterRepository
import com.myntra.repurpose.util.AppConstants
import com.myntra.repurpose.util.Utils
import com.myntra.repurpose.view.activities.ECartHomeActivity
import com.myntra.repurpose.view.adapter.ProductsInCategoryPagerAdapter

class ProductOverviewFragment : Fragment() {
    private var mToolbar: Toolbar? = null
    private var viewPager: ViewPager? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(
            R.layout.frag_category_details,
            container, false
        )
        requireActivity().title = "Products"

        // Simulate Web service calls
        FakeWebServer.fakeWebServer!!.getAllProducts(
            AppConstants.CURRENT_CATEGORY
        )

        viewPager = view.findViewById<View>(R.id.htab_viewpager) as ViewPager?
        collapsingToolbarLayout = view.findViewById<View>(R.id.htab_collapse_toolbar) as CollapsingToolbarLayout?
        collapsingToolbarLayout!!.isTitleEnabled = false
        mToolbar = view.findViewById<View>(R.id.htab_toolbar) as Toolbar?
        if (mToolbar != null) {
            (activity as ECartHomeActivity?)!!.setSupportActionBar(mToolbar)
        }
        if (mToolbar != null) {
            (activity as ECartHomeActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            mToolbar!!.setNavigationIcon(R.drawable.ic_drawer)
        }
        mToolbar!!.setNavigationOnClickListener {
            (activity as ECartHomeActivity?)!!.getmDrawerLayout()!!.openDrawer(GravityCompat.START)
        }
        setUpUi()
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK)
            ) {
                Utils.switchContent(
                    R.id.frag_container,
                    Utils.HOME_FRAGMENT,
                    ((context) as ECartHomeActivity?)!!,
                    Utils.AnimationType.SLIDE_RIGHT
                )
            }
            true
        }
        return view
    }

    private fun setUpUi() {
        setupViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ProductsInCategoryPagerAdapter(requireActivity().supportFragmentManager)
        val keys: Set<String> = CenterRepository.centerRepository!!.getMapOfProductsInCategory().keys
        for (string: String in keys) {
            adapter.addFrag(ProductListFragment(string), string)
        }
        viewPager!!.adapter = adapter
    }
}