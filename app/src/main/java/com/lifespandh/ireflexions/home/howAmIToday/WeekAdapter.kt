package com.lifespandh.ireflexions.home.howAmIToday

import android.view.LayoutInflater
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
import com.lifespandh.ireflexions.models.DailyCheckInEntry
import com.lifespandh.ireflexions.utils.logs.logE
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeekAdapter(
    private var weekDays: List<String>,
    private var month: ArrayList<String>,
    private var date: ArrayList<String>,
    private var dateList: ArrayList<String>,
    private var dailyEntryMapItem: Map<String, List<DailyCheckInEntry>> = emptyMap(),
) : BaseRecyclerViewAdapter() {

    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    lateinit var listener: OnItemClickedListener
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeekViewHolder(getView(R.layout.day_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        logE("called here vbind $holder $position")
        if (holder is WeekViewHolder)
            holder.bind(dateList[position])
    }

    override fun getItemCount(): Int {
        logE("called size ${weekDays.size}")
        return weekDays.size
    }

    fun setOnItemClickedListener(listener: OnItemClickedListener) {
        this.listener = listener
    }

    inner class WeekViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val txtDay: TextView = itemView.findViewById(R.id.txt_day)
        private val txtMonth: TextView = itemView.findViewById(R.id.txt_month)
        private val txtDate: TextView = itemView.findViewById(R.id.txt_date)
        private val view: View = itemView.findViewById(R.id.view)
        private val listDailyEntries: RecyclerView = itemView.findViewById(R.id.list_daily_entries)

        fun bind(date: String) {
            val parsedDateTime = parser.parse(date).time

            if (System.currentTimeMillis() < parsedDateTime) {
                itemView.isClickable = false
            }

            if (position == selectedPosition && System.currentTimeMillis() > parsedDateTime) {
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
            val position = absoluteAdapterPosition
            if (absoluteAdapterPosition == itemCount - 1)
                view.visibility = View.INVISIBLE

            val programItem = weekDays[position]
            val month = month[position]
            val date_ = this@WeekAdapter.date[position]
            txtDay.text = programItem
            txtMonth.text = month
            txtDate.text = date_

            listDailyEntries.layoutManager = GridLayoutManager(getContext(), 3)

            if (dailyEntryMapItem.containsKey(date)) {
                val dailyEntryList = dailyEntryMapItem[date]!!
                val adapter = JournalEntryListAdapter(dailyEntryList)
                listDailyEntries.adapter = adapter
            }
        }
    }

    interface OnItemClickedListener {
        fun onItemClick(position: Int, viewHolder: RecyclerView.ViewHolder)
    }
}

class JournalEntryListAdapter(

    private val dailyCheckInItems: List<DailyCheckInEntry>
) : BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JournalEntryViewHolder(getView(R.layout.image_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JournalEntryListAdapter.JournalEntryViewHolder)
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