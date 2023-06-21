package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isInvisible
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.lifespandh.ireflexions.R
import java.time.format.TextStyle
import java.util.Locale

class DayViewContainer(view: View, listener: MonthsAdapter.OnDateClicked?) : ViewContainer(view) {
    val textView: TextView = view.findViewById(R.id.calendarDayText)

    init {
        view.setOnClickListener {
            listener?.onDateClicked("hello ")
        }
    }
}

class MonthsAdapter(
    private val listener: OnDateClicked
): MonthDayBinder<DayViewContainer> {
    override fun create(view: View) = DayViewContainer(view, listener)

    override fun bind(container: DayViewContainer, data: CalendarDay) {
        container.view.isInvisible = data.position != DayPosition.MonthDate
        container.textView.text = data.date.dayOfMonth.toString()
    }

    interface OnDateClicked {
        fun onDateClicked(date: String)
    }
}

class MonthDayViewContainer(view: View): ViewContainer(view) {
    val monthView: ViewGroup = view.findViewById(R.id.titlesContainer)
}

class MonthFooterAdapter: MonthHeaderFooterBinder<MonthDayViewContainer> {
    private val daysOfWeek = daysOfWeek()

    override fun bind(container: MonthDayViewContainer, data: CalendarMonth) {
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

    override fun create(view: View): MonthDayViewContainer {
        return MonthDayViewContainer(view)
    }
}