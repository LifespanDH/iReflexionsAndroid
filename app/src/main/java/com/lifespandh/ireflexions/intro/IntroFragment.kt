package com.lifespandh.ireflexions.intro

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_intro.*

class IntroFragment : BaseFragment() {

    private val introVideoUrl = "" // Add the url for the video here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupViews()
        setListeners()
    }

    private fun setupViews() {
        txt_loading.visibility = View.VISIBLE

        videoView.apply {
            setVideoURI(Uri.parse(introVideoUrl))
            requestFocus()
            setMediaController(null)
        }
    }

    private fun setListeners() {
        videoView.setOnPreparedListener {
            txt_loading.visibility = View.INVISIBLE
            videoView.start()
        }

        videoView.setOnCompletionListener {
            txt_next.visibility = View.VISIBLE
        }

        videoView.setOnErrorListener { _, _, _ ->
            Toast.makeText(requireContext(), "error loading video", Toast.LENGTH_LONG).show()
            false
        }

//        txt_skip.setOnClickListener {
//            findNavController().navigate(R.id.action_introFragment_to_quizFragment)
//        }
//
//        txt_next.setOnClickListener {
//            findNavController().navigate(R.id.action_introFragment_to_quizFragment)
//        }
//
//        img_back.setOnClickListener {
//            findNavController().navigate(R.id.action_introFragment_to_editAccountFragment)
//        }
    }

    override fun onResume() {
        super.onResume()
//        (activity as MainActivity).drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//        (activity as MainActivity).toolbar.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        videoView.stopPlayback()
    }

    companion object {
        fun newInstance() = IntroFragment()
    }
}