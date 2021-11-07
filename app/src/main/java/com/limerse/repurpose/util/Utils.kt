package com.limerse.repurpose.util

import android.content.Context
import android.content.CursorLoader
import android.graphics.Typeface
import com.limerse.repurpose.R
import androidx.core.graphics.drawable.DrawableCompat
import android.graphics.PorterDuff
import android.provider.MediaStore
import androidx.fragment.app.FragmentActivity
import com.limerse.repurpose.view.fragment.HomeFragment
import com.limerse.repurpose.view.fragment.MyCartFragment
import com.limerse.repurpose.view.fragment.SettingsFragment
import com.limerse.repurpose.view.fragment.ContactUsFragment
import com.limerse.repurpose.view.fragment.ProductOverviewFragment
import android.os.Vibrator
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.fragment.app.Fragment
import java.util.HashMap

object Utils {
    const val ATTRIBUTE_TTF_KEY = "ttf_name"
    const val ATTRIBUTE_SCHEMA = "http://schemas.android.com/apk/lib/com.myntra.repurpose.util"
    const val SHOPPING_LIST_TAG = "SHoppingListFragment"
    const val PRODUCT_OVERVIEW_FRAGMENT_TAG = "ProductOverView"
    const val MY_CART_FRAGMENT = "MyCartFragment"
    const val MY_ORDERS_FRAGMENT = "MYOrdersFragment"
    const val HOME_FRAGMENT = "HomeFragment"
    const val SEARCH_FRAGMENT_TAG = "SearchFragment"
    const val SETTINGS_FRAGMENT_TAG = "SettingsFragment"
    const val OTP_LOGIN_TAG = "OTPLogingFragment"
    const val CONTACT_US_FRAGMENT = "ContactUs"
    private const val PREFERENCES_FILE = "materialsample_settings"
    private var CURRENT_TAG: String? = null
    private val TYPEFACE: MutableMap<String?, Typeface?> = HashMap()
    fun getToolbarHeight(context: Context): Int {
        return context.resources.getDimension(
            R.dimen.abc_action_bar_default_height_material
        ).toInt()
    }

    fun getStatusBarHeight(context: Context): Int {
        return context.resources.getDimension(
            R.dimen.statusbar_size
        ).toInt()
    }

    fun tintMyDrawable(drawable: Drawable?, color: Int): Drawable? {
        var drawable = drawable
        drawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(drawable, color)
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
        return drawable
    }

