package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.utils.date.DateInfo
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible

class WeeklyReportAdapter (
    private var dailyEntryMap: Map<String, List<DailyCheckInEntry>>,
    private var dates: List<DateInfo>,
    private val listener: OnItemClickedListener
        ): BaseRecyclerViewAdapter () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeeklyReportViewHolder(getView(R.layout.item_weekly_report, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeeklyReportViewHolder)
            holder.bind(dates[position])
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    fun setDailyEntryMap(weeklyReport: Map<String, List<DailyCheckInEntry>>) {
        this.dailyEntryMap = weeklyReport
        notifyDataSetChanged()
    }

    fun setDates(dates: List<DateInfo>) {
        this.dates = dates
        notifyDataSetChanged()
    }

    inner class WeeklyReportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var txtToday: TextView = itemView.findViewById(R.id.txt_today)
        private var txtNoEntry: TextView = itemView.findViewById(R.id.txt_noentry)
        private var txtAddNoEntry: TextView = itemView.findViewById(R.id.txt_add_noentry)
        private var addCircleImage: ImageView = itemView.findViewById(R.id.addCircleImage)
        private var weeklyEntryOverview: RecyclerView = itemView.findViewById(R.id.weeklyEntryOverview)

        fun bind(dateInfo: DateInfo) {
            val parsedDate = dateInfo.first

            txtToday.text = dateInfo.second

            val dailyEntryList = dailyEntryMap.get(parsedDate)
            val dailyEntriesPresent = !dailyEntryList.isNullOrEmpty()

            if (dailyEntriesPresent) {
                weeklyEntryOverview.layoutManager = GridLayoutManager(getContext(), 1)
                val adapter = JournalEntryAdapter(
                    itemList = dailyEntryList as MutableList<DailyCheckInEntry>,
                    object : JournalEntryAdapter.OnItemClicked {
                        override fun onItemClick(dailyCheckInEntry: DailyCheckInEntry) {

                        }

                    }
                )
                weeklyEntryOverview.adapter = adapter
            }

            weeklyEntryOverview.isVisible = dailyEntriesPresent
            txtAddNoEntry.isVisible = !dailyEntriesPresent
            addCircleImage.isVisible = !dailyEntriesPresent
            txtNoEntry.isVisible = !dailyEntriesPresent

            addCircleImage.setOnClickListener {
                //listener.onAddEntryClicked(dates[absoluteAdapterPosition].first)
            }
        }
    }

    interface OnItemClickedListener {
        fun onItemClick(position: Int, viewHolder: RecyclerView.ViewHolder)
        fun onAddEntryClicked(date: String)
    }
}