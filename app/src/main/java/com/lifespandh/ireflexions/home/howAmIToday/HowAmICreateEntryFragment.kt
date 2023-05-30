package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lifespandh.ireflexions.R

class HowAmICreateEntryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_how_am_i_create_entry, container, false)
    }

    companion object {
        fun newInstance() = HowAmICreateEntryFragment()
    }
}