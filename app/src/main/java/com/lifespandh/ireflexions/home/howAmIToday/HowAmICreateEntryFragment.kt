package com.lifespandh.ireflexions.home.howAmIToday

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.irefgraphs.CheckinObject
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.howAmIToday.adapters.EnvironmentalAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.HappeningAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.TraitAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentalCondition
import com.lifespandh.ireflexions.models.howAmIToday.SleepQuality
import com.lifespandh.ireflexions.models.howAmIToday.TraitCategory
import com.lifespandh.ireflexions.models.howAmIToday.TraitSubCategory
import com.lifespandh.ireflexions.models.howAmIToday.UserTrait
import com.lifespandh.ireflexions.models.howAmIToday.WhatsHappening
import com.lifespandh.ireflexions.utils.date.getDateTimeInFormat
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeInvisible
import com.lifespandh.ireflexions.utils.ui.makeVisible
import com.lifespandh.ireflexions.utils.ui.toast
import com.lifespandh.ireflexions.utils.ui.trimString
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.btn_discard
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.btn_save
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.btn_sleep_hour
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.btn_sleep_quality
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.checkinCircleCategory
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.checkinCircleTrait
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.edt_journal
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.environmentalView
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.happeningView
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.img_back
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.loader
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.mainLayout
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.seekBar_movement
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.seekBar_sleep
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.seekBar_sleep_quality
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.traitView

