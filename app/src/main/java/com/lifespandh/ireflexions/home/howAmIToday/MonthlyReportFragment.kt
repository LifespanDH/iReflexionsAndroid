package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.ViewContainer
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.howAmIToday.adapters.MonthFooterAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.MonthsAdapter
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_monthly_report.calendarView
import java.time.LocalDate
import java.time.YearMonth

class MonthlyReportFragment : BaseFragment(), MonthsAdapter.OnDateClicked {

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
        class DayViewContainer(view: View) : ViewContainer(view) {
            val textView = view.findViewById<TextView>(R.id.calendarDayText)
            val monthView = view.findViewById<ViewGroup>(R.id.titlesContainer)

            init {
                view.setOnClickListener {

                }
            }
        }

        calendarView.dayBinder = MonthsAdapter(this)

        calendarView.monthHeaderBinder = MonthFooterAdapter()

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)
    }

    companion object {

    }

    override fun onDateClicked(date: LocalDate?) {
        logE("called here $date")
    }
}