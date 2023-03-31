package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.howAmIToday.adapters.WeekAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.WeeklyReportAdapter
import com.lifespandh.ireflexions.models.DailyCheckInEntry
import kotlinx.android.synthetic.main.fragment_weekly_report.*
import java.text.SimpleDateFormat
import java.util.*

class WeeklyReportFragment : BaseFragment(),
    WeeklyReportAdapter.OnItemClickedListener,
    WeekAdapter.OnItemClickedListener {

    private lateinit var adapter: WeekAdapter
    private lateinit var currentDate: Date

    private val dateBundle = Bundle()

    private lateinit var weeklyReportAdapter: WeeklyReportAdapter

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy")
    private var date = Calendar.getInstance().time
    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    private val formatDay = SimpleDateFormat("EEE", Locale.US)
    private val formatMonth = SimpleDateFormat("MMM", Locale.US)
    private val formatDate = SimpleDateFormat("dd", Locale.US)
    private val calendar = Calendar.getInstance()

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_weekly_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    companion object {
        fun newInstance() = WeeklyReportFragment()
    }

    private fun init() {
        calendar.apply {
            firstDayOfWeek = Calendar.MONDAY
            this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        }
        setWeeklyReportAdapter()
        setAdapter(calendar)
        setListeners()
        setObservers()
    }

    private fun setListeners(){
        arrow_previous.setOnClickListener {
            val calendarPrevious = Calendar.getInstance().apply {
                time = currentDate
                firstDayOfWeek = Calendar.MONDAY
                add(Calendar.DAY_OF_MONTH, -7)
                this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
            }

            setWeeklyReportAdapter()
            setAdapter(calendarPrevious)

        }

        arrow_next.setOnClickListener {
            val calendarNext = Calendar.getInstance().apply {
                time = currentDate
                firstDayOfWeek = Calendar.MONDAY
                add(Calendar.DAY_OF_MONTH, 7)
                this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
            }

            setWeeklyReportAdapter()
            setAdapter(calendarNext)
        }

    }

    private fun setObservers(){

    }

    fun setWeeklyReportAdapter() {
        val dateList = ArrayList<String>()
        val datesOrigin = ArrayList<Date>()
        val dayList = ArrayList<String>()
        val dates = ArrayList<String>()
        val items: Map<String, List<DailyCheckInEntry>> = emptyMap()

        weekEntryOverview.layoutManager = GridLayoutManager(context, 1)
        weeklyReportAdapter = WeeklyReportAdapter(
            items, dateList, datesOrigin, dayList, dates, findNavController()
        )
        weeklyReportAdapter.setOnItemClickedListener(this)
        weekEntryOverview.adapter = weeklyReportAdapter
    }

    private fun setAdapter(calendar: Calendar) {

        currentDate = calendar.time

        var firstDayString = String()
        var lastDayString = String()
        val days = ArrayList<String>()
        val month = ArrayList<String>()
        val date = ArrayList<String>()
        val dateList = ArrayList<String>()
        val weekDateList = ArrayList<String>()
        val weekDateListOrigin = ArrayList<Date>()

        for (i in 0..6) {
            when (i) {
                0 -> {
                    val fDay = dateFormat.parse(dateFormat.format(calendar.time))
                    firstDayString = parser.format(fDay)
                }
                6 -> {
                    lastDayString = parser.format(calendar.time)
                }
            }

            days.add(formatDay.format(calendar.time))
            month.add(formatMonth.format(calendar.time))
            date.add(formatDate.format(calendar.time))
            val dayNumberSuffix = getDayNumberSuffix(calendar.get(Calendar.DAY_OF_MONTH))
            val formatter = SimpleDateFormat("EEEE, MMMM dd'$dayNumberSuffix', y", Locale.US)
            weekDateList.add(formatter.format(calendar.time))
            weekDateListOrigin.add(calendar.time)
            dateList.add(parser.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        weeklyReportAdapter.dateList = weekDateList
        weeklyReportAdapter.dateListOrigin = weekDateListOrigin
        weeklyReportAdapter.dayList = date
        weeklyReportAdapter.dates = dateList
        weeklyReportAdapter.notifyDataSetChanged()

        dayView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = WeekAdapter(
            days, month, date, dateList = dateList, homeViewModel = homeViewModel
        )

        adapter.setOnItemClickedListener(this)
    }

    private fun getDayNumberSuffix(day: Int): String? {
        return if (day in 11..13) {
            "th"
        } else when (day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }

    override fun onItemClick(position: Int, toDate: Date) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(position: Int, viewHolder: RecyclerView.ViewHolder) {
        TODO("Not yet implemented")
    }
}