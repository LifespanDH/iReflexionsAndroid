package com.lifespandh.ireflexions.home.howAmIToday.adapters.monthly

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.utils.date.getDateInHumanFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date

class JournalEntryListAdapter(
    private val journalEntries: MutableList<Pair<String, String>>,
    private val listener: OnItemClicked
): BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JournalEntryViewHolder(getView(R.layout.item_journal_entry_text, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JournalEntryViewHolder)
            holder.bind(journalEntries[position])
    }

    override fun getItemCount(): Int {
        return journalEntries.size
    }

    fun addToList(toMutableList: MutableList<JsonElement>?, date: LocalDate) {
        toMutableList?.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = Date.from(date?.atStartOfDay()?.toInstant(ZoneOffset.UTC))
            journalEntries.add(Pair(getDateInHumanFormat(calendar), it.asString))
        }
        notifyDataSetChanged()
    }

    inner class JournalEntryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val journalEntryText: TextView = itemView.findViewById(R.id.journalEntryText)


        fun bind(journalEntry: Pair<String, String>) {
            journalEntryText.text = "${journalEntry.first} (${journalEntry.second.substring(0,2)})"
            journalEntryText.setOnClickListener {
                listener.onJournalItemClick(journalEntry)
            }
        }
    }

    interface OnItemClicked {
        fun onJournalItemClick(journalEntry: Pair<String, String>)
    }
}