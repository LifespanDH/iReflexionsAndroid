package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.howAmIToday.adapters.WeekAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.WeeklyReportAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.utils.date.getWeekDates
import kotlinx.android.synthetic.main.fragment_weekly_report.*
import java.text.SimpleDateFormat
import java.util.*

class WeeklyReportFragment : BaseFragment(),
    WeeklyReportAdapter.OnItemClickedListener,
    WeekAdapter.OnItemClickedListener {

    private lateinit var currentDate: Date

    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }
    private val weekAdapter by lazy { WeekAdapter(listOf(), howAmITodayViewModel, this) }

    private val dateBundle = Bundle()

    private lateinit var weeklyReportAdapter: WeeklyReportAdapter

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy")
    private var date = Calendar.getInstance().time
    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    private val formatDay = SimpleDateFormat("EEE", Locale.US)
    private val formatMonth = SimpleDateFormat("MMM", Locale.US)
    private val formatDate = SimpleDateFormat("dd", Locale.US)
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
        calendar.apply {
            firstDayOfWeek = Calendar.MONDAY
            this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        }
        setViews()
        setWeeklyReportAdapter()
        setAdapters(calendar)
        setListeners()
        setObservers()
    }

    private fun setViews() {
        dayView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = weekAdapter
        }
    }

    private fun setListeners(){
        arrow_previous.setOnClickListener {
            val calendarPrevious = Calendar.getInstance().apply {
                time = currentDate
                firstDayOfWeek = Calendar.MONDAY
                add(Calendar.DAY_OF_MONTH, -7)
                this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
            }

            setWeeklyReportAdapter()
            setAdapters(calendarPrevious)

        }

        arrow_next.setOnClickListener {
            val calendarNext = Calendar.getInstance().apply {
                time = currentDate
                firstDayOfWeek = Calendar.MONDAY
                add(Calendar.DAY_OF_MONTH, 7)
                this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
            }

            setWeeklyReportAdapter()
            setAdapters(calendarNext)
        }

    }

    private fun setObservers(){

    }

    private fun setWeeklyReportAdapter() {
        val dateList = ArrayList<String>()
        val datesOrigin = ArrayList<Date>()
        val dayList = ArrayList<String>()
        val dates = ArrayList<String>()
        val items: Map<String, List<DailyCheckInEntry>> = emptyMap()

        weekEntryOverview.layoutManager = GridLayoutManager(context, 1)
//        weeklyReportAdapter = WeeklyReportAdapter(
//            items, , this
//        )
        weekEntryOverview.adapter = weeklyReportAdapter
    }

    private fun setAdapters(calendar: Calendar) {
        setWeekAdapter(calendar)
        currentDate = calendar.time

        var firstDayString = String()
        var lastDayString = String()
//        val days = ArrayList<String>()
//        val month = ArrayList<String>()
        val date = ArrayList<String>()
//        val dateList = ArrayList<String>()
        val weekDateList = ArrayList<String>()
        val weekDateListOrigin = ArrayList<Date>()

        for (i in 0..6) {
            when (i) {
                0 -> {
                    val fDay = dateFormat.parse(dateFormat.format(calendar.time))
                    firstDayString = parser.format(fDay)
                }
                6 -> {
                    lastDayString = parser.format(calendar.time)
                }
            }

//            days.add(formatDay.format(calendar.time))
//            month.add(formatMonth.format(calendar.time))
//            date.add(formatDate.format(calendar.time))
//            val dayNumberSuffix = getDayNumberSuffix(calendar.get(Calendar.DAY_OF_MONTH))
//            val formatter = SimpleDateFormat("EEEE, MMMM dd'$dayNumberSuffix', y", Locale.US)
//            weekDateList.add(formatter.format(calendar.time))
//            weekDateListOrigin.add(calendar.time)
//            dateList.add(parser.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

//        weeklyReportAdapter.dateList = weekDateList
//        weeklyReportAdapter.dateListOrigin = weekDateListOrigin
//        weeklyReportAdapter.dayList = date
//        weeklyReportAdapter.dates = dateList
        weeklyReportAdapter.notifyDataSetChanged()



    }

    private fun setWeekAdapter(calendar: Calendar) {
        val dates = getWeekDates(calendar)
        weekAdapter.setDates(dates)
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