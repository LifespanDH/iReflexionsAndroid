package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isInvisible
import com.google.gson.JsonObject
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.utils.EMOTIONS
import com.lifespandh.ireflexions.utils.JOURNAL_ENTRIES
import com.lifespandh.ireflexions.utils.MOVEMENT
import com.lifespandh.ireflexions.utils.PANIC_ATTACK
import com.lifespandh.ireflexions.utils.SLEEP
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.ui.makeVisible
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class DayViewContainer(view: View, listener: MonthsAdapter.OnDateClicked?) : ViewContainer(view) {
    val textView: TextView = view.findViewById(R.id.calendarDayText)
    val sleepTime: TextView = view.findViewById(R.id.sleepTime)

    val journalImage: ImageView = view.findViewById(R.id.journalImage)
    val panicImage: ImageView = view.findViewById(R.id.panicImage)

    var date: LocalDate? = null
    init {
        view.setOnClickListener {
            listener?.onDateClicked(date)
        }
    }
}

class MonthsAdapter(
    private val dailyData: JsonObject,
    private val category: String,
    private val listener: OnDateClicked
): MonthDayBinder<DayViewContainer> {
    override fun create(view: View) = DayViewContainer(view, listener)

    override fun bind(container: DayViewContainer, data: CalendarDay) {
        container.date = data.date
        container.view.isInvisible = data.position != DayPosition.MonthDate
        container.textView.text = data.date.dayOfMonth.toString()

        val dayData = (dailyData.get("${data.date.dayOfMonth}") as JsonObject?)

        when(category) {
            EMOTIONS -> {

            }
            SLEEP -> {
                val sleep = dayData?.get("sleep")?.asInt
                sleep?.let {
                    container.sleepTime.makeVisible()
                    container.sleepTime.text = it.toString()
                }
            }
            JOURNAL_ENTRIES -> {
                val journalEntries = dayData?.get("journal_entries")?.asJsonArray
                journalEntries?.let {
                    if (it.isEmpty.not())
                        container.journalImage.makeVisible()
                }
            }
            PANIC_ATTACK -> {
                val panicAttack = dayData?.get("panic_attack")?.asJsonArray
                panicAttack?.let {
                    if (it.isEmpty.not())
                        container.panicImage.makeVisible()
                }
            }
            MOVEMENT -> {

            }
        }


    }

    interface OnDateClicked {
        fun onDateClicked(date: LocalDate?)
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