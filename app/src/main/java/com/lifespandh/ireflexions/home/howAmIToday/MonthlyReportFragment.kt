package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.google.gson.JsonObject
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.howAmIToday.adapters.MonthFooterAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.MonthsAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.utils.EMOTIONS
import com.lifespandh.ireflexions.utils.JOURNAL_ENTRIES
import com.lifespandh.ireflexions.utils.MOVEMENT
import com.lifespandh.ireflexions.utils.PANIC_ATTACK
import com.lifespandh.ireflexions.utils.SLEEP
import com.lifespandh.ireflexions.utils.date.MONTH
import com.lifespandh.ireflexions.utils.date.TIME_DIFFERENCE
import com.lifespandh.ireflexions.utils.date.YEAR
import com.lifespandh.ireflexions.utils.date.getCurrentMonth
import com.lifespandh.ireflexions.utils.date.getCurrentYear
import com.lifespandh.ireflexions.utils.date.getStartEndCurrentMonth
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import kotlinx.android.synthetic.main.fragment_monthly_report.calendarView
import kotlinx.android.synthetic.main.fragment_monthly_report.categorySpinner
import java.time.LocalDate

class MonthlyReportFragment : BaseFragment(), MonthsAdapter.OnDateClicked {

    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }

    private var dailyData: JsonObject = JsonObject()
    private var categories = arrayOf<String?>(EMOTIONS, SLEEP, JOURNAL_ENTRIES, PANIC_ATTACK, MOVEMENT)

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
        getMonthlyReports()
        setListeners()
        setObservers()
    }

    private fun initViews() {
        initCalendar()

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories)
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        categorySpinner.adapter = ad
    }

    private fun setListeners() {
        categorySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val category = categories[position]
                initCalendar(category ?: EMOTIONS)
            }
        }

    }

    private fun initCalendar(category: String = EMOTIONS) {
        calendarView.dayBinder = MonthsAdapter(dailyData, category, this)
        calendarView.monthHeaderBinder = MonthFooterAdapter()
        val months = getStartEndCurrentMonth(TIME_DIFFERENCE)
        calendarView.setup(months.first, months.second, firstDayOfWeekFromLocale())
        calendarView.scrollToMonth(months.third)
    }

    private fun getMonthlyReports(year: Int = getCurrentYear(), month: Int = getCurrentMonth()) {
        howAmITodayViewModel.getMonthlyReports(createJsonRequestBody(
            YEAR to year,
            MONTH to month
        ))
    }

    private fun setObservers() {
        howAmITodayViewModel.monthlyLiveData.observeFreshly(viewLifecycleOwner) {
            dailyData = it.get("daily_data") as JsonObject
            logE("Called here $dailyData")
            initCalendar()
        }
    }

    companion object {

    }

    override fun onDateClicked(date: LocalDate?) {
        logE("called here $date")
    }
}