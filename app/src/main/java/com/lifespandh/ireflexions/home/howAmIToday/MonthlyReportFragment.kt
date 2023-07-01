package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.howAmIToday.adapters.MonthFooterAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.MonthsAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.monthly.EmotionColorBarAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.monthly.JournalEntryListAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.monthly.PanicAttackListAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.Emotion
import com.lifespandh.ireflexions.models.howAmIToday.EmotionData
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
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import kotlinx.android.synthetic.main.fragment_monthly_report.calendarView
import kotlinx.android.synthetic.main.fragment_monthly_report.categorySpinner
import kotlinx.android.synthetic.main.fragment_monthly_report.chartViewBarMovement
import kotlinx.android.synthetic.main.fragment_monthly_report.chartViewBarSleep
import kotlinx.android.synthetic.main.fragment_monthly_report.emotionColorBarRecyclerView
import kotlinx.android.synthetic.main.fragment_monthly_report.journalListRecyclerView
import kotlinx.android.synthetic.main.fragment_monthly_report.monthTop
import kotlinx.android.synthetic.main.fragment_monthly_report.panicListRecyclerView
import java.time.LocalDate
import java.time.YearMonth

class MonthlyReportFragment : BaseFragment(), MonthsAdapter.OnDateClicked {

    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }
    private val journalEntryListAdapter by lazy { JournalEntryListAdapter(mutableListOf()) }
    private val panicAttackListAdapter by lazy { PanicAttackListAdapter(mutableListOf()) }
    private val emotionColorBarAdapter by lazy { EmotionColorBarAdapter(mutableMapOf()) }
    private val emotionsList = mutableMapOf<String, EmotionData>()

    private var mainEmotionCount = 0
    private var subEmotionsCount = 0
    private var dailyData: JsonObject = JsonObject()
    private var categories = arrayOf<String?>(EMOTIONS, SLEEP, JOURNAL_ENTRIES, PANIC_ATTACK, MOVEMENT)

    private var selectedMonth = getCurrentMonth()
    private var selectedYear = getCurrentYear()

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

        journalListRecyclerView.apply {
            adapter = journalEntryListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        panicListRecyclerView.apply {
            adapter = panicAttackListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        emotionColorBarRecyclerView.apply {
            adapter = emotionColorBarAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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

                hideViews()

                chartViewBarSleep.isVisible = category == SLEEP
                chartViewBarMovement.isVisible = category == MOVEMENT
                journalListRecyclerView.isVisible = category == JOURNAL_ENTRIES
                panicListRecyclerView.isVisible = category == PANIC_ATTACK
                emotionColorBarRecyclerView.isVisible = category == EMOTIONS
            }
        }
    }

    private fun hideViews() {
        chartViewBarSleep.makeGone()
        chartViewBarMovement.makeGone()
        journalListRecyclerView.makeGone()
        panicListRecyclerView.makeGone()
        emotionColorBarRecyclerView.makeGone()
    }

    private fun initCalendar(category: String = EMOTIONS) {
        monthTop.text = "${getCurrentMonth()} ${getCurrentYear()}"
        calendarView.dayBinder = MonthsAdapter(dailyData, category, requireContext(),this)
        calendarView.monthHeaderBinder = MonthFooterAdapter()
        val months = getStartEndCurrentMonth(TIME_DIFFERENCE)
        calendarView.setup(months.first, months.second, firstDayOfWeekFromLocale())
        calendarView.scrollToMonth(months.third)

        if (category in listOf(SLEEP, MOVEMENT))
            initChart(category ?: SLEEP)
    }

    private fun getMonthlyReports() {
        howAmITodayViewModel.getMonthlyReports(createJsonRequestBody(
            YEAR to selectedYear,
            MONTH to selectedMonth.value
        ))
    }

    private fun setObservers() {
        howAmITodayViewModel.monthlyLiveData.observeFreshly(viewLifecycleOwner) {
            dailyData = it.get("daily_data") as JsonObject
            initCalendar()
        }
    }

    private fun initChart(
        category: String
    ) {
        val length = selectedMonth.length(YearMonth.now().isLeapYear)
        val dataList = FloatArray(length)


        for (i in 0 until length) {
            val dayData = (dailyData.get("${i + 1}") as JsonObject?)
            when(category) {
                SLEEP -> {
                    dataList[i] = dayData?.get("sleep")?.asFloat ?: 0f
                }
                MOVEMENT -> {
                    dataList[i] = dayData?.get("movement")?.asFloat ?: 0f
                }
            }
        }

        val chartViewBar = if (category == SLEEP) chartViewBarSleep else chartViewBarMovement
        chartViewBar.makeVisible()

        chartViewBar.heights1 = dataList
        chartViewBar.heights2 = dataList
        chartViewBar.heights3 = dataList
        chartViewBar.heights4 = dataList

        chartViewBar.colors = intArrayOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.chart_background
            ),
            if (category == SLEEP) ContextCompat.getColor(
                requireContext(), R.color.chart_sleep
            ) else ContextCompat.getColor(
                requireContext(), R.color.chart_movement
            )
        )

        chartViewBar.xAxisLabels = arrayOf("")
        chartViewBar.xAxisShow = true
        chartViewBar.yAxisShow = true
        if (category == SLEEP) {
            chartViewBar.yAxisLabelStep = 2f
        } else {
            chartViewBar.yAxisLabels = requireContext().resources.getStringArray(R.array.movementLabels)
        }
        chartViewBar.showShadow = true
        chartViewBar.showTop = true
        chartViewBar.postInvalidate()
    }

    companion object {

    }

    override fun onDateClicked(date: LocalDate?) {
        logE("called here $date")
    }

    override fun addEmotionsToList(emotions: Set<MutableMap.MutableEntry<String, JsonElement>>?) {
        emotions?.forEach {
            val count = it.value.asJsonObject.get("count").asInt
            if (count == 0)
                return@forEach

            val color = it.value.asJsonObject.get("color").asString
            val traitSubs = it.value.asJsonObject.get("trait_sub_categories").asJsonObject.entrySet()
            val subs = mutableMapOf<String, Emotion>()
            traitSubs.forEachIndexed { index, sub ->
                val currentEmotion = subs[sub.key]
                if (currentEmotion?.count == null || currentEmotion?.count == 0)
                    return@forEachIndexed
                subs[sub.key] = Emotion((currentEmotion?.count ?: 0) + 1, currentEmotion?.color ?: sub.value.asJsonObject.get("color").asString)
                subEmotionsCount += (subs[sub.key]?.count ?: 0)
            }
            val currentEmotionData = emotionsList[it.key]
            emotionsList[it.key] = EmotionData(Emotion((currentEmotionData?.first?.count ?: 0) + count, color), subs)
            mainEmotionCount += (emotionsList[it.key]?.first?.count ?: 0)
        }
        emotionColorBarAdapter.addToList(emotionsList, mainEmotionCount)
    }

    override fun addJournalEntryToList(toMutableList: MutableList<JsonElement>?, date: LocalDate) {
        journalEntryListAdapter.addToList(toMutableList, date)
    }

    override fun addPanicAttackToList(panicAttack: JsonArray?, date: LocalDate) {
        panicAttackListAdapter.addToList(panicAttack?.toMutableList(), date)
    }
}