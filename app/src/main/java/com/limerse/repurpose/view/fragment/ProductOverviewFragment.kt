package com.limerse.repurpose.view.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.flaviofaria.kenburnsview.KenBurnsView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.limerse.repurpose.R
import com.limerse.repurpose.domain.mock.FakeWebServer
import com.limerse.repurpose.model.CenterRepository
import com.limerse.repurpose.util.AppConstants
import com.limerse.repurpose.util.Utils
import com.limerse.repurpose.view.activities.ECartHomeActivity
import com.limerse.repurpose.view.adapter.ProductsInCategoryPagerAdapter

class ProductOverviewFragment : Fragment() {
    private var header: KenBurnsView? = null
    private var mToolbar: Toolbar? = null
    private var viewPager: ViewPager? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var tabLayout: TabLayout? = null

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

        // TODO We Can use Async task But pallete creation is problemitic job
        // will
        // get back to it later

        // new ProductLoaderTask(null, getActivity(), viewPager, tabLayout);

        // Volley can be used here very efficiently but Fake JSON creation is
        // time consuming Leain it now
        viewPager = view.findViewById<View>(R.id.htab_viewpager) as ViewPager?
        collapsingToolbarLayout = view
            .findViewById<View>(R.id.htab_collapse_toolbar) as CollapsingToolbarLayout?
        collapsingToolbarLayout!!.setTitleEnabled(false)
        header = view.findViewById<View>(R.id.htab_header) as KenBurnsView?
        header!!.setImageResource(R.drawable.nav_header_bg)
        tabLayout = view.findViewById<View>(R.id.htab_tabs) as TabLayout?
        mToolbar = view.findViewById<View>(R.id.htab_toolbar) as Toolbar?
        if (mToolbar != null) {
            (activity as ECartHomeActivity?)!!.setSupportActionBar(mToolbar)
        }
        if (mToolbar != null) {
            (activity as ECartHomeActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            mToolbar!!.setNavigationIcon(R.drawable.ic_drawer)
        }
        mToolbar!!.setNavigationOnClickListener {
            (activity as ECartHomeActivity?)!!.getmDrawerLayout()!!
                .openDrawer(GravityCompat.START)
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
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager!!.currentItem = tab.position
                    when (tab.position) {
                        0 -> {
                            header!!.setImageResource(R.drawable.nav_header_bg)
                        }
                        1 -> {
                            header!!.setImageResource(R.drawable.nav_header_bg)
                        }
                        2 -> {
                            header!!.setImageResource(R.drawable.nav_header_bg)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ProductsInCategoryPagerAdapter(
            requireActivity().supportFragmentManager
        )
        val keys: Set<String> =
            CenterRepository.centerRepository!!.getMapOfProductsInCategory().keys
        for (string: String in keys) {
            adapter.addFrag(ProductListFragment(string), string)
        }
        viewPager!!.adapter = adapter
    }
}