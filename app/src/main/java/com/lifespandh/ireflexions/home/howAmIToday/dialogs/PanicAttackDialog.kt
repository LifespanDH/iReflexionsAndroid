package com.lifespandh.ireflexions.home.howAmIToday.dialogs

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.models.DailyCheckInEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//class PanicAttackDialog(
//    context: Context,
//    val dailyItem: DailyCheckInEntry,
//    val viewLifecycleOwner: LifecycleOwner,
//    val disconnectTimerListener: DisconnectTimerListener,
//    private val intensityLogFile: AppLogFile?
//) : Dialog(context), PanicAttackTriggerAdapter.OnItemClickedListener,
//    PanicAttackSymptomsAdapter.OnItemClickedListener {
//
//    private lateinit var viewModel: HowAmIEntryViewModel
//
//    private var dialogUtils = DialogUtils()
//
//    private var onPanicAttackDialogListener: OnPanicAttackDialogListener? = null
//    private var onPanicAttackDialogSaveDataListener: OnPanicAttackDialogSaveDataListener? = null
//
//    private lateinit var panicAttackTriggerTriggerAdapter: PanicAttackTriggerAdapter
//    private lateinit var panicAttackSymptomsAdapter: PanicAttackSymptomsAdapter
//
//    private var panicTriggersList: ArrayList<PanicAttackTrigger> = ArrayList()
//    private var panicSymptomsList: ArrayList<PanicAttackSymptom> = ArrayList()
//
//    private var panicId = ""
//    private lateinit var uId: String
//    private lateinit var panicItem: PanicAttack
//
//    private lateinit var sharedPrefsHelper: SharedPrefsHelper
//
//    private val intensityLogStringBuilder = StringBuilder()
//
//    fun setOnPanicAttackDialogListener(onPanicAttackDialogListener: OnPanicAttackDialogListener) {
//        this.onPanicAttackDialogListener = onPanicAttackDialogListener
//    }
//
//    fun setOnPanicAttackDialogSaveDataListener(onPanicAttackDialogSaveDataListener: OnPanicAttackDialogSaveDataListener) {
//        this.onPanicAttackDialogSaveDataListener = onPanicAttackDialogSaveDataListener
//    }
//
//    companion object {
//        private val TAG = PanicAttackDialog::class.simpleName
//    }
//
//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        disconnectTimerListener.resetDisconnectTimer()
//        return super.dispatchTouchEvent(ev)
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setContentView(R.layout.panic_attack_entry_fragment)
//
//        val androidFactory =
//            ViewModelProvider.AndroidViewModelFactory((context.applicationContext as Application))
//        viewModel = androidFactory.create(HowAmIEntryViewModel::class.java)
//
//        if (dailyItem.panicAttack != null) {
//            panicItem = dailyItem.panicAttack!!
//            viewModel.panicAttack = panicItem
//            viewModel.triggersInit.addAll(panicItem.triggerResults)
//            viewModel.symptomsInit.addAll(panicItem.symptomResults)
//            viewModel.panicAttack!!.time = panicItem.time
//            viewModel.panicAttack!!.intensity = panicItem.intensity
//            setIntensity()
//        }
//
//        sharedPrefsHelper = SharedPrefsHelper(context.getActivity() as MainActivity)
//
//        uId = sharedPrefsHelper.currentUserId
//
//        val width = (context.resources.displayMetrics.widthPixels * 0.80).toInt()
//        val height = (context.resources.displayMetrics.heightPixels * 0.80).toInt()
//        window!!.setLayout(width, height)
//        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        triangleSliderView.intensity.observe(viewLifecycleOwner, {
//            viewModel.intensity = it
//            addIntensityLogLine(it)
//        })
//
//        val time: List<String> = context.resources.getStringArray(R.array.time).toList()
//
//        val adapter = object : ArrayAdapter<String>(
//            context.getActivity() as MainActivity,
//            R.layout.support_simple_spinner_dropdown_item,
//            time
//        ) {
//            override fun getDropDownView(
//                position: Int,
//                convertView: View?,
//                parent: ViewGroup
//            ): View {
//                val view = super.getDropDownView(
//                    position,
//                    convertView,
//                    parent
//                ) as TextView
//
//                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
//                view.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.lighter_gray
//                    )
//                )
//
//                return view
//            }
//
//            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//
//                val view = super.getView(position, convertView, parent) as TextView
//                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
//                view.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.lighter_gray
//                    )
//                )
//                return view
//            }
//
//        }
//        adapter.setDropDownViewResource(R.layout.spinner_item)
//        spinnerTime.adapter = adapter
//
//        val sdf = SimpleDateFormat("hh:mm aa")
//        val currentTime: String = sdf.format(
//            if (dailyItem.panicAttack != null)
//                panicItem.time
//            else
//                System.currentTimeMillis()
//        )
//        edt_time.setText(currentTime.substring(0, 5))
//        spinnerTime.setSelection(if (currentTime.substring(6, 8) == "am") 0 else 1)
//
//        val dateInputMask = DateInputMask(edt_time)
//        dateInputMask.listen()
//
//        setPanicAttackTriggerAdapter(triggersView)
//        setPanicAttackSymptomsAdapter(symptomsView)
//
//        img_close.setOnClickListener {
//            saveToIntensityLog(intensityLogStringBuilder)
//            dismiss()
//        }
//        btn_discard.setOnClickListener {
//            saveToIntensityLog(intensityLogStringBuilder)
//            dismiss()
//        }
//
//        btn_save.setOnClickListener {
//            saveToIntensityLog(intensityLogStringBuilder)
//            panicId = UUID.randomUUID().toString()
//            if (sharedPrefsHelper.userGuest) {
//                showDialog(
//                    context.getString(R.string.explore_without_an_account_dialog_title),
//                    context.getString(R.string.explore_without_an_account_dialog_body_text_entry)
//                )
//            } else if (sharedPrefsHelper.membershipLevel == MembershipLevel.NoSubscription.ordinal) {
//                showDialog(
//                    context.getString(R.string.member_ship_level_no_subscription_dialog_title),
//                    context.getString(R.string.member_ship_level_no_subscription_dialog_body_text_entry)
//                )
//            } else {
//                viewModel.panicAttack = PanicAttack()
//
//                viewModel.panicAttack!!.intensity = viewModel.intensity
//
//                for (i in viewModel.panicTriggerMap) {
//                    if (i.key < panicTriggersList.size) {
//                        val triggerResult =
//                            PanicAttackTriggerResult(
//                                userId = uId,
//                                triggerId = panicTriggersList[i.key].id,
//                                name = panicTriggersList[i.key].name,
//                                panicAttackId = panicId,
//                                isSelected = i.value
//                            )
//
//                        val serverId = panicTriggersList[i.key].serverId
//                        viewModel.panicAttack!!.triggerResults.add(triggerResult)
//                        viewModel.triggerResultWithId.add(
//                            TriggerItemResultModel(
//                                serverId,
//                                i.value
//                            )
//                        )
//                        Storage.triggerResultWithId.add(
//                            TriggerItemResultModel(
//                                serverId,
//                                i.value
//                            )
//                        )
//
//                        if (i.key < PanicAttackTriggers.defaultPanicAttackTriggers.size && i.value) {
//                            viewModel.panicAttack!!.triggerResults.add(triggerResult)
//                            viewModel.triggerResultWithId.add(
//                                TriggerItemResultModel(
//                                    serverId,
//                                    i.value
//                                )
//                            )
//                            Storage.triggerResultWithId.add(
//                                TriggerItemResultModel(
//                                    serverId,
//                                    i.value
//                                )
//                            )
//                        } else if (i.key >= PanicAttackTriggers.defaultPanicAttackTriggers.size) {
//                            viewModel.panicAttack!!.triggerResults.add(triggerResult)
//                            viewModel.triggerResultWithId.add(
//                                TriggerItemResultModel(
//                                    serverId,
//                                    i.value
//                                )
//                            )
//                            Storage.triggerResultWithId.add(
//                                TriggerItemResultModel(
//                                    serverId,
//                                    i.value
//                                )
//                            )
//
//                        }
//                    }
//                }
//
//                for (i in viewModel.panicSymptomMap) {
//                    if (i.key < panicSymptomsList.size) {
//                        val symptomResult =
//                            PanicAttackSymptomResult(
//                                userId = uId,
//                                symptomId = panicSymptomsList[i.key].id,
//                                name = panicSymptomsList[i.key].name,
//                                panicAttackId = panicId,
//                                isSelected = i.value
//                            )
//
//                        val serverId = panicSymptomsList[i.key].serverId
//                        viewModel.panicAttack!!.symptomResults.add(symptomResult)
//                        viewModel.symptomResultWithId.add(
//                            SymptomItemResultModel(
//                                serverId,
//                                i.value
//                            )
//                        )
//                        Storage.symptomResultWithId.add(
//                            SymptomItemResultModel(
//                                serverId,
//                                i.value
//                            )
//                        )
//
//                        if (i.key < PanicAttackSymptoms.defaultPanicAttackSymptoms.size && i.value) {
//                            viewModel.panicAttack!!.symptomResults.add(symptomResult)
//                            viewModel.symptomResultWithId.add(
//                                SymptomItemResultModel(
//                                    serverId,
//                                    i.value
//                                )
//                            )
//                            Storage.symptomResultWithId.add(
//                                SymptomItemResultModel(
//                                    serverId,
//                                    i.value
//                                )
//                            )
//                        } else if (i.key >= PanicAttackSymptoms.defaultPanicAttackSymptoms.size) {
//                            viewModel.panicAttack!!.symptomResults.add(symptomResult)
//                            viewModel.symptomResultWithId.add(
//                                SymptomItemResultModel(
//                                    serverId,
//                                    i.value
//                                )
//                            )
//                            Storage.symptomResultWithId.add(
//                                SymptomItemResultModel(
//                                    serverId,
//                                    i.value
//                                )
//                            )
//                        }
//                    }
//                }
//
//                val dataPanicAttack = DataPanicAttack(
//                    id = panicId,
//                    time = Calendar.getInstance().time,
//                    intensity = viewModel.intensity // 0 to 5
//                )
//
//                GlobalScope.launch(Dispatchers.Main) {
//                    withContext(Dispatchers.IO) {
//                        viewModel.insertPanicTriggerResult(viewModel.panicAttack!!.triggerResults)
//                        viewModel.insertPanicSymptomResult(viewModel.panicAttack!!.symptomResults)
//                        viewModel.insert(dataPanicAttack)
//                    }
//                }
//                dailyItem.panicAttack = viewModel.panicAttack
//
//                if (onPanicAttackDialogListener != null)
//                    onPanicAttackDialogListener?.onSavePanicAttack(viewModel, dataPanicAttack.id)
//
//                if (onPanicAttackDialogSaveDataListener != null) {
//                    saveData()
//                }
//
//                dismiss()
//            }
//        }
//
//        viewModel.errorMessageResIdLiveData.observe(viewLifecycleOwner,
//            {
//                if (it != 0) {
//                    (context.getActivity() as MainActivity).showMessage(it)
//                }
//            })
//
//    }
//
//    private fun addIntensityLogLine(intensity: Int?) {
//        intensityLogStringBuilder.appendLine("${Date()}; $intensity")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        setIntensity()
//        dialogUtils.dismissAllDialogs()
//    }
//
//    private fun showDialog(title: String, message: String) {
//        ExploreWithoutAnAccountDialog.newInstance(title, message)
//            .show((context.getActivity() as FragmentActivity).supportFragmentManager, TAG)
//    }
//
//    private fun setIntensity() {
//        viewModel.panicAttack?.apply {
//            triangleSliderView.setIntensity(this.intensity)
//            return
//        }
//        triangleSliderView.setIntensity(0)
//    }
//
//    private fun saveData() {
//
//        Toast.makeText(
//            context,
//            context.getString(R.string.message_saving_data_waiting_please),
//            Toast.LENGTH_LONG
//        ).show()
//
//        viewModel.checkInEntry.panicAttack = panicItem
//
//        GlobalScope.launch(Dispatchers.Main) {
//
//            var checkInDailyUpdateResult = false
//
//            withContext(Dispatchers.IO) {
//                checkInDailyUpdateResult = viewModel.updateDailyCheckInToServer(dailyItem)
//            }
//
//            if (checkInDailyUpdateResult) {
//
//                Toast.makeText(
//                    context,
//                    context.getString(R.string.message_updated_successfully),
//                    Toast.LENGTH_LONG
//                ).show()
//                if (onPanicAttackDialogSaveDataListener != null) {
//                    onPanicAttackDialogSaveDataListener?.onSavePanicAttack(viewModel)
//                }
//            }
//        }
//    }
//
//    private fun setPanicAttackSymptomsAdapter(symptomsView: RecyclerView) {
//        symptomsView.layoutManager =
//            object : GridLayoutManager(context, 2, VERTICAL, false) {
//                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
//                    lp.width = width / spanCount
//                    return true
//                }
//            }
//
//        panicSymptomsList.addAll(PanicAttackSymptoms.defaultPanicAttackSymptoms)
//
//        if (dailyItem.panicAttack != null) {
//            val list = panicItem.symptomResults
//
//            for (i in list) {
//                viewModel.panicSymptomMap[i.symptomId] = i.isSelected
//            }
//        }
//
//        val symptomResultsList =
//            if (dailyItem.panicAttack != null) dailyItem.panicAttack?.symptomResults else arrayListOf()
//        panicAttackSymptomsAdapter =
//            PanicAttackSymptomsAdapter(
//                context,
//                panicSymptomsList,
//                symptomResultsList!!,
//                viewModel
//            )
//        panicAttackSymptomsAdapter.setOnItemClickedListener(this)
//
//        symptomsView.adapter = panicAttackSymptomsAdapter
//    }
//
//    private fun setPanicAttackTriggerAdapter(triggersView: RecyclerView) {
//
//        panicTriggersList.addAll(PanicAttackTriggers.defaultPanicAttackTriggers)
//
//        if (dailyItem.panicAttack != null) {
//            val list = panicItem.triggerResults
//
//            for (i in list) {
//                viewModel.panicTriggerMap[i.triggerId] = i.isSelected
//            }
//
//        }
//        triggersView.layoutManager =
//            object : GridLayoutManager(context, 2, VERTICAL, false) {
//                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
//                    lp.width = width / spanCount
//                    return true
//                }
//            }
//
//        panicAttackTriggerTriggerAdapter = PanicAttackTriggerAdapter(
//            context,
//            panicTriggersList,
//            viewModel
//        )
//        panicAttackTriggerTriggerAdapter.setOnItemClickedListener(this)
//        triggersView.adapter = panicAttackTriggerTriggerAdapter
//
//    }
//
//    override fun onItemClick(
//        panicAttackTrigger: PanicAttackTrigger,
//        position: Int,
//        viewHolder: PanicAttackTriggerAdapter.ViewHolder
//    ) {
//        dialogUtils.showAddItemDialog(context) {
//            viewModel.panicTriggerMap[panicTriggersList.size] = false
//
//            val panicAttackTrigger = PanicAttackTrigger(
//                id = panicTriggersList.size,
//                serverId = panicAttackTrigger.serverId,
//                name = it,
//                true
//            )
//
//            PanicAttackTriggers.customPanicAttackTriggers.add(
//                PanicAttackTriggers.customPanicAttackTriggers.size,
//                panicAttackTrigger
//            )
//            panicTriggersList.add(
//                panicTriggersList.size,
//                PanicAttackTriggers.customPanicAttackTriggers.last()
//            )
//
//            panicAttackTrigger.apply {
//                val panicAttackTriggerResult = PanicAttackTriggerResult(
//                    PanicAttackTriggers.customPanicAttackTriggers.last().id,
//                    "",
//                    this.name,
//                    this.id,
//                    "",
//                    false,
//                    this.serverId
//                )
//                GlobalScope.launch(Dispatchers.Main) {
//                    withContext(Dispatchers.IO) {
//                        viewModel.updateEntriesPanicAttackTriggersToServer(
//                            panicAttackTriggerResult,
//                            this@apply
//                        )
//                    }
//                }
//            }
//            panicAttackTriggerTriggerAdapter.notifyDataSetChanged()
//
//        }
//    }
//
//    override fun onLongItemClick(
//        position: Int,
//        viewHolder: PanicAttackTriggerAdapter.ViewHolder,
//        panicAttackTrigger: PanicAttackTrigger
//    ) {
//        if (panicTriggersList[position].isCustom) {
//            dialogUtils.showRemoveItemDialog(context, panicAttackTrigger.name) {
//                val serverId = panicTriggersList[position].serverId
//                viewModel.panicTriggerMap.remove(position)
//                onPanicAttackDialogListener?.onRemoveItemPanicTrigge(viewModel)
//                panicTriggersList.removeAt(position)
//                PanicAttackTriggers.defaultPanicAttackTriggers.removeAt(position)
//                panicAttackTriggerTriggerAdapter.notifyDataSetChanged()
//
//                GlobalScope.launch(Dispatchers.Main) {
//                    withContext(Dispatchers.IO) {
//                        viewModel.deleteTriggerItemsServer(serverId)
//                        viewModel.deleteTriggerLocalDb(serverId)
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onItemClick(position: Int, viewHolder: PanicAttackSymptomsAdapter.ViewHolder) {
//        dialogUtils.showAddItemDialog(context) {
//            viewModel.panicSymptomMap[panicSymptomsList.size] = false
//
//            val panicAttackSymptom =
//                PanicAttackSymptom(id = panicSymptomsList.size, serverId = "", name = it, true)
//
//            PanicAttackSymptoms.customPanicAttackSymptoms.add(
//                PanicAttackSymptoms.customPanicAttackSymptoms.size,
//                panicAttackSymptom
//            )
//            panicSymptomsList.add(
//                panicSymptomsList.size,
//                PanicAttackSymptoms.customPanicAttackSymptoms.last()
//            )
//            panicAttackSymptomsAdapter.notifyDataSetChanged()
//
//            panicAttackSymptom.apply {
//                val panicAttackSymptomResult = PanicAttackSymptomResult(
//                    PanicAttackSymptoms.customPanicAttackSymptoms.last().id,
//                    "",
//                    this.name,
//                    this.id,
//                    "",
//                    false,
//                    this.serverId
//                )
//                GlobalScope.launch(Dispatchers.Main) {
//                    withContext(Dispatchers.IO) {
//                        viewModel.updateEntriesPanicAttackSymptomsToServer(
//                            panicAttackSymptomResult,
//                            this@apply
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onLongItemClick(
//        position: Int,
//        viewHolder: PanicAttackSymptomsAdapter.ViewHolder,
//        panicAttackSymptom: PanicAttackSymptom
//    ) {
//        if (panicSymptomsList[position].isCustom) {
//            dialogUtils.showRemoveItemDialog(context, panicAttackSymptom.name) {
//                val serverId = panicSymptomsList[position].serverId
//                viewModel.panicSymptomMap.remove(position)
//                onPanicAttackDialogListener?.onRemoveItemPanicSymptom(viewModel)
//                panicSymptomsList.removeAt(position)
//                PanicAttackSymptoms.defaultPanicAttackSymptoms.removeAt(position)
//                panicAttackSymptomsAdapter.notifyDataSetChanged()
//
//                GlobalScope.launch(Dispatchers.Main) {
//                    withContext(Dispatchers.IO) {
//                        viewModel.deleteSymptomItemsServer(serverId)
//                        viewModel.deleteSymptomLocalDb(serverId)
//                    }
//                }
//            }
//        }
//    }
//
//    private fun saveToIntensityLog(stringBuilder: StringBuilder) {
//        intensityLogFile?.write(stringBuilder.toString())
//        intensityLogFile?.flush()
//    }
//
//    interface OnPanicAttackDialogSaveDataListener {
//        fun onSavePanicAttack(viewModel: HowAmIEntryViewModel)
//    }
//
//    interface OnPanicAttackDialogListener {
//        fun onSavePanicAttack(viewModel: HowAmIEntryViewModel, dataPanicAttackId: String)
//        fun onRemoveItemPanicTrigge(viewModel: HowAmIEntryViewModel)
//        fun onRemoveItemPanicSymptom(viewModel: HowAmIEntryViewModel)
//    }
//
//}