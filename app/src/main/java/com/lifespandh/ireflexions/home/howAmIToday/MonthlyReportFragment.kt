package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_monthly_report.calendarView
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class MonthlyReportFragment : BaseFragment() {

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
        }
        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                if (data.position == DayPosition.MonthDate) {

                }
                container.view.isInvisible = data.position != DayPosition.MonthDate
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }

        val daysOfWeek = daysOfWeek()

        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarMonth) {
                if (container.monthView.tag == null) {
                    container.monthView.tag = data.yearMonth
                    container.monthView.children.map { it as TextView }
                        .forEachIndexed { index, textView ->
                            val dayOfWeek = daysOfWeek[index]
                            val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                            textView.text = title
                        }
                }
            }

            override fun create(view: View): DayViewContainer {
                return DayViewContainer(view)
            }
        }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)
    }

    companion object {

    }
}