
package com.myntra.repurpose.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myntra.repurpose.R
import com.myntra.repurpose.model.CenterRepository.Companion.centerRepository
import com.myntra.repurpose.util.Utils
import com.myntra.repurpose.util.Utils.AnimationType
import com.myntra.repurpose.util.Utils.switchContent
import com.myntra.repurpose.util.Utils.switchFragmentWithAnimation
import com.myntra.repurpose.view.activities.ECartHomeActivity
import com.myntra.repurpose.view.adapter.ShoppingListAdapter
import com.myntra.repurpose.view.customview.OnStartDragListener
import com.myntra.repurpose.view.customview.SimpleItemTouchHelperCallback

class MyCartFragment : Fragment(), OnStartDragListener {
    private var mItemTouchHelper: ItemTouchHelper? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.frag_product_list_fragment, container,
            false
        )
        view.findViewById<View>(R.id.slide_down).visibility = View.VISIBLE
        view.findViewById<View>(R.id.slide_down).setOnTouchListener { v, event ->
            switchFragmentWithAnimation(
                R.id.frag_container,
                HomeFragment(),
                (context as ECartHomeActivity?)!!,
                Utils.HOME_FRAGMENT, AnimationType.SLIDE_DOWN
            )
            false
        }

        // Fill Recycler View
        noItemDefault = view.findViewById<View>(R.id.default_nodata) as FrameLayout
        recyclerView = view
            .findViewById<View>(R.id.product_list_recycler_view) as RecyclerView
        if (centerRepository!!.listOfProductsInShoppingList.size != 0) {
            val linearLayoutManager = LinearLayoutManager(
                requireActivity().baseContext
            )
            recyclerView!!.layoutManager = linearLayoutManager
            recyclerView!!.setHasFixedSize(true)
            val shoppinListAdapter = ShoppingListAdapter(
                requireActivity(), this
            )
            recyclerView!!.adapter = shoppinListAdapter
            shoppinListAdapter
                .SetOnItemClickListener(object : ShoppingListAdapter.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        switchFragmentWithAnimation(
                            R.id.frag_container,
                            ProductDetailsFragment(
                                "", position,
                                true
                            ),
                            (context as ECartHomeActivity?)!!, null,
                            AnimationType.SLIDE_LEFT
                        )
                    }
                })
            val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(
                shoppinListAdapter
            )
            mItemTouchHelper = ItemTouchHelper(callback)
            mItemTouchHelper!!.attachToRecyclerView(recyclerView)
        } else {
            updateMyCartFragment(false)
        }
        view.findViewById<View>(R.id.start_shopping).setOnClickListener {
            switchContent(
                R.id.frag_container,
                Utils.HOME_FRAGMENT,
                (context as ECartHomeActivity?)!!,
                AnimationType.SLIDE_UP
            )
        }

        // Handle Back press
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP
                && keyCode == KeyEvent.KEYCODE_BACK
            ) {
                switchContent(
                    R.id.frag_container,
                    Utils.HOME_FRAGMENT,
                    (context as ECartHomeActivity?)!!,
                    AnimationType.SLIDE_UP
                )
            }
            true
        }
        return view
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper!!.startDrag(viewHolder!!)
    }

    companion object {
        private var noItemDefault: FrameLayout? = null
        private var recyclerView: RecyclerView? = null

        /**
         * @param rootView
         * @param myCartListView
         */
        fun updateMyCartFragment(showList: Boolean) {
            if (showList) {
                if (null != recyclerView && null != noItemDefault) {
                    recyclerView!!.visibility = View.VISIBLE
                    noItemDefault!!.visibility = View.GONE
                }
            } else {
                if (null != recyclerView && null != noItemDefault) {
                    recyclerView!!.visibility = View.GONE
                    noItemDefault!!.visibility = View.VISIBLE
                }
            }
        }
    }
}