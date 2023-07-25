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
import com.lifespandh.ireflexions.home.howAmIToday.adapters.WeekAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.WeeklyReportAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.utils.date.DATE_FORMAT
import com.lifespandh.ireflexions.utils.date.DATE_TIME_LONG_FORMAT
import com.lifespandh.ireflexions.utils.date.DateInfo
import com.lifespandh.ireflexions.utils.date.WEEK_START_DATE
import com.lifespandh.ireflexions.utils.date.changeDateTimeFormat
import com.lifespandh.ireflexions.utils.date.getCalendarAfterBefore
import com.lifespandh.ireflexions.utils.date.getDateInFormat
import com.lifespandh.ireflexions.utils.date.getWeekDates
import com.lifespandh.ireflexions.utils.date.toDate
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import kotlinx.android.synthetic.main.fragment_weekly_report.*
import kotlinx.android.synthetic.main.item_weekly_report.weeklyEntryOverview
import java.text.SimpleDateFormat
import java.util.*

class WeeklyReportFragment : BaseFragment(),
    WeeklyReportAdapter.OnItemClickedListener,
    WeekAdapter.OnItemClickedListener {

    private lateinit var currentDate: Date

    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }
    private val weekAdapter by lazy { WeekAdapter(listOf(), howAmITodayViewModel, this) }
    private val weeklyReportAdapter by lazy { WeeklyReportAdapter(mapOf(), listOf(), this) }

    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

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
        setViews()
        setAdapters()
        getWeeklyEntries()
        setListeners()
        setObservers()
    }

    private fun setViews() {
        dayView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = weekAdapter
        }

        weekEntryOverview.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = weeklyReportAdapter
        }
    }

    private fun setListeners() {
        arrow_previous.setOnClickListener {
            val calendarPrevious = getCalendarAfterBefore(currentDate, -7)
            setAdapters(calendarPrevious)
            getWeeklyEntries()
        }

        arrow_next.setOnClickListener {
            val calendarNext = getCalendarAfterBefore(currentDate, 7)
            setAdapters(calendarNext)
            getWeeklyEntries()
        }

    }

    private fun setObservers() {
        howAmITodayViewModel.weeklyReportLiveData.observeFreshly(viewLifecycleOwner) {
            loader.makeGone()
            weekEntryOverview.makeVisible()

            weeklyReportAdapter.setDailyEntryMap(it.associate { it.date to it.dailyEntries })
        }
    }

    private fun setAdapters(cal: Calendar? = null) {
        val calendar_ = cal ?: Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        }
        currentDate = calendar_.time
        val dates = getWeekDates(calendar_)
        setWeekAdapter(dates)
        setWeeklyReportAdapter(dates)
    }

    private fun getWeeklyEntries() {
        loader.makeVisible()
        weekEntryOverview.makeGone()

        val requestBody = createJsonRequestBody(WEEK_START_DATE to getDateInFormat(currentDate.time))
        howAmITodayViewModel.getWeeklyEntries(requestBody)
    }

    private fun setWeekAdapter(dates: MutableList<DateInfo>) {
        weekAdapter.setDates(dates)
    }

    private fun setWeeklyReportAdapter(dates: MutableList<DateInfo>) {
        weeklyReportAdapter.setDates(dates)
    }

    override fun onItemClick(position: Int, date: String) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(position: Int, viewHolder: RecyclerView.ViewHolder) {
        TODO("Not yet implemented")
    }

    override fun onAddEntryClicked(date: String) {
        val parsedDate = date.changeDateTimeFormat(DATE_FORMAT, DATE_TIME_LONG_FORMAT).getDateInFormat()
        val action = WeeklyReportFragmentDirections.actionWeeklyReportFragmentToHowAmICreateEntryFragment(parsedDate)
        findNavController().navigate(action)
    }
}