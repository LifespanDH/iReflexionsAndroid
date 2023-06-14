package com.lifespandh.ireflexions.home.howAmIToday

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.home.course.CoursesAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.PanicAttackSymptomsAdapter
import com.lifespandh.ireflexions.home.howAmIToday.adapters.PanicAttackTriggersAdapter
import com.lifespandh.ireflexions.models.PanicAttackSymptom
import com.lifespandh.ireflexions.models.PanicAttackSymptoms
import com.lifespandh.ireflexions.models.PanicAttackTrigger
import com.lifespandh.ireflexions.models.PanicAttackTriggers
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



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_panic_attack, null)
            builder.setView(view)

            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)

            this.view = view
            init()

            dialog
        } ?: throw IllegalStateException("Activity cannot be null.")
    }

    private fun init(){
        setViews()
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
        panicAttackTriggersAdapter.setList(panicTriggersList)

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
        panicAttackSymptomsAdapter.setList(panicSymptomsList)
    }

    override fun onItemClicked(panicAttackTrigger: PanicAttackTrigger) {
        TODO("Not yet implemented")
    }

    override fun onItemClicked(panicAttackSymptom: PanicAttackSymptom) {
        TODO("Not yet implemented")
    }
}
