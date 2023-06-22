package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.ViewContainer
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.howAmIToday.adapters.MonthFooterAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.MonthsAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.utils.date.TIME_DIFFERENCE
import com.lifespandh.ireflexions.utils.date.getCurrentMonth
import com.lifespandh.ireflexions.utils.date.getStartEndCurrentMonth
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_monthly_report.calendarView
import java.time.LocalDate
import java.time.YearMonth

class MonthlyReportFragment : BaseFragment(), MonthsAdapter.OnDateClicked {

    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_monthly_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initViews()
    }

    private fun initViews() {
        initCalendar()
        getMonthlyReports()
    }

    private fun initCalendar() {
        calendarView.dayBinder = MonthsAdapter(this)
        calendarView.monthHeaderBinder = MonthFooterAdapter()
        val months = getStartEndCurrentMonth(TIME_DIFFERENCE)
        calendarView.setup(months.first, months.second, firstDayOfWeekFromLocale())
        calendarView.scrollToMonth(months.third)
    }

    private fun getMonthlyReports(month: Int = getCurrentMonth()) {
        howAmITodayViewModel.getMonthlyReports(month)
    }

    companion object {

    }

    override fun onDateClicked(date: LocalDate?) {
        logE("called here $date")
    }
}