
package com.myntra.repurpose.view.fragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.myntra.repurpose.R
import com.myntra.repurpose.model.entities.Product
import com.myntra.repurpose.util.Utils
import com.myntra.repurpose.util.Utils.AnimationType
import com.myntra.repurpose.util.Utils.switchContent
import com.myntra.repurpose.view.activities.ECartHomeActivity
import java.util.*

class SearchProductFragment : Fragment() {
    private val REQ_CODE_SPEECH_INPUT = 100
    var searchProductList = ArrayList<Product>()
    var searchInProgress = false
    private var heading: TextView? = null
    private var btnSpeak: ImageButton? = null
    private var serchInput: EditText? = null
    private var serachListView: ListView? = null
    /** The search adapter.  */ // private SearchListArrayAdapter searchAdapter;
    /**
     * The root view.
     */
    private var rootView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(
            R.layout.frag_search_product,
            container, false
        )
        btnSpeak = rootView!!.findViewById<View>(R.id.btnSpeak) as ImageButton
        heading = rootView!!.findViewById<View>(R.id.txtSpeech_heading) as TextView
        serchInput = rootView!!.findViewById<View>(R.id.edt_search_input) as EditText
        serchInput!!.isSelected = true
        serachListView = rootView!!
            .findViewById<View>(R.id.search_list_view) as ListView
        serachListView!!.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(activity, "Selected$position", Toast.LENGTH_SHORT)
                    .show()
            }
        serchInput!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                inputString: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
                heading!!.text = ("Showing results for "
                        + inputString.toString().lowercase(Locale.getDefault()))
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
                heading!!.text = "Search Products"
            }

            override fun afterTextChanged(arg0: Editable) {}
        })
        btnSpeak!!.setOnClickListener { promptSpeechInput() }
        rootView!!.isFocusableInTouchMode = true
        rootView!!.requestFocus()
        rootView!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP
                && keyCode == KeyEvent.KEYCODE_BACK
            ) {
                switchContent(
                    R.id.frag_container,
                    Utils.HOME_FRAGMENT,
                    (context as ECartHomeActivity?)!!,
                    AnimationType.SLIDE_DOWN
                )
            }
            true
        })
        return rootView
    }

    /**
     * Showing google speech input dialog.
     */
    private fun promptSpeechInput() {
        searchInProgress = true
        searchProductList.clear()
        heading!!.text = "Search Products"
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What do you wish for")
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                activity,
                "Voice search not supported by your device ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Receiving speech input.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        searchInProgress = false
        if (resultCode == Activity.RESULT_OK && null != data) {
            when (requestCode) {
                REQ_CODE_SPEECH_INPUT -> {
                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    heading!!.text = "Showing Results for " + result!![0]
                }
                REQ_SCAN_RESULT -> {
                }
            }
        }
    }

    companion object {
        private const val REQ_SCAN_RESULT = 200
        fun newInstance(): Fragment {
            // TODO Auto-generated method stub
            return SearchProductFragment()
        }
    }
}