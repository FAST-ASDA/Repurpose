
package com.limerse.repurpose.domain.api

import android.content.Context
import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.limerse.repurpose.domain.mock.FakeWebServer.Companion.fakeWebServer
import com.limerse.repurpose.model.CenterRepository
import com.limerse.repurpose.view.activities.ECartHomeActivity
import com.limerse.repurpose.view.adapter.ProductsInCategoryPagerAdapter
import com.limerse.repurpose.view.fragment.ProductListFragment

/**
 * The Class ImageLoaderTask.
 */
class ProductLoaderTask(
    listView: RecyclerView?, private val context: Context,
    private val viewPager: ViewPager, private val tabs: TabLayout
) : AsyncTask<String?, Void?, Void?>() {
    private val recyclerView: RecyclerView? = null
    override fun onPreExecute() {
        super.onPreExecute()
        if (null != (context as ECartHomeActivity).progressBar) context.progressBar!!.visibility =
            View.VISIBLE
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if (null != (context as ECartHomeActivity).progressBar) context.progressBar!!.visibility =
            View.GONE
        setUpUi()
    }

    override fun doInBackground(vararg p0: String?): Void? {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        fakeWebServer!!.allElectronics
        return null
    }

    private fun setUpUi() {
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ProductsInCategoryPagerAdapter(
            (context as ECartHomeActivity).supportFragmentManager
        )
        val keys: Set<String> = CenterRepository.centerRepository!!.mapOfProductsInCategory
            .keys
        for (string in keys) {
            adapter.addFrag(ProductListFragment(string), string)
        }
        viewPager.adapter = adapter

//		viewPager.setPageTransformer(true,
//				new );
        tabs.setupWithViewPager(viewPager)
    }
}