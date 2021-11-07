package com.limerse.repurpose.domain.api

import android.content.Context
import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.limerse.repurpose.R
import com.limerse.repurpose.domain.mock.FakeWebServer.Companion.fakeWebServer
import com.limerse.repurpose.util.AppConstants
import com.limerse.repurpose.util.Utils
import com.limerse.repurpose.util.Utils.AnimationType
import com.limerse.repurpose.view.activities.ECartHomeActivity
import com.limerse.repurpose.view.adapter.CategoryListAdapter
import com.limerse.repurpose.view.fragment.ProductOverviewFragment

class ProductCategoryLoaderTask(private val recyclerView: RecyclerView?, private val context: Context) : AsyncTask<String?, Void?, Void?>() {
    override fun onPreExecute() {
        super.onPreExecute()
        if (null != (context as ECartHomeActivity).progressBar){
            context.progressBar!!.visibility = View.VISIBLE
        }
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if (null != (context as ECartHomeActivity).progressBar){
            context.progressBar!!.visibility = View.GONE
        }
        if (recyclerView != null) {
            val simpleRecyclerAdapter = CategoryListAdapter(context)
            recyclerView.adapter = simpleRecyclerAdapter
            simpleRecyclerAdapter.SetOnItemClickListener(object: CategoryListAdapter.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        AppConstants.CURRENT_CATEGORY = position
                        Utils.switchFragmentWithAnimation(
                            R.id.frag_container,
                            ProductOverviewFragment(),
                            context, null,
                            AnimationType.SLIDE_LEFT
                        )
                    }
                })
        }
    }

    override fun doInBackground(vararg p0: String?): Void? {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        fakeWebServer!!.addCategory()
        return null
    }
}