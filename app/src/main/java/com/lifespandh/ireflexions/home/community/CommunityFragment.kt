package com.lifespandh.ireflexions.home.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lifespandh.ireflexions.R
import kotlinx.android.synthetic.main.fragment_community.*

class CommunityFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupWebView()
    }

    private fun setupWebView() {
        communityWebView.apply {
            settings.javaScriptEnabled = true
            loadUrl("https://www.heypeers.com/")
        }
    }

    companion object {

        fun newInstance() = CommunityFragment()
    }
}