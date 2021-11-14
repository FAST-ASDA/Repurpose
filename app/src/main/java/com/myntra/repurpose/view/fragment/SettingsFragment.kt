
package com.myntra.repurpose.view.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.myntra.repurpose.R
import com.myntra.repurpose.util.PreferenceHelper
import com.myntra.repurpose.util.Utils
import com.myntra.repurpose.util.Utils.AnimationType
import com.myntra.repurpose.util.Utils.switchContent
import com.myntra.repurpose.view.activities.ECartHomeActivity

// TODO: Auto-generated Javadoc
/**
 * Fragment that appears in the "content_frame", shows a animal.
 */
class SettingsFragment
/**
 * Instantiates a new settings fragment.
 */
    : Fragment() {
    private var submitLog: TextView? = null
    private var mToolbar: Toolbar? = null

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(
            R.layout.frag_settings, container,
            false
        )
        requireActivity().title = "About App"
        mToolbar = rootView.findViewById<View>(R.id.htab_toolbar) as Toolbar
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
        mToolbar!!.setTitleTextColor(Color.WHITE)
        submitLog = rootView.findViewById<View>(R.id.submit_log_txt) as TextView
        if (PreferenceHelper.prefernceHelperInstace.getBoolean(
                activity, PreferenceHelper.SUBMIT_LOGS, true
            )
        ) {
            submitLog!!.text = "Disable"
        } else {
            submitLog!!.text = "Enable"
        }
        rootView.findViewById<View>(R.id.submit_log).setOnClickListener {
            if (PreferenceHelper.prefernceHelperInstace
                    .getBoolean(
                        activity,
                        PreferenceHelper.SUBMIT_LOGS, true
                    )
            ) {
                PreferenceHelper
                    .prefernceHelperInstace
                    .setBoolean(
                        activity,
                        PreferenceHelper.SUBMIT_LOGS, false
                    )
                submitLog!!.text = "Disable"
            } else {
                PreferenceHelper.prefernceHelperInstace
                    .setBoolean(
                        activity,
                        PreferenceHelper.SUBMIT_LOGS, true
                    )
                submitLog!!.text = "Enable"
            }
        }
        rootView.isFocusableInTouchMode = true
        rootView.requestFocus()
        rootView.setOnKeyListener { v, keyCode, event ->
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
        rootView.findViewById<View>(R.id.picasso).setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/square/picasso")
            )
            startActivity(browserIntent)
        }
        rootView.findViewById<View>(R.id.acra).setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/ACRA/acra")
            )
            startActivity(browserIntent)
        }
        rootView.findViewById<View>(R.id.pull_zoom_view).setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/Frank-Zhu/PullZoomView")
            )
            startActivity(browserIntent)
        }
        rootView.findViewById<View>(R.id.list_buddies).setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/jpardogo/ListBuddies")
            )
            startActivity(browserIntent)
        }
        rootView.findViewById<View>(R.id.list_jazzy).setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/twotoasters/JazzyListView")
            )
            startActivity(browserIntent)
        }
        rootView.findViewById<View>(R.id.email_dev).setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SEND
            )
            emailIntent.type = "text/plain"
            emailIntent
                .putExtra(
                    Intent.EXTRA_EMAIL, arrayOf("serveroverloadofficial@gmail.com")
                )
            emailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Hello There"
            )
            emailIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Add Message here"
            )
            emailIntent.type = "message/rfc822"
            try {
                startActivity(
                    Intent.createChooser(
                        emailIntent,
                        "Send email using..."
                    )
                )
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    activity,
                    "No email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return rootView
    }

    companion object {
        fun newInstance(): Fragment {
            // TODO Auto-generated method stub
            return SettingsFragment()
        }
    }
}