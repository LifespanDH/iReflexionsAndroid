package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.lifespandh.irefgraphs.CheckinObject
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeViewModel
import com.lifespandh.ireflexions.home.care.CareCenterFragmentDirections
import com.lifespandh.ireflexions.home.howAmIToday.adapters.EnvironmentalAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.HappeningAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.EmotionTraits
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentCondition
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentConditions.Companion.defaultEnvironmentConditions
import com.lifespandh.ireflexions.models.howAmIToday.Happening
import com.lifespandh.ireflexions.models.howAmIToday.Happenings.Companion.defaultHappenings
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.LOGIN_CUSTOM_USER
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.btn_sleep_hour
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.btn_sleep_quality
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.checkinCircleCategory
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.environmentalView
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.happeningView

class HowAmICreateEntryFragment : BaseFragment(), HappeningAdapter.OnItemClicked, EnvironmentalAdapter.OnItemClicked {

    private val happeningAdapter by lazy { HappeningAdapter(listOf(), this) }
    private val environmentalAdapter by lazy { EnvironmentalAdapter(listOf(), this) }
    private var happeningsList: ArrayList<Happening> = ArrayList()
    private var environmentList: ArrayList<EnvironmentCondition> = ArrayList()
    private val howAmITodayViewModel by viewModels<HowAmITodayViewModel> { viewModelFactory }


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

    private fun init(){
        setCircleViews()
        setRecyclerViews()
        getTraitCategories()
        setObservers()
    }

    private fun setCircleViews(){
        btn_sleep_hour.text = "Unsure"
        btn_sleep_quality.text = "Unsure"
        val traitCategories: MutableList<CheckinObject> = mutableListOf()
        val traits: MutableList<CheckinObject> = mutableListOf()

        for ((index, traitCategory) in EmotionTraits.categories.withIndex()) {
            traitCategories.add(
                CheckinObject(
                    index,
                    ContextCompat.getColor(requireContext(), traitCategory.color),
                    traitCategory.name
                )
            )
        }

        checkinCircleCategory.checkinObjects = traitCategories
        checkinCircleCategory.isCategory = true

    }

    private fun setRecyclerViews(){
        happeningsList.addAll(defaultHappenings)
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
        happeningAdapter.setList(happeningsList)

        environmentList.addAll(defaultEnvironmentConditions)
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
        environmentalAdapter.setList(environmentList)

    }

    private fun getTraitCategories(){
        howAmITodayViewModel.getTraitCategories()
    }

    private fun setObservers(){
        howAmITodayViewModel.traitCategoryLiveData.observeFreshly(this){

        }
    }

    override fun onItemClicked(happening: Happening) {

    }

    override fun onCustomItemClicked() {

        Log.d("check","entered onCustomClick")
        val action = HowAmICreateEntryFragmentDirections.actionHowAmICreateEntryFragmentToCustomHappeningFragment()
        findNavController().navigate(action)
    }

    override fun onEnvironmentItemClicked() {
        TODO("Not yet implemented")
    }
}