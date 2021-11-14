package com.myntra.repurpose.view.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.myntra.repurpose.R
import com.myntra.repurpose.util.Utils
import com.myntra.repurpose.util.Utils.AnimationType
import com.myntra.repurpose.util.Utils.switchContent
import com.myntra.repurpose.view.activities.ECartHomeActivity

class ContactUsFragment : Fragment() {
    private var mToolbar: Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
            R.layout.frag_about, container,
            false
        )
        requireActivity().title = "Contact Us"
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
        rootView.findViewById<View>(R.id.locations).setOnClickListener { }
        rootView.findViewById<View>(R.id.contact_num).setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + "8888813275")
            startActivity(callIntent)
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
        rootView.findViewById<View>(R.id.site_dev).setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://limerse.com/")
            )
            startActivity(browserIntent)
        }
        rootView.findViewById<View>(R.id.email).setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SEND
            )
            emailIntent.type = "text/plain"
            emailIntent
                .putExtra(
                    Intent.EXTRA_EMAIL, arrayOf("tiwariakshat03@gmail.com")
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
}