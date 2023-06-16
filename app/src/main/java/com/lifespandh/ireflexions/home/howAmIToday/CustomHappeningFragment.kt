package com.lifespandh.ireflexions.home.howAmIToday

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseDialogFragment
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentalCondition
import com.lifespandh.ireflexions.models.howAmIToday.PanicSymptom
import com.lifespandh.ireflexions.models.howAmIToday.PanicTrigger
import com.lifespandh.ireflexions.models.howAmIToday.WhatsHappening
import com.lifespandh.ireflexions.utils.ui.trimString
import kotlinx.android.synthetic.main.custom_happening.custom_name_editText

class CustomHappeningFragment: BaseDialogFragment() {

    private lateinit var view: View
    private lateinit var closeDialogImage : ImageView
    private lateinit var editText: EditText
    private lateinit var saveButton: Button

    private var dialogFor = DIALOG_FOR.WHATS_HAPPENING
    private val args: CustomHappeningFragmentArgs by navArgs()
    private val howAmITodayViewModel by activityViewModels<HowAmITodayViewModel> { viewModelFactory }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.custom_happening, null)
            builder.setView(view)

            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)

            this.view = view
            init()

            dialog
        } ?: throw IllegalStateException("Activity cannot be null.")
    }

    private fun init() {
        getArgumentValues()
        initViews()
        setListeners()
    }

    private fun getArgumentValues() {
        dialogFor = args.dialogFor
    }

    private fun initViews(){
        closeDialogImage = view.findViewById(R.id.img_close)
        editText = view.findViewById(R.id.custom_name_editText)
        saveButton = view.findViewById(R.id.btn_save)
    }
    private fun setListeners(){
        closeDialogImage.setOnClickListener {
            dismiss()
        }

        saveButton.setOnClickListener {
            val name = editText.trimString()
            when(dialogFor) {
                DIALOG_FOR.WHATS_HAPPENING -> {
                    val whatsHappening = WhatsHappening(name, "", true)
                    howAmITodayViewModel.selectedWhatsHappening.add(whatsHappening)
                    howAmITodayViewModel.newWhatsHappening.value = whatsHappening
                }
                DIALOG_FOR.ENVIRONMENTAL_CONDITIONS -> {
                    val environmentalCondition = EnvironmentalCondition(name, true)
                    howAmITodayViewModel.selectedEnvironmentalConditions.add(environmentalCondition)
                    howAmITodayViewModel.newEnvironmentalCondition.value = environmentalCondition
                }

                DIALOG_FOR.PANIC_TRIGGER -> {
                    val panicTrigger = PanicTrigger(name, true)
                    howAmITodayViewModel.newPanicTrigger.value = panicTrigger
                    howAmITodayViewModel.selectedPanicTriggers.add(panicTrigger)
                }
                DIALOG_FOR.PANIC_SYMPTOM -> {
                    val panicSymptom = PanicSymptom(name, true)
                    howAmITodayViewModel.selectedPanicSymptoms.add(panicSymptom)
                    howAmITodayViewModel.newPanicSymptom.value = panicSymptom
                }
            }
            dismiss()
        }
    }

    companion object {
    }
}