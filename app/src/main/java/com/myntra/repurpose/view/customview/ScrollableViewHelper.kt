
package com.myntra.repurpose.view.customview

import android.view.View
import android.widget.ListView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView

/**
 * Helper class for determining the current scroll positions for scrollable views. Currently works
 * for ListView, ScrollView & RecyclerView, but the library users can override it to add support
 * for other views.
 */
class ScrollableViewHelper {
    /**
     * Returns the current scroll position of the scrollable view. If this method returns zero or
     * less, it means at the scrollable view is in a position such as the panel should handle
     * scrolling. If the method returns anything above zero, then the panel will let the scrollable
     * view handle the scrolling
     *
     * @param scrollableView the scrollable view
     * @param isSlidingUp    whether or not the panel is sliding up or down
     * @return the scroll position
     */
    fun getScrollableViewScrollPosition(scrollableView: View?, isSlidingUp: Boolean): Int {
        if (scrollableView == null) return 0
        return if (scrollableView is ScrollView) {
            if (isSlidingUp) {
                scrollableView.getScrollY()
            } else {
                val sv = scrollableView
                val child = sv.getChildAt(0)
                child.bottom - (sv.height + sv.scrollY)
            }
        } else if (scrollableView is ListView && scrollableView.childCount > 0) {
            val lv = scrollableView
            if (lv.adapter == null) return 0
            if (isSlidingUp) {
                val firstChild = lv.getChildAt(0)
                // Approximate the scroll position based on the top child and the first visible item
                lv.firstVisiblePosition * firstChild.height - firstChild.top
            } else {
                val lastChild = lv.getChildAt(lv.childCount - 1)
                // Approximate the scroll position based on the bottom child and the last visible item
                (lv.adapter.count - lv.lastVisiblePosition - 1) * lastChild.height + lastChild.bottom - lv.bottom
            }
        } else if (scrollableView is RecyclerView && scrollableView.childCount > 0) {
            val rv = scrollableView
            val lm = rv.layoutManager
            if (rv.adapter == null) return 0
            if (isSlidingUp) {
                val firstChild = rv.getChildAt(0)
                // Approximate the scroll position based on the top child and the first visible item
                rv.getChildLayoutPosition(firstChild) * lm!!.getDecoratedMeasuredHeight(firstChild) - lm.getDecoratedTop(
                    firstChild
                )
            } else {
                val lastChild = rv.getChildAt(rv.childCount - 1)
                // Approximate the scroll position based on the bottom child and the last visible item
                (rv.adapter!!
                    .itemCount - 1) * lm!!.getDecoratedMeasuredHeight(lastChild) + lm.getDecoratedBottom(
                    lastChild
                ) - rv.bottom
            }
        } else {
            0
        }
    }
}