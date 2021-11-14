
package com.myntra.repurpose.view.customview

import android.graphics.RectF

/**
 * Helper class to perform math computations.
 */
object MathUtils {
    /**
     * Truncates a float number `f` to `decimalPlaces`.
     *
     * @param f             the number to be truncated.
     * @param decimalPlaces the amount of decimals that `f`
     * will be truncated to.
     * @return a truncated representation of `f`.
     */
    internal fun truncate(f: Float, decimalPlaces: Int): Float {
        val decimalShift = Math.pow(10.0, decimalPlaces.toDouble()).toFloat()
        return Math.round(f * decimalShift) / decimalShift
    }

    /**
     * Checks whether two [RectF] have the same aspect ratio.
     *
     * @param r1 the first rect.
     * @param r2 the second rect.
     * @return `true` if both rectangles have the same aspect ratio,
     * `false` otherwise.
     */
    internal fun haveSameAspectRatio(r1: RectF, r2: RectF): Boolean {
        // Reduces precision to avoid problems when comparing aspect ratios.
        val srcRectRatio = truncate(getRectRatio(r1), 3)
        val dstRectRatio = truncate(getRectRatio(r2), 3)

        // Compares aspect ratios that allows for a tolerance range of [0, 0.01] 
        return Math.abs(srcRectRatio - dstRectRatio) <= 0.01f
    }

    /**
     * Computes the aspect ratio of a given rect.
     *
     * @param rect the rect to have its aspect ratio computed.
     * @return the rect aspect ratio.
     */
    internal fun getRectRatio(rect: RectF): Float {
        return rect.width() / rect.height()
    }
}