    fun getRealPathFromURI(contentUri: Uri?, mContext: Context?): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(
            mContext, contentUri, proj,
            null, null, null
        )
        val cursor = loader.loadInBackground()
        val column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result
    }

    /**
     * Convert milliseconds into time hh:mm:ss
     *
     * @param milliseconds
     * @return time in String
     */
    fun getDuration(milliseconds: Long): String {
        val sec = milliseconds / 1000 % 60
        val min = milliseconds / (60 * 1000) % 60
        val hour = milliseconds / (60 * 60 * 1000)
        val s = if (sec < 10) "0$sec" else "" + sec
        val m = if (min < 10) "0$min" else "" + min
        val h = "" + hour
        var time = ""
        time = if (hour > 0) {
            "$h:$m:$s"
        } else {
            "$m:$s"
        }
        return time
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    @JvmStatic
    fun switchFragmentWithAnimation(
        id: Int, fragment: Fragment?,
        activity: FragmentActivity, TAG: String?, transitionStyle: AnimationType?
    ) {
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager
            .beginTransaction()
        if (transitionStyle != null) {
            when (transitionStyle) {
                AnimationType.SLIDE_DOWN ->
                    // Exit from down
                    fragmentTransaction.setCustomAnimations(
                        R.anim.slide_up,
                        R.anim.slide_down
                    )
                AnimationType.SLIDE_UP ->
                    // Enter from Up
                    fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_up,
                        R.anim.slide_out_up
                    )
                AnimationType.SLIDE_LEFT ->
                    // Enter from left
                    fragmentTransaction.setCustomAnimations(
                        R.anim.slide_left,
                        R.anim.slide_out_left
                    )
                AnimationType.SLIDE_RIGHT -> fragmentTransaction.setCustomAnimations(
                    R.anim.slide_right,
                    R.anim.slide_out_right
                )
                AnimationType.FADE_IN -> {
                    fragmentTransaction.setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                    fragmentTransaction.setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.donot_move
                    )
                }
                AnimationType.FADE_OUT -> fragmentTransaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.donot_move
                )
                AnimationType.SLIDE_IN_SLIDE_OUT -> fragmentTransaction.setCustomAnimations(
                    R.anim.slide_left,
                    R.anim.slide_out_left
                )
                else -> {
                }
            }
        }
        CURRENT_TAG = TAG
        fragmentTransaction.replace(id, fragment!!)
        fragmentTransaction.addToBackStack(TAG)
        fragmentTransaction.commit()
    }

    @JvmStatic
    fun switchContent(
        id: Int, TAG: String,
        baseActivity: FragmentActivity, transitionStyle: AnimationType?
    ) {
        var fragmentToReplace: Fragment? = null
        val fragmentManager = baseActivity
            .supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        // If our current fragment is null, or the new fragment is different, we
        // need to change our current fragment
        if (CURRENT_TAG == null || TAG != CURRENT_TAG) {
            if (transitionStyle != null) {
                when (transitionStyle) {
                    AnimationType.SLIDE_DOWN ->                         // Exit from down
                        transaction.setCustomAnimations(
                            R.anim.slide_up,
                            R.anim.slide_down
                        )
                    AnimationType.SLIDE_UP ->                         // Enter from Up
                        transaction.setCustomAnimations(
                            R.anim.slide_in_up,
                            R.anim.slide_out_up
                        )
                    AnimationType.SLIDE_LEFT ->                         // Enter from left
                        transaction.setCustomAnimations(
                            R.anim.slide_left,
                            R.anim.slide_out_left
                        )
                    AnimationType.SLIDE_RIGHT -> transaction.setCustomAnimations(
                        R.anim.slide_right,
                        R.anim.slide_out_right
                    )
                    AnimationType.FADE_IN -> {
                        transaction.setCustomAnimations(
                            R.anim.fade_in,
                            R.anim.fade_out
                        )
                        transaction.setCustomAnimations(
                            R.anim.fade_in,
                            R.anim.donot_move
                        )
                    }
                    AnimationType.FADE_OUT -> transaction.setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.donot_move
                    )
                    AnimationType.SLIDE_IN_SLIDE_OUT -> transaction.setCustomAnimations(
                        R.anim.slide_left,
                        R.anim.slide_out_left
                    )
                    else -> {
                    }
                }
            }

            // Try to find the fragment we are switching to
            val fragment = fragmentManager.findFragmentByTag(TAG)

            // If the new fragment can't be found in the manager, create a new
            // one
            if (fragment == null) {
                if (TAG == HOME_FRAGMENT) {
                    fragmentToReplace = HomeFragment()
                } else if (TAG == SHOPPING_LIST_TAG) {
                    fragmentToReplace = MyCartFragment()
                } else if (TAG == SETTINGS_FRAGMENT_TAG) {
                    fragmentToReplace = SettingsFragment()
                } else if (TAG == CONTACT_US_FRAGMENT) {
                    fragmentToReplace = ContactUsFragment()
                } else if (TAG == PRODUCT_OVERVIEW_FRAGMENT_TAG) {
                    fragmentToReplace = ProductOverviewFragment()
                } else if (TAG == SHOPPING_LIST_TAG) {
                    fragmentToReplace = MyCartFragment()
                }
            } else {
                if (TAG == HOME_FRAGMENT) {
                    fragmentToReplace = fragment as HomeFragment?
                } else if (TAG == SHOPPING_LIST_TAG) {
                    fragmentToReplace = fragment as MyCartFragment?
                } else if (TAG == PRODUCT_OVERVIEW_FRAGMENT_TAG) {
                    fragmentToReplace = fragment as ProductOverviewFragment?
                } else if (TAG == SETTINGS_FRAGMENT_TAG) {
                    fragmentToReplace = fragment as SettingsFragment?
                } else if (TAG == CONTACT_US_FRAGMENT) {
                    fragmentToReplace = fragment as ContactUsFragment?
                }
            }
            CURRENT_TAG = TAG

            // Replace our current fragment with the one we are changing to
            transaction.replace(id, fragmentToReplace!!, TAG)
            transaction.commit()
        } else {
            // Do nothing since we are already on the fragment being changed to
        }
    }

    @JvmStatic
    fun vibrate(context: Context) {
        // Get instance of Vibrator from current Context and Vibrate for 400
        // milliseconds
        (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
            .vibrate(100)
    }

    @JvmStatic
    fun getVersion(context: Context): String {
        return try {
            val pInfo = context.packageManager.getPackageInfo(
                context.packageName, PackageManager.GET_META_DATA
            )
            pInfo.versionCode.toString() + " " + pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "1.0.1"
        }
    }

    fun getFonts(context: Context, fontName: String?): Typeface? {
        var typeface = TYPEFACE[fontName]
        if (typeface == null) {
            typeface = Typeface.createFromAsset(
                context.assets, "font/"
                        + fontName
            )
            TYPEFACE[fontName] = typeface
        }
        return typeface
    }

    enum class AnimationType {
        SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN, FADE_IN, SLIDE_IN_SLIDE_OUT, FADE_OUT
    }
}