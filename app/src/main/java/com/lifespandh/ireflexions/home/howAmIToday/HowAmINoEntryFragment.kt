package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
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
import com.lifespandh.ireflexions.utils.date.getWeekDates
import com.lifespandh.ireflexions.utils.dialogs.DialogUtils
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.addCircleImageView
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.addCircleImageViewBig
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.arrow_next
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.arrow_previous
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.dailyEntryRecyclerView
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.dayView
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.layout_month
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.loader
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.mainLayout
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.txt_add_noentry
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.txt_entry
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.txt_noentry
import kotlinx.android.synthetic.main.fragment_how_am_i_no_entry.weekView
import java.util.Calendar
import java.util.Date

class HowAmINoEntryFragment : BaseFragment(), WeekAdapter.OnItemClickedListener, JournalEntryAdapter.OnItemClicked {

    private var toDate = Calendar.getInstance().time
    private val weekAdapter by lazy { WeekAdapter(listOf(), howAmITodayViewModel, this) }
    private val dateBundle = Bundle()
    private lateinit var currentDate: Date
    private val journalEntryAdapter by lazy { JournalEntryAdapter(mutableListOf(), this) }

    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }
    private val dialogUtils = DialogUtils()

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

        dayView.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = weekAdapter
        }
    }

    private fun setListeners() {
        addCircleImageView.setOnClickListener {
            if (System.currentTimeMillis() > toDate.time) {
                val action = HowAmINoEntryFragmentDirections.actionHowAmINoEntryFragmentToHowAmICreateEntryFragment(toDate)
                findNavController().navigate(action)
            }
            else {
                dialogUtils.showMessageDialog(requireContext(), "Error", "Cannot add a post-dated entry")
            }
        }

        weekView.setOnClickListener {
            findNavController().navigate(R.id.action_howAmINoEntryFragment_to_weeklyReportFragment)
        }

        layout_month.setOnClickListener {
            findNavController().navigate(
                R.id.action_howAmINoEntryFragment_to_monthlyReportFragment,
            )
        }

        addCircleImageViewBig.setOnClickListener {
            addCircleImageView.callOnClick()
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

        val dates = getWeekDates(calendar)
        weekAdapter.setDates(dates)
    }

    private fun setEntryLayout(isListEmpty: Boolean) {
        txt_entry.isInvisible = !isListEmpty
        addCircleImageView.isInvisible = !isListEmpty

        txt_noentry.isInvisible = !isListEmpty
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
        this.toDate = date.getDateInFormat(DATE_FORMAT)
        getDailyEntries(date)
    }

    override fun onItemClick(dailyCheckInEntry: DailyCheckInEntry) {

    }
}
