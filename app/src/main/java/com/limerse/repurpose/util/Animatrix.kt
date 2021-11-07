
package com.limerse.repurpose.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.animation.OvershootInterpolator
import android.widget.SeekBar
import android.view.animation.AccelerateDecelerateInterpolator
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils

object Animatrix {
    fun scale(view: View, delay: Long) {
        view.scaleX = 0f
        view.scaleY = 0f
        view.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setStartDelay(delay)
            .setInterpolator(OvershootInterpolator())
            .start()
    }

    fun slideUp(view: View, delay: Long) {
        view.translationY = 0f
        view.animate()
            .scaleY(1f)
            .setDuration(500)
            .setStartDelay(delay)
            .setInterpolator(OvershootInterpolator())
            .start()
    }

    fun animateSeekBar(seekBar: SeekBar) {
        seekBar.progress = 15
        val progressAnimator: ObjectAnimator = ObjectAnimator.ofInt(seekBar, "progress", 15, 0)
        progressAnimator.duration = 300
        progressAnimator.interpolator = AccelerateDecelerateInterpolator()
        progressAnimator.start()
    }

    @JvmStatic
    fun circularRevealView(revealLayout: View) {
        val cx: Int = revealLayout.width / 2
        val cy: Int = revealLayout.height / 2
        val finalRadius: Float =
            Math.max(revealLayout.width, revealLayout.height).toFloat()

        // create the animator for this view (the start radius is zero)
        var circularReveal: Animator? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal =
                ViewAnimationUtils.createCircularReveal(revealLayout, cx, cy, 0f, finalRadius)
            circularReveal.setDuration(1000)

            // make the view visible and start the animation
            revealLayout.visibility = View.VISIBLE
            circularReveal.start()
        } else {
            revealLayout.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    fun exitReveal(myView: View): Animator? {
        // previously visible view

        // get the center for the clipping circle
        val cx: Int = myView.measuredWidth / 2
        val cy: Int = myView.measuredHeight / 2


        // get the initial radius for the clipping circle
        val initialRadius: Int = myView.width / 2

        // create the animation (the final radius is zero)
        var anim: Animator? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius.toFloat(), 0f)

            // make the view invisible when the animation is done
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.INVISIBLE
                }
            })
        }

        //  anim.setDuration(800);

        // start the animation
        return anim
    }
}