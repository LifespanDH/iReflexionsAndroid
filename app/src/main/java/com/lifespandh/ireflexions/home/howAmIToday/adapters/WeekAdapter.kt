package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.utils.date.DATE_FORMAT
import com.lifespandh.ireflexions.utils.date.DateInfo
import com.lifespandh.ireflexions.utils.date.getDateInFormat
import kotlin.math.abs

class WeekAdapter(
    private var dates: List<DateInfo>,
    private val howAmITodayViewModel: HowAmITodayViewModel,
    private val listener: OnItemClickedListener,
    private var dailyEntryMapItem: Map<String, List<DailyCheckInEntry>> = emptyMap(),
) : BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeekViewHolder(getView(R.layout.day_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WeekViewHolder)
            holder.bind(dates[position])
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    fun setDates(dates: List<DateInfo>) {
        this.dates = dates
        notifyDataSetChanged()
    }

    inner class WeekViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val txtDay: TextView = itemView.findViewById(R.id.txt_day)
        private val txtMonth: TextView = itemView.findViewById(R.id.txt_month)
        private val txtDate: TextView = itemView.findViewById(R.id.txt_date)
        private val view: View = itemView.findViewById(R.id.view)
        private val listDailyEntries: RecyclerView = itemView.findViewById(R.id.list_daily_entries)

        fun bind(datePair: Pair<String, Triple<String, String, String>>) {
            val date = datePair.first

            val parsedDateTime = date.getDateInFormat(DATE_FORMAT)?.time

            if (parsedDateTime != null && System.currentTimeMillis() < parsedDateTime) {
                itemView.isClickable = false
            }

            if (absoluteAdapterPosition == howAmITodayViewModel.selectedPosition) {
                itemView.background =
                    ContextCompat.getDrawable(getContext(), R.drawable.week_day_round_rectangle)
            } else {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        getContext(),
                        android.R.color.transparent
                    )
                )

            }
            if (absoluteAdapterPosition == itemCount - 1)
                view.visibility = View.INVISIBLE

            val programItem = datePair.second.first
            val month = datePair.second.second
            val date_ = datePair.second.third
            txtDay.text = programItem
            txtMonth.text = month
            txtDate.text = date_

            listDailyEntries.layoutManager = GridLayoutManager(getContext(), 3)

            if (dailyEntryMapItem.containsKey(date)) {
                val dailyEntryList = dailyEntryMapItem[date]!!
                val adapter = JournalEntryListAdapter(dailyEntryList)
                listDailyEntries.adapter = adapter
            }

            itemView.setOnClickListener {
                val prev = howAmITodayViewModel.selectedPosition
                howAmITodayViewModel.selectedPosition = absoluteAdapterPosition
                if (prev != -1)
                    notifyItemChanged(prev)
                notifyItemChanged(howAmITodayViewModel.selectedPosition)

                listener.onItemClick(absoluteAdapterPosition, date)
            }
        }
    }

    interface OnItemClickedListener {
        fun onItemClick(position: Int, date: String)
    }
}

class JournalEntryListAdapter(

    private val dailyCheckInItems: List<DailyCheckInEntry>
) : BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JournalEntryViewHolder(getView(R.layout.image_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JournalEntryViewHolder)
            holder.bind(dailyCheckInItems[position])
    }

    override fun getItemCount(): Int {
        return dailyCheckInItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class JournalEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.img_item)

        fun bind(dailyCheckInItem: DailyCheckInEntry) {
            Glide.with(getContext()).load(R.drawable.journal_dot).into(imageView)
        }
    }
}