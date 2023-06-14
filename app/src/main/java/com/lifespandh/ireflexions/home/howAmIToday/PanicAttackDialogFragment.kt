package com.lifespandh.ireflexions.home.howAmIToday

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.home.course.CoursesAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.PanicAttackSymptomsAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.PanicAttackTriggersAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.PanicAttackSymptom
import com.lifespandh.ireflexions.models.PanicAttackSymptoms
import com.lifespandh.ireflexions.models.PanicAttackTrigger
import com.lifespandh.ireflexions.models.PanicAttackTriggers
import com.lifespandh.ireflexions.models.howAmIToday.PanicSymptom
import com.lifespandh.ireflexions.models.howAmIToday.PanicTrigger
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_panic_attack.img_close
import kotlinx.android.synthetic.main.fragment_panic_attack.symptomsView
import kotlinx.android.synthetic.main.fragment_panic_attack.triggersView
import kotlinx.android.synthetic.main.fragment_panic_attack.view.img_close
import kotlinx.android.synthetic.main.fragment_panic_attack.view.symptomsView
import kotlinx.android.synthetic.main.fragment_panic_attack.view.triggersView


class PanicAttackDialogFragment : BaseDialogFragment(), PanicAttackTriggersAdapter.OnItemClicked, PanicAttackSymptomsAdapter.OnItemClicked {

    private lateinit var view: View
    private lateinit var closeDialogImage : ImageView

    private var panicTriggersList: ArrayList<PanicAttackTrigger> = ArrayList()
    private var panicSymptomsList: ArrayList<PanicAttackSymptom> = ArrayList()

    private val panicAttackTriggersAdapter by lazy { PanicAttackTriggersAdapter(listOf(), this) }
    private val panicAttackSymptomsAdapter by lazy { PanicAttackSymptomsAdapter(listOf(), this) }

    private val howAmITodayViewModel by activityViewModels<HowAmITodayViewModel> { viewModelFactory }

    private var panicSymptoms: List<PanicSymptom> = listOf()
    private var panicTriggers: List<PanicTrigger> = listOf()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_panic_attack, null)
            builder.setView(view)

            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)

            this.view = view
            logE("called view create")

            dialog
        } ?: throw IllegalStateException("Activity cannot be null.")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logE("called view created")
        init()
    }

    private fun init() {
        setViews()
        setObservers()
        setListeners()
    }

    private fun setListeners(){
        view.img_close.setOnClickListener {
            dismiss()
        }
    }

    private fun setViews() {

        panicTriggersList.addAll(PanicAttackTriggers.defaultPanicAttackTriggers)
        panicSymptomsList.addAll(PanicAttackSymptoms.defaultPanicAttackSymptoms)

        view.triggersView.apply {
            adapter = panicAttackTriggersAdapter
            layoutManager =
                object : GridLayoutManager(context, 2, VERTICAL, false) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.width = width / spanCount
                    return true
                }
            }
        }
//        panicAttackTriggersAdapter.setList(panicTriggersList)

        view.symptomsView.apply {
            adapter = panicAttackSymptomsAdapter
            layoutManager =
                object : GridLayoutManager(context, 2, VERTICAL, false) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.width = width / spanCount
                    return true
                }
            }
        }
//        panicAttackSymptomsAdapter.setList(panicSymptomsList)
    }

    private fun setObservers() {
        howAmITodayViewModel.howAmITodayLiveData.observe(viewLifecycleOwner) {
            panicAttackSymptomsAdapter.setList(it.panicSymptoms)
            panicAttackTriggersAdapter.setList(it.panicTriggers)
        }
    }

    override fun onItemClicked(panicAttackTrigger: PanicTrigger) {
        TODO("Not yet implemented")
    }

    override fun onItemClicked(panicAttackSymptom: PanicSymptom) {
        TODO("Not yet implemented")
    }
}
