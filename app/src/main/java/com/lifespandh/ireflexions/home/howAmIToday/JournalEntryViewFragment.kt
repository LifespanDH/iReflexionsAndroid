package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.home.care.CareCenterExerciseFragmentArgs
import kotlinx.android.synthetic.main.fragment_journal_entry_view.dateText
import kotlinx.android.synthetic.main.fragment_journal_entry_view.journalEntryText

class JournalEntryViewFragment: BaseDialogFragment() {

    private val args: JournalEntryViewFragmentArgs by navArgs()
    private var date: String? = null
    private var journalEntry: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_journal_entry_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getBundleValues()
        setViews()
    }

    private fun getBundleValues() {
        date = args.date
        journalEntry = args.entry
    }

    private fun setViews() {
        dateText.text = date
        journalEntryText.text = journalEntry
    }
}