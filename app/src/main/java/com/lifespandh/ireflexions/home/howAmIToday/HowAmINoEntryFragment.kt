package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.howAmIToday.adapters.WeekAdapter
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.*
import java.text.SimpleDateFormat
import java.util.*

class HowAmINoEntryFragment : BaseFragment(), WeekAdapter.OnItemClickedListener {

    private var token: String? = null
    private var toDate = Calendar.getInstance().time
    private lateinit var weekAdapter: WeekAdapter
    private val dateBundle = Bundle()
    private lateinit var currentDate: Date
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy")

    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    private val formatDay = SimpleDateFormat("EEE", Locale.US)
    private val formatMonth = SimpleDateFormat("MMM", Locale.US)
    private val formatDate = SimpleDateFormat("dd", Locale.US)

    private val homeViewModel by activityViewModels<HomeViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_how_am_i_no_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun setObservers() {
        tokenViewModel.token.observeFreshly(viewLifecycleOwner) {
            token = it
        }
    }

    private fun init() {
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        addCircleImageView.setOnClickListener {
            if (System.currentTimeMillis() > toDate.time) {
                dateBundle.putSerializable("DATE", toDate)
                findNavController().navigate(
                    R.id.action_howAmINoEntryFragment_to_howAmICreateEntryFragment,
                    dateBundle
                )
            }
        }

        weekView.setOnClickListener {
            findNavController().navigate(R.id.action_howAmINoEntryFragment_to_weeklyReportFragment2)
        }

        layout_month.setOnClickListener {
            findNavController().navigate(
                R.id.action_howAmINoEntryFragment_to_monthlyReportFragment2,
            )
        }

        addCircleImageViewBig.setOnClickListener {
            if (System.currentTimeMillis() > toDate.time) {
                dateBundle.putSerializable("DATE", toDate)
                findNavController().navigate(
                    R.id.action_howAmINoEntryFragment_to_howAmICreateEntryFragment,
                    dateBundle
                )
            }
        }

        arrow_previous.setOnClickListener {
            val calendarPrevious = Calendar.getInstance().apply {
                time = currentDate
                firstDayOfWeek = Calendar.MONDAY
                add(Calendar.DAY_OF_MONTH, -7)
                this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
            }
            setAdapter(calendarPrevious)

        }

        arrow_next.setOnClickListener {
            val calendarNext = Calendar.getInstance().apply {
                time = currentDate
                firstDayOfWeek = Calendar.MONDAY
                add(Calendar.DAY_OF_MONTH, 7)
                this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
            }
            setAdapter(calendarNext)

        }
    }

    private fun setAdapter(calendar: Calendar) {
        currentDate = calendar.time
        var firstDayString = String()
        var lastDayString = String()
        val days = ArrayList<String>()
        val month = ArrayList<String>()
        val date = ArrayList<String>()
        val dateList = ArrayList<String>()

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
            days.add(formatDay.format(calendar.time))
            month.add(formatMonth.format(calendar.time))
            date.add(formatDate.format(calendar.time))
            dateList.add(parser.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        dayView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        weekAdapter = WeekAdapter(
            days, month, date, dateList = dateList, homeViewModel = homeViewModel
        )
        dayView.adapter = weekAdapter
        weekAdapter.setOnItemClickedListener(this)
    }

    private fun setJournalAdapter(date: Date) {
//        var dailyEntries: List<DailyCheckInEntry> = emptyList()
//
//        val dailyItems = homeViewModel.dailyCheckInList.groupBy {
//            dateFormat.format(parser.parse(it.date))
//        }
//
//        if (dailyItems.containsKey(dateFormat.format(date))) {
//            dailyEntries = dailyItems[dateFormat.format(date)]!!
//        }
//
//        if (journalEntryOverview != null) {
//            journalEntryOverview.layoutManager = GridLayoutManager(context, 1)
//            journalEntryAdapter = JournalEntryAdapter(
//                requireContext(),
//                itemListDailyCheckIn = dailyEntries
//            )
//
//            journalEntryAdapter.setOnItemClickedListener(this)
//            journalEntryOverview.adapter = journalEntryAdapter
//
//            if (dailyEntries.isNotEmpty()) {
//                setEntryLayout()
//            } else {
//                setNoEntryLayout()
//            }
//        }
    }

    private fun setEntryLayout() {
        txt_entry.visibility = View.VISIBLE
        addCircleImageView.visibility = View.VISIBLE

        txt_noentry.visibility = View.INVISIBLE
        txt_add_noentry.visibility = View.INVISIBLE
        addCircleImageViewBig.visibility = View.INVISIBLE
    }

    private fun setNoEntryLayout() {
        txt_entry.visibility = View.INVISIBLE
        addCircleImageView.visibility = View.INVISIBLE

        txt_noentry.visibility = View.VISIBLE
        txt_add_noentry.visibility = View.VISIBLE
        addCircleImageViewBig.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance() = HowAmINoEntryFragment()
    }

    override fun onResume() {
        super.onResume()
        val cal = Calendar.getInstance()

        cal.time = toDate
        homeViewModel.selectedPosition = cal.get(Calendar.DAY_OF_WEEK) - 2

        cal.apply {
            firstDayOfWeek = Calendar.MONDAY
            this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        }

        setAdapter(cal)
    }

    override fun onItemClick(position: Int, toDate: Date) {
        weekAdapter.changeDataSet(position)
        this.toDate = toDate
        setJournalAdapter(toDate)
    }
}
