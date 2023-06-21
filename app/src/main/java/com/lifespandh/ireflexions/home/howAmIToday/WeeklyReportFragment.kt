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
import com.lifespandh.ireflexions.utils.date.DateInfo
import com.lifespandh.ireflexions.utils.date.getCalendarAfterBefore
import com.lifespandh.ireflexions.utils.date.getWeekDates
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_weekly_report.*
import java.text.SimpleDateFormat
import java.util.*

class WeeklyReportFragment : BaseFragment(),
    WeeklyReportAdapter.OnItemClickedListener,
    WeekAdapter.OnItemClickedListener {

    private lateinit var currentDate: Date

    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }
    private val weekAdapter by lazy { WeekAdapter(listOf(), howAmITodayViewModel, this) }
    private val weeklyReportAdapter by lazy { WeeklyReportAdapter(mapOf(), listOf(), this) }

    private val dateBundle = Bundle()

//    private lateinit var weeklyReportAdapter: WeeklyReportAdapter

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy")
    private var date = Calendar.getInstance().time
    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    private val calendar = Calendar.getInstance()


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
        }

        arrow_next.setOnClickListener {
            val calendarNext = getCalendarAfterBefore(currentDate, 7)
            setAdapters(calendarNext)
        }

    }

    private fun setObservers() {

    }

    private fun setAdapters(cal: Calendar? = null) {
        val calendar_ = cal ?: calendar.apply {
            firstDayOfWeek = Calendar.MONDAY
            this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        }
        currentDate = calendar_.time
        val dates = getWeekDates(calendar_)
        setWeekAdapter(dates)
        setWeeklyReportAdapter(dates)
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
        val parsedDate = parser.parse(date)
        val action = WeeklyReportFragmentDirections.actionWeeklyReportFragmentToHowAmICreateEntryFragment(parsedDate)
        findNavController().navigate(action)
    }
}