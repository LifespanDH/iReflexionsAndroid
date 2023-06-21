package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.utils.date.DateInfo
import kotlin.collections.ArrayList

class WeeklyReportAdapter (
    private var dailyEntryMap: Map<String, List<DailyCheckInEntry>>,
    private var dates: List<DateInfo>,
    private val listener: OnItemClickedListener
        ): BaseRecyclerViewAdapter () {

    private val dailyBundle = Bundle()

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

    fun setDates(dates: List<DateInfo>) {
        this.dates = dates
    }

    inner class WeeklyReportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var txtToday: TextView = itemView.findViewById(R.id.txt_today)
        var txtNoEntry: TextView = itemView.findViewById(R.id.txt_noentry)
        var txtAddNoEntry: TextView = itemView.findViewById(R.id.txt_add_noentry)
        var addCircleImage: ImageView = itemView.findViewById(R.id.addCircleImage)
        var weeklyEntryOverview: RecyclerView = itemView.findViewById(R.id.weeklyEntryOverview)

        fun bind(dateInfo: DateInfo) {
            val journalItem = dateInfo.first

            txtToday.text = journalItem

            val date = dateInfo.second.first
            if (dailyEntryMap.containsKey(date)) {
                weeklyEntryOverview.layoutManager = GridLayoutManager(getContext(), 1)

//                val dailyEntryList = dailyEntryMap[date]!!

                txtAddNoEntry.visibility = View.INVISIBLE
                addCircleImage.visibility = View.INVISIBLE
                txtNoEntry.visibility = View.INVISIBLE

//                val adapter = JournalEntryAdapter(
//                    itemListDailyCheckIn = dailyEntryList,
//                    isChangeColor = true
//                )
//                adapter.setOnItemClickedListener(this)
//                weeklyEntryOverview.adapter = adapter
            }

            addCircleImage.setOnClickListener {
//                listener.onAddEntryClicked(dates[absoluteAdapterPosition])
            }

//            if (System.currentTimeMillis() < dateListOrigin[position].time) {
//                addCircleImage.isClickable = false
//            }
        }
    }

    interface OnItemClickedListener {
        fun onItemClick(position: Int, viewHolder: RecyclerView.ViewHolder)
        fun onAddEntryClicked(date: String)
    }
}