package com.lifespandh.ireflexions.home.care

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_text_crisis_lines.*

class TextCrisisLinesFragment : BaseDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_crisis_lines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setListeners()
    }

    private fun setListeners() {

        close_dialog_textView.setOnClickListener {
            dismiss()
        }

        val websiteString = getString(R.string.website)

        crisis_text_line1_website.apply {
            setHtmlToView(
                this,
                "<u><a style='text-decoration:underline'>$websiteString</a></u>",
                "https://www.crisistextline.org/"
            )
        }

        crisis_text_line2_website.apply {
            setHtmlToView(
                this,
            "<u><a style='text-decoration:underline'>$websiteString</a></u>",
            "https://oregonyouthline.org/"
            )
        }

        crisis_text_line3_website.apply {
            setHtmlToView(
                this,
            "<u><a style='text-decoration:underline' >$websiteString</a></u>",
            "https://www.thetrevorproject.org/"
            )
        }

        crisis_text_line1_phone.setOnClickListener {
            val homeText = getString(R.string.home_message_text_crisis_line)
            openMessageAppWithPhoneNumber("741741", homeText)
        }

        crisis_text_line2_phone.setOnClickListener {
            val teen2TeenText = getString(R.string.teen2teen_message_text_crisis_line)
            openMessageAppWithPhoneNumber("839863", teen2TeenText)
        }

        crisis_text_line3_phone.setOnClickListener {
            val teen2TeenText = getString(R.string.teen2teen_message_text_crisis_line)
            openMessageAppWithPhoneNumber("839863", teen2TeenText)
        }
    }

    private fun setHtmlToView(view: TextView, html: String, url: String) {

        view.movementMethod = LinkMovementMethod.getInstance();
        view.text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        view.isFocusable = true

        view.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            )
        }

    }

    private fun openMessageAppWithPhoneNumber(phoneNumber: String, messageText:String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = getMessageUri(phoneNumber)
            putExtra("sms_body", messageText)
        }
        startActivity(intent)
    }

    fun getMessageUri(phoneNumber: String): Uri {
        val normalized = PhoneNumberUtils.normalizeNumber(phoneNumber)
        return Uri.parse("smsto: $normalized")
    }

}