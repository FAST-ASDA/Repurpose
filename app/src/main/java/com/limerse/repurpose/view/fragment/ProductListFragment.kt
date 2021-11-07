
package com.limerse.repurpose.view.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.limerse.repurpose.R
import com.limerse.repurpose.util.Utils
import com.limerse.repurpose.util.Utils.AnimationType
import com.limerse.repurpose.util.Utils.switchFragmentWithAnimation
import com.limerse.repurpose.view.activities.ECartHomeActivity
import com.limerse.repurpose.view.adapter.ProductListAdapter

class ProductListFragment : Fragment {
    private var subcategoryKey: String? = null
    private var isShoppingList: Boolean

    constructor() {
        isShoppingList = true
    }

    constructor(subcategoryKey: String?) {
        isShoppingList = false
        this.subcategoryKey = subcategoryKey
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.frag_product_list_fragment, container,
            false
        )
        if (isShoppingList) {
            view.findViewById<View>(R.id.slide_down).visibility = View.VISIBLE
            view.findViewById<View>(R.id.slide_down)
                .setOnTouchListener { v, event -> //							Utils.switchContent(R.id.top_container,
//									Utils.HOME_FRAGMENT,
//									((ECartHomeActivity) (getContext())),
//									AnimationType.SLIDE_DOWN);
                    switchFragmentWithAnimation(
                        R.id.frag_container,
                        HomeFragment(),
                        (context as ECartHomeActivity?)!!, Utils.HOME_FRAGMENT,
                        AnimationType.SLIDE_DOWN
                    )
                    false
                }
        }

        // Fill Recycler View
        val recyclerView = view
            .findViewById<View>(R.id.product_list_recycler_view) as RecyclerView
        val linearLayoutManager = LinearLayoutManager(
            requireActivity().baseContext
        )
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        val adapter = ProductListAdapter(
            subcategoryKey,
            requireActivity(), isShoppingList
        )
        recyclerView.adapter = adapter
        adapter.SetOnItemClickListener(object : ProductListAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                switchFragmentWithAnimation(
                    R.id.frag_container,
                    ProductDetailsFragment(subcategoryKey, position, false),
                    (context as ECartHomeActivity?)!!, null,
                    AnimationType.SLIDE_LEFT
                )
            }
        })

        // Handle Back press
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP
                && keyCode == KeyEvent.KEYCODE_BACK
            ) {

//					Utils.switchContent(R.id.top_container,
//							Utils.HOME_FRAGMENT,
//							((ECartHomeActivity) (getContext())),
//							AnimationType.SLIDE_UP);
                switchFragmentWithAnimation(
                    R.id.frag_container,
                    HomeFragment(),
                    (context as ECartHomeActivity?)!!, Utils.HOME_FRAGMENT,
                    AnimationType.SLIDE_UP
                )
            }
            true
        }
        return view
    }
}