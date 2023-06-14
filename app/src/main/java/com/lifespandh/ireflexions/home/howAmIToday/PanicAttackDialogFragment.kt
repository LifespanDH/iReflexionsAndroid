package com.lifespandh.ireflexions.home.howAmIToday

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.home.howAmIToday.adapters.PanicAttackSymptomsAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.PanicAttackTriggersAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.PanicSymptom
import com.lifespandh.ireflexions.models.howAmIToday.PanicTrigger
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_panic_attack.view.img_close
import kotlinx.android.synthetic.main.fragment_panic_attack.view.symptomsView
import kotlinx.android.synthetic.main.fragment_panic_attack.view.triggersView

class PanicAttackDialogFragment : BaseDialogFragment(), PanicAttackTriggersAdapter.OnItemClicked, PanicAttackSymptomsAdapter.OnItemClicked {

    private lateinit var view: View

    private val panicAttackTriggersAdapter by lazy { PanicAttackTriggersAdapter(listOf(), this, howAmITodayViewModel) }
    private val panicAttackSymptomsAdapter by lazy { PanicAttackSymptomsAdapter(listOf(), this, howAmITodayViewModel) }

    private val howAmITodayViewModel by activityViewModels<HowAmITodayViewModel> { viewModelFactory }

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
    }

    private fun setObservers() {
        howAmITodayViewModel.howAmITodayLiveData.observe(viewLifecycleOwner) {
            val symptoms = it.panicSymptoms.toMutableList()
            symptoms.add(PanicSymptom.other())
            panicAttackSymptomsAdapter.setList(symptoms)

            val triggers = it.panicTriggers.toMutableList()
            triggers.add(PanicTrigger.other())
            panicAttackTriggersAdapter.setList(triggers)
        }
    }

    override fun onItemClicked(panicAttackTrigger: PanicTrigger) {

    }

    override fun onItemClicked(panicAttackSymptom: PanicSymptom) {

    }

    override fun onCustomSymptomClicked() {
        val action = PanicAttackDialogFragmentDirections.actionPanicAttackDialogFragmentToCustomHappeningFragment(DIALOG_FOR.PANIC_SYMPTOM)
        findNavController().navigate(action)
    }

    override fun onCustomTriggerClicked() {
        val action = PanicAttackDialogFragmentDirections.actionPanicAttackDialogFragmentToCustomHappeningFragment(DIALOG_FOR.PANIC_TRIGGER)
        findNavController().navigate(action)
    }
}
