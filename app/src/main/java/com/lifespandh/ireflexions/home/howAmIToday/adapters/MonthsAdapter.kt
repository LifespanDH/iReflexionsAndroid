package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonElement
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
    val movementImage: ImageView = view.findViewById(R.id.movementImage)

    val emotionsLayout: RecyclerView = view.findViewById(R.id.emotionsLayout)

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
    private val context: Context,
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
                val emotions = dayData?.get("emotions")?.asJsonObject?.entrySet()
                val totalCount = dayData?.get("emotions_count")?.asInt
                if (totalCount == 0 || totalCount == null)
                    return
                emotions?.let {
                    container.emotionsLayout.apply {
                        adapter = EmotionBarAdapter(it.toMutableList(), totalCount!!)
                        layoutManager = LinearLayoutManager(context)
                        isVisible = true
                    }
                }
                listener.addEmotionsToList(emotions)
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
                listener.addJournalEntryToList(journalEntries?.toMutableList(), data.date)
            }
            PANIC_ATTACK -> {
                val panicAttack = dayData?.get("panic_attack")?.asJsonArray
                panicAttack?.let {
                    if (it.isEmpty.not())
                        container.panicImage.makeVisible()
                }
                listener.addPanicAttackToList(panicAttack, data.date)
            }
            MOVEMENT -> {
                val movement = dayData?.get("movement")?.asInt
                movement?.let {
                    val image = when(movement) {
                        in 0..2 -> {
                            R.drawable.move_slider_low
                        }
                        3 -> R.drawable.move_slider_mid
                        else -> R.drawable.move_slider_high
                    }
                    container.movementImage.setImageDrawable(ContextCompat.getDrawable(context, image))
                    container.movementImage.makeVisible()

                }
            }
        }


    }

    interface OnDateClicked {
        fun onDateClicked(date: LocalDate?)
        fun addEmotionsToList(emotions: Set<MutableMap.MutableEntry<String, JsonElement>>?)
        fun addJournalEntryToList(toMutableList: MutableList<JsonElement>?, date: LocalDate)
        fun addPanicAttackToList(panicAttack: JsonArray?, date: LocalDate)
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