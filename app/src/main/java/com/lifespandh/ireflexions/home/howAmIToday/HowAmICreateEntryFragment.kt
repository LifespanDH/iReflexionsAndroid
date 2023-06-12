package com.lifespandh.ireflexions.home.howAmIToday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.irefgraphs.CheckinObject
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.home.howAmIToday.adapters.EnvironmentalAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.HappeningAdapter
import com.lifespandh.ireflexions.models.howAmI.EmotionTraits
import com.lifespandh.ireflexions.models.howAmI.EnvironmentCondition
import com.lifespandh.ireflexions.models.howAmI.EnvironmentConditions.Companion.defaultEnvironmentConditions
import com.lifespandh.ireflexions.models.howAmI.Happening
import com.lifespandh.ireflexions.models.howAmI.Happenings.Companion.defaultHappenings
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.checkinCircleCategory
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.environmentalView
import kotlinx.android.synthetic.main.fragment_how_am_i_create_entry.happeningView

class HowAmICreateEntryFragment : Fragment(), HappeningAdapter.OnItemClicked, EnvironmentalAdapter.OnItemClicked {

    private val happeningAdapter by lazy { HappeningAdapter(listOf(), this) }
    private val environmentalAdapter by lazy { EnvironmentalAdapter(listOf(), this) }
    private var happeningsList: ArrayList<Happening> = ArrayList()
    private var environmentList: ArrayList<EnvironmentCondition> = ArrayList()

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
    }

    private fun setCircleViews(){
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

    override fun onItemClicked(link: String) {
//        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
//        startActivity(browserIntent)
    }
}