class HowAmICreateEntryFragment : BaseFragment(), HappeningAdapter.OnItemClicked, EnvironmentalAdapter.OnItemClicked,
    TraitAdapter.OnItemClickedListener {

    private val happeningAdapter by lazy { HappeningAdapter(mutableListOf(), this, howAmITodayViewModel) }
    private val environmentalAdapter by lazy { EnvironmentalAdapter(mutableListOf(), this, howAmITodayViewModel) }
    private val traitAdapter = TraitAdapter(
        mutableListOf(),
        this
    )
    private val howAmITodayViewModel by activityViewModels<HowAmITodayViewModel> { viewModelFactory }

    private var currentCategory: TraitCategory? = null
    private var dateTime: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_how_am_i_create_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setViews()
        setRecyclerViews()
        getTraitCategories()
        setListeners()
        setObservers()
    }

    private fun setViews() {
        traitView.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        traitView.adapter = traitAdapter
    }

    private fun setCircleViews(traitCategories: List<TraitCategory>) {
        btn_sleep_hour.text = "Unsure"
        btn_sleep_quality.text = "Unsure"
        val checkInObjects: MutableList<CheckinObject> = mutableListOf()
        val traits: MutableList<CheckinObject> = mutableListOf()

        traitCategories.forEachIndexed { index, traitCategory ->
            checkInObjects.add(
                CheckinObject(
                    traitCategory.id,
                    index,
                    Color.parseColor(traitCategory.color),
                    traitCategory.name
                )
            )
        }

        checkinCircleCategory.checkinObjects = checkInObjects
        checkinCircleCategory.isCategory = true
        checkinCircleCategory.invalidateView()

        checkinCircleCategory.selectedSector.observe(viewLifecycleOwner) {
            currentCategory = traitCategories[it]
            traits.clear()
            val selectedSubCategoryList = howAmITodayViewModel.selectedTraitSubCategory.get(currentCategory!!.id)
            currentCategory?.traits?.forEachIndexed { index, traitSubCategory ->
                traits.add(
                    CheckinObject(
                        traitSubCategory.id,
                        index,
                        Color.parseColor(traitSubCategory.color),
                        traitSubCategory.name,
                        selectedSubCategoryList?.contains(traitSubCategory) == true
                    )
                )
            }
            checkinCircleTrait.makeVisible()
            checkinCircleCategory.makeInvisible()
            img_back.makeVisible()
            checkinCircleTrait.checkinObjects = traits
            checkinCircleTrait.invalidateView()
        }

        checkinCircleTrait.selectedSector.observe(viewLifecycleOwner) { it1 ->
            val currentTrait = currentCategory!!.traits[it1]

            if (howAmITodayViewModel.selectedTraitSubCategory.containsKey(currentCategory!!.id)) {
                val traitSubCategoryList = howAmITodayViewModel.selectedTraitSubCategory.get(currentCategory!!.id)

                if (traitSubCategoryList?.contains(currentTrait) == true) {
                    traitSubCategoryList.remove(currentTrait)
                } else {
                    traitSubCategoryList?.add(currentTrait)
                }
                howAmITodayViewModel.selectedTraitSubCategory[currentCategory!!.id] =
                    traitSubCategoryList!!
            } else {
                howAmITodayViewModel.selectedTraitSubCategory[currentCategory!!.id] = mutableListOf(currentTrait,)
            }

            traitAdapter.removeOrAdd(currentTrait)
        }

    }

    private fun setRecyclerViews() {
        happeningView.apply {
            adapter = happeningAdapter
            layoutManager =
                object: GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false) {
                    override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                        lp.width = width / spanCount
                        return true
                    }
                }
        }

        environmentalView.apply {
            adapter = environmentalAdapter
            layoutManager =
                object : GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false) {
                    override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                        lp.width = width / spanCount
                        return true
                    }
                }
        }
    }

    private fun getTraitCategories() {
        howAmITodayViewModel.getHowAmITodayData()
        loader.makeVisible()
        mainLayout.makeGone()
    }

    private fun setListeners() {
        img_back.setOnClickListener {
            img_back.visibility = View.INVISIBLE
            checkinCircleCategory.visibility = View.VISIBLE
            checkinCircleTrait.visibility = View.INVISIBLE
        }

        btn_save.setOnClickListener {
            val traitSubCategories = mutableListOf<TraitSubCategory>()
            val userTraits = mutableListOf<UserTrait>()
            howAmITodayViewModel.selectedTraitSubCategory.forEach {
                val traits = mutableListOf<TraitSubCategory>()
                for (trait in it.value)
                    traits.add(trait)
                userTraits.add(
                    UserTrait(it.key, traits)
                )
            }
            howAmITodayViewModel.selectedTraitSubCategory.values.forEach {
                for (trait in it)
                    traitSubCategories.add(trait)
            }

            val selectedPanicAttack = howAmITodayViewModel.selectedPanicAttack.value

            val sleepQuality = SleepQuality(seekBar_sleep.progress, seekBar_sleep_quality.progress)

            val dailyCheckInEntry = DailyCheckInEntry(
                userTraits = userTraits,
                whatsHappening = howAmITodayViewModel.selectedWhatsHappening,
                panicAttack = selectedPanicAttack,
                environmentalConditions = howAmITodayViewModel.selectedEnvironmentalConditions,
                sleepQuality = sleepQuality,
                movement = seekBar_movement.progress,
                journalEntry = edt_journal.trimString(),
                dateTime = getDateTimeInFormat()
            )

            howAmITodayViewModel.addDailyCheckInEntry(dailyCheckInEntry)
        }

        btn_discard.setOnClickListener {
        // we can add dialog here to ask before leaving the screen
            findNavController().navigateUp()
        }

    }

    private fun setObservers(){
        howAmITodayViewModel.howAmITodayLiveData.observeFreshly(this) {
            val traitCategories = it.traitCategories
            val whatsHappening = it.whatsHappening.toMutableList()
            val environmentalConditions = it.environmentalConditions.toMutableList()

            whatsHappening.add(WhatsHappening.createNew())
            environmentalConditions.add(EnvironmentalCondition.other())

            setCircleViews(traitCategories)
            happeningAdapter.setList(whatsHappening)
            environmentalAdapter.setList(environmentalConditions)

            mainLayout.makeVisible()
            loader.makeGone()
        }

        howAmITodayViewModel.newWhatsHappening.observeFreshly(this) {
            it?.let {
                happeningAdapter.addUserCreated(it)
            }
        }

        howAmITodayViewModel.newEnvironmentalCondition.observeFreshly(this) {
            it?.let {
                environmentalAdapter.addUserCreated(it)
            }
        }

        howAmITodayViewModel.dailyCheckInEntryAddedLiveData.observeFreshly(this) {
            toast("added daily entry")
            findNavController().navigateUp()
        }
    }

    override fun onItemClicked(happening: WhatsHappening) {
        if (happening.panicAttack) {
            val action = HowAmICreateEntryFragmentDirections.actionHowAmICreateEntryFragmentToPanicAttackDialogFragment()
            findNavController().navigate(action)
        } else {

        }
    }

    override fun onCustomItemClicked() {
        val action = HowAmICreateEntryFragmentDirections.actionHowAmICreateEntryFragmentToCustomHappeningFragment(DIALOG_FOR.WHATS_HAPPENING)
        findNavController().navigate(action)
    }

    override fun onEnvironmentItemClicked() {
        TODO("Not yet implemented")
    }

    override fun onOtherClicked() {
        val action = HowAmICreateEntryFragmentDirections.actionHowAmICreateEntryFragmentToCustomHappeningFragment(DIALOG_FOR.ENVIRONMENTAL_CONDITIONS)
        findNavController().navigate(action)
    }

    override fun onCancelClicked(traitSubCategory: TraitSubCategory) {

        val index = checkinCircleTrait.checkinObjects.filter { it.id == traitSubCategory.id }.firstOrNull()
        index?.let {
            checkinCircleTrait.checkinObjects[it.orderNumber].isSelected = false
        }
        if (howAmITodayViewModel.selectedTraitSubCategory.containsKey(traitSubCategory.traitId))
            howAmITodayViewModel.selectedTraitSubCategory[traitSubCategory.traitId]?.remove(traitSubCategory)
        checkinCircleTrait.invalidate()
    }

    override fun onDestroyView() {
        howAmITodayViewModel.clearData()
        super.onDestroyView()
    }
}