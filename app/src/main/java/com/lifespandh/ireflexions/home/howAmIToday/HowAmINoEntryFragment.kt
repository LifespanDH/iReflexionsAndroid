package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.howAmIToday.adapters.JournalEntryAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.WeekAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.utils.date.DATE
import com.lifespandh.ireflexions.utils.date.DATE_FORMAT
import com.lifespandh.ireflexions.utils.date.getCalendarAfterBefore
import com.lifespandh.ireflexions.utils.date.getDateInFormat
import com.lifespandh.ireflexions.utils.date.getDateInFormat
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.*
import java.text.SimpleDateFormat
import java.util.*

class HowAmINoEntryFragment : BaseFragment(), WeekAdapter.OnItemClickedListener, JournalEntryAdapter.OnItemClicked {

    private var toDate = Calendar.getInstance().time
    private lateinit var weekAdapter: WeekAdapter
    private val dateBundle = Bundle()
    private lateinit var currentDate: Date
    private val journalEntryAdapter by lazy { JournalEntryAdapter(mutableListOf(), this) }
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy")
    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    private val formatDay = SimpleDateFormat("EEE", Locale.US)
    private val formatMonth = SimpleDateFormat("MMM", Locale.US)
    private val formatDate = SimpleDateFormat("dd", Locale.US)

    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }

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
        howAmITodayViewModel.dailyCheckInEntriesLiveData.observeFreshly(viewLifecycleOwner) {
            journalEntryAdapter.setList(it)
            setEntryLayout(it.isEmpty())

            loader.makeGone()
            mainLayout.makeVisible()
        }
    }

    private fun init() {
        getDailyEntries(getDateInFormat())
        setViews()
        setListeners()
        setObservers()
    }

    private fun getDailyEntries(date: String) {
        loader.makeVisible()
        mainLayout.makeGone()

        val requestBody = createJsonRequestBody(DATE to date)
        howAmITodayViewModel.getDailyEntries(requestBody)
    }

    private fun setViews(){
        dailyEntryRecyclerView.apply {
            adapter = journalEntryAdapter
            layoutManager = LinearLayoutManager(context)
        }
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
            val calendarPrevious = getCalendarAfterBefore(currentDate, -7)
            setAdapter(calendarPrevious)
        }

        arrow_next.setOnClickListener {
            val calendarNext = getCalendarAfterBefore(currentDate, 7)
            setAdapter(calendarNext)

        }
    }

    private fun setAdapter(calendar: Calendar) {
        currentDate = calendar.time

        val days = ArrayList<String>()
        val month = ArrayList<String>()
        val date = ArrayList<String>()
        val dateList = ArrayList<String>()
        val dates: MutableList<Pair<String, Triple<String, String, String>>> = mutableListOf()
        for (i in 0..6) {
            when (i) {
                0 -> {
                }
                6 -> {
                }
            }
            days.add(formatDay.format(calendar.time))
            month.add(formatMonth.format(calendar.time))
            date.add(formatDate.format(calendar.time))
            dateList.add(calendar.time.getDateInFormat())
            dates.add(calendar.time.getDateInFormat() to Triple(formatDay.format(calendar.time), formatMonth.format(calendar.time), formatDate.format(calendar.time)))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        dayView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        weekAdapter = WeekAdapter(
            dates = dates, howAmITodayViewModel = howAmITodayViewModel
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

    private fun setEntryLayout(isListEmpty: Boolean) {
        txt_entry.isInvisible = !isListEmpty
        addCircleImageView.isInvisible = !isListEmpty

        txt_noentry.isInvisible = isListEmpty
        txt_add_noentry.isInvisible = isListEmpty
        addCircleImageViewBig.isInvisible = isListEmpty
    }

    companion object {
        fun newInstance() = HowAmINoEntryFragment()
    }

    override fun onResume() {
        super.onResume()
        val cal = Calendar.getInstance()

        cal.time = toDate
        howAmITodayViewModel.selectedPosition = cal.get(Calendar.DAY_OF_WEEK) - 2

        cal.apply {
            firstDayOfWeek = Calendar.MONDAY
            this[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        }

        setAdapter(cal)
    }

    override fun onItemClick(position: Int, date: String) {
        weekAdapter.changeDataSet(position)
        this.toDate = date.getDateInFormat(DATE_FORMAT)
        getDailyEntries(date)
    }

    override fun onItemClick(dailyCheckInEntry: DailyCheckInEntry) {
        TODO("Not yet implemented")
    }
}
