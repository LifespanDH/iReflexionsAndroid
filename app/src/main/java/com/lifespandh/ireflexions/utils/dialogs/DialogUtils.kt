package com.lifespandh.ireflexions.utils.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.lifespandh.ireflexions.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogUtils {

    private var removeItemDialog: AlertDialog? = null
    private var messageDialog: AlertDialog? = null
    private var changeEmailDialogView: Dialog? = null
    private var okCancelDialog: AlertDialog? = null
    private var connectBiofeedbackDialog: AlertDialog? = null
    private var baselineDialog: AlertDialog? = null
    private var frameOverlayDialog: Dialog? = null
    private var biofeedbackOverlayDialog: Dialog? = null
    private var addItemDialogView: Dialog? = null
    private var turnOnBluetoothDialog: AlertDialog? = null
    private var disconnectTpsDeviceDialog: AlertDialog? = null
    private var offlineLoginDialog: AlertDialog? = null
    private var selectSensorDialog: AlertDialog? = null
    private var sendFeedbackDialog: Dialog? = null

    fun showMessage(context: Context, @StringRes messageResId: Int) {
        showMessage(context, context.getString(messageResId))
    }

    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showMessageDialog(context:Context, header: String, message:String, onOkClick: () -> Unit = {}) {
        val dialog = AlertDialog.Builder(context)
            .create()

        LayoutInflater.from(context).inflate(R.layout.popup_view, null).apply {
            dialog.setView(this)
            findViewById<TextView>(R.id.headerTextView)?.text = header
            findViewById<TextView>(R.id.messageTextView)?.text = message
            val cancelButton = findViewById<Button>(R.id.cancelButton)
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            findViewById<Button>(R.id.okButton).setOnClickListener {
                    onOkClick()
            }
        }

        dialog.show()
    }

//    fun showMessageDialog(context: Context, message: String, postAction: () -> Unit) {
//        messageDialog = AlertDialog.Builder(context)
//            .create()
//
//        LayoutInflater.from(context).inflate(R.layout.popup_view, null).apply {
//            messageDialog?.setView(this)
//            findViewById<TextView>(R.id.headerTextView)?.visibility = View.GONE
//            findViewById<View>(R.id.divider)?.visibility = View.GONE
//            findViewById<TextView>(R.id.messageTextView)?.text = message
//            val cancelButton = findViewById<Button>(R.id.cancelButton)
//            cancelButton.visibility = View.INVISIBLE
//            cancelButton.setOnClickListener {
//                messageDialog?.dismiss()
//            }
//            findViewById<Button>(R.id.okButton).setOnClickListener {
//                postAction.invoke()
//                messageDialog?.dismiss()
//            }
//        }
//        messageDialog?.show()
//    }
//
//    fun showChangeEmailDialog(context: Context, postAction: (name: String) -> Unit) {
//
//        changeEmailDialogView = Dialog(context)
//        changeEmailDialogView!!.setContentView(R.layout.custom_happening)
//
//        val window: Window = changeEmailDialogView?.window!!
//
//        val width = (context.resources.displayMetrics.widthPixels * 0.80).toInt()
//        val height = (context.resources.displayMetrics.heightPixels * 0.30).toInt()
//        window.setLayout(width, height)
//
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        var appServerResult: AppServerResult
//
//        val customName: EditText = changeEmailDialogView!!.findViewById(R.id.custom_name_editText)
//        val imgClose: ImageView = changeEmailDialogView!!.findViewById(R.id.img_close)
//        val btnSave: Button = changeEmailDialogView!!.findViewById(R.id.btn_save)
//
//        customName.hint = context.getString(R.string.new_email)
//        btnSave.text = context.getString(R.string.change)
//        changeEmailDialogView?.show()
//
//        imgClose.setOnClickListener {
//            changeEmailDialogView?.dismiss()
//        }
//
//        btnSave.setOnClickListener {
//
//            if (customName.text.toString().isNotEmpty()) {
//                GlobalScope.launch(Dispatchers.Main) {
//                    withContext(Dispatchers.IO) {
//                        appServerResult =
//                            IReflexionsApp.awsAmplifyCognitoApi.changeEmail(customName.text.toString())
//                    }
//
//                    if (appServerResult.statusCode == StatusCodeConstants.RESPONSE_SUCCESSFULLY_200) {
//                        Toast.makeText(
//                            context,
//                            (context as MainActivity).getString(R.string.confirmation_code_sent),
//                            Toast.LENGTH_LONG
//                        ).show()
//
//                        postAction(customName.text.toString())
//
//                    } else {
//                        Toast.makeText(
//                            context,
//                            (context as MainActivity).getString(R.string.confirmation_code_error),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//                changeEmailDialogView!!.dismiss()
//            } else {
//                Toast.makeText(
//                    context,
//                    (context as MainActivity).getString(R.string.email_empty_error),
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    }

    fun showPopupOkCancelDialog(
        context: Context,
        header: String,
        message: String,
        okClickAction: () -> Unit
    ) {
        okCancelDialog = AlertDialog.Builder(context)
            .create()

        val factory = LayoutInflater.from(context)
        val dialogView: View = factory.inflate(R.layout.popup_view, null)
        okCancelDialog?.setView(dialogView)
        dialogView.findViewById<TextView>(R.id.headerTextView)?.text = header
        dialogView.findViewById<TextView>(R.id.messageTextView)?.text = message
        dialogView.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            okCancelDialog?.dismiss()
        }
        dialogView.findViewById<Button>(R.id.okButton).setOnClickListener {
            okClickAction()
            okCancelDialog?.dismiss()
        }
        okCancelDialog?.show()
    }

//    fun showConnectBiofeedbackDialog(
//        context: Context,
//        header: String,
//        message: String,
//        isCanceledOnClickOutside: Boolean,
//        okClickAction: () -> Unit,
//        cancel: () -> Unit
//    ) {
//        connectBiofeedbackDialog = AlertDialog.Builder(context)
//            .create()
//
//        val factory = LayoutInflater.from(context)
//        val dialogView: View = factory.inflate(R.layout.connect_sensor_dialog, null)
//        connectBiofeedbackDialog?.setView(dialogView)
//        dialogView.findViewById<TextView>(R.id.headerTextView)?.text = header
//        dialogView.findViewById<TextView>(R.id.messageTextView)?.text = message
//        dialogView.findViewById<Button>(R.id.cancelButton).setOnClickListener {
//            cancel()
//            connectBiofeedbackDialog?.dismiss()
//        }
//        dialogView.findViewById<Button>(R.id.okButton).setOnClickListener {
//            okClickAction()
//            connectBiofeedbackDialog?.dismiss()
//        }
//        connectBiofeedbackDialog?.setOnCancelListener { cancel() }
//        connectBiofeedbackDialog?.setCanceledOnTouchOutside(isCanceledOnClickOutside)
//        connectBiofeedbackDialog?.show()
//    }
//
//    fun showBaselineDialog(
//        context: Context,
//        header: String,
//        message: String,
//        okClickAction: () -> Unit
//    ) {
//        baselineDialog = AlertDialog.Builder(context)
//            .create()
//
//        baselineDialog?.setCancelable(false)
//        baselineDialog?.setCanceledOnTouchOutside(false)
//
//        val factory = LayoutInflater.from(context)
//        val dialogView: View = factory.inflate(R.layout.connect_sensor_dialog, null)
//        baselineDialog?.setView(dialogView)
//        dialogView.findViewById<TextView>(R.id.headerTextView)?.apply {
//            text = header
//            isAllCaps = true
//        }
//        dialogView.findViewById<TextView>(R.id.messageTextView)?.text = message
//        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
//        cancelButton.visibility = View.INVISIBLE
//
//        val connectBtn = dialogView.findViewById<Button>(R.id.okButton)
//        connectBtn.text = context.getString(R.string.begin)
//
//        cancelButton.setOnClickListener {
//            baselineDialog?.dismiss()
//        }
//        connectBtn.setOnClickListener {
//            okClickAction()
//            baselineDialog?.dismiss()
//        }
//        baselineDialog?.show()
//    }
//
//    fun showFrameOverlay(activity: MainActivity) {
//
//        frameOverlayDialog = Dialog(activity)
//        var mCountDownTimer: CountDownTimer? = null
//
//        val window: Window = frameOverlayDialog?.window!!
//
//        frameOverlayDialog?.setCancelable(false)
//        frameOverlayDialog?.setCanceledOnTouchOutside(false)
//
//        frameOverlayDialog?.setOnKeyListener(android.content.DialogInterface.OnKeyListener { _, keyCode, _ ->
//            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                frameOverlayDialog?.dismiss()
//                activity.onBackPressed()
//                return@OnKeyListener true
//            }
//            false
//        })
//
//        frameOverlayDialog?.setContentView(R.layout.baseline_view)
//
//        val width = (activity.resources.displayMetrics.widthPixels * 0.90).toInt()
//        window.attributes.width = width
//
//        frameOverlayDialog?.show()
//        val readyToGoText = frameOverlayDialog?.findViewById<TextView>(R.id.timeTxt)
//        if (mCountDownTimer == null) {
//            mCountDownTimer = object : CountDownTimer(32000, 1000) {
//                override fun onTick(millisUntilFinished: Long) {
//                    val sec = (millisUntilFinished / 1000).toInt() - 1
//                    if (sec >= 0) {
//                        readyToGoText?.text = sec.toString()
//                    }
//                }
//
//                override fun onFinish() {
//                    readyToGoText?.visibility = View.VISIBLE
//                    frameOverlayDialog?.dismiss()
//                }
//            }
//        }
//        mCountDownTimer.start()
//    }
//
//    fun showBiofeedbackOverlay(activity: MainActivity) {
//
//        biofeedbackOverlayDialog = Dialog(activity)
//
//        val window: Window = biofeedbackOverlayDialog?.window!!
//
//        biofeedbackOverlayDialog?.setCancelable(false)
//        biofeedbackOverlayDialog?.setCanceledOnTouchOutside(false)
//        biofeedbackOverlayDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        biofeedbackOverlayDialog?.setOnKeyListener(android.content.DialogInterface.OnKeyListener { _, keyCode, _ ->
//            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                biofeedbackOverlayDialog?.dismiss()
//                activity.onBackPressed()
//                return@OnKeyListener true
//            }
//            false
//        })
//
//            biofeedbackOverlayDialog?.setContentView(R.layout.dialog_overlay)
//        biofeedbackOverlayDialog?.show()
//    }
//
//    @SuppressLint("SetTextI18n")
//    fun showRemoveItemDialog(
//        context: Context,
//        name: String,
//        okClickAction: () -> Unit
//    ) {
//        removeItemDialog = AlertDialog.Builder(context)
//            .create()
//
//        val factory = LayoutInflater.from(context)
//        val dialogView: View = factory.inflate(R.layout.popup_view, null)
//        removeItemDialog?.setView(dialogView)
//        dialogView.findViewById<TextView>(R.id.headerTextView)?.text =
//            context.getString(R.string.delete_item)
//        dialogView.findViewById<TextView>(R.id.messageTextView)?.text =
//            String.format(context.getString(R.string.confirm_remove_item), name)
//        dialogView.findViewById<Button>(R.id.cancelButton).setOnClickListener {
//            removeItemDialog?.dismiss()
//        }
//        dialogView.findViewById<Button>(R.id.okButton).setOnClickListener {
//            okClickAction()
//            removeItemDialog?.dismiss()
//        }
//        removeItemDialog?.show()
//    }
//
//    fun showAddItemDialog(
//        context: Context,
//        okClickAction: (customName: String) -> Unit
//    ) {
//        addItemDialogView = Dialog(context)
//        addItemDialogView?.setContentView(R.layout.custom_happening)
//
//        val window: Window = addItemDialogView?.window!!
//
//        val width = (context.resources.displayMetrics.widthPixels * 0.80).toInt()
//        val height = (context.resources.displayMetrics.heightPixels * 0.30).toInt()
//        window.setLayout(width, height)
//
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        val customName: EditText = addItemDialogView!!.findViewById(R.id.custom_name_editText)
//        val imgClose: ImageView = addItemDialogView!!.findViewById(R.id.img_close)
//        val btnSave: Button = addItemDialogView!!.findViewById(R.id.btn_save)
//
//        imgClose.setOnClickListener {
//            addItemDialogView?.dismiss()
//        }
//        btnSave.setOnClickListener {
//            okClickAction(customName.text.toString())
//            addItemDialogView?.dismiss()
//        }
//        addItemDialogView?.show()
//    }
//
//    fun showTurnOnBluetoothDialog(
//        context: Context,
//        isCanceledOnClickOutside: Boolean,
//        okClickAction: () -> Unit,
//        cancelClickAction: () -> Unit
//    ) {
//        turnOnBluetoothDialog = AlertDialog.Builder(context)
//            .setTitle(context.getString(R.string.title_bluetooth_off))
//            .setMessage(R.string.question_bluetooth_turn_on)
//            .setPositiveButton(R.string.yes) { dialogInterface, _ ->
//                okClickAction()
//                dialogInterface.dismiss()
//            }
//            .setNegativeButton(R.string.no) { _, _ ->
//                cancelClickAction()
//            }
//            .setOnCancelListener {
//                cancelClickAction()
//            }
//            .create()
//        turnOnBluetoothDialog?.setCanceledOnTouchOutside(isCanceledOnClickOutside)
//        turnOnBluetoothDialog?.show()
//    }
//
//    fun showDisconnectTpsDeviceDialog(
//        context: Context,
//        onCancelAction: () -> Unit = {},
//        okClickAction: () -> Unit
//    ) {
//        disconnectTpsDeviceDialog = AlertDialog.Builder(context)
//            .setTitle(context.getString(R.string.title_disconnect_tps))
//            .setMessage(context.getString(R.string.question_disconnect_tps))
//            .setPositiveButton(R.string.yes) { _, _ ->
//                okClickAction()
//            }
//            .setNegativeButton(R.string.action_cancel) { _, _ ->
//                onCancelAction()
//            }
//            .create()
//        disconnectTpsDeviceDialog?.show()
//    }
//
//    fun showOfflineLoginDialog(
//        context: Context,
//        message: String,
//        positiveButtonClickAction: () -> Unit,
//        onCancelAction: () -> Unit
//    ) {
//        offlineLoginDialog = AlertDialog.Builder(context)
//            .create()
//
//        LayoutInflater.from(context).inflate(R.layout.popup_view, null).apply {
//            offlineLoginDialog?.setView(this)
//            findViewById<TextView>(R.id.headerTextView)?.visibility = View.GONE
//            findViewById<View>(R.id.divider)?.visibility = View.GONE
//            findViewById<TextView>(R.id.messageTextView)?.text = message
//            val cancelButton = findViewById<Button>(R.id.cancelButton)
//
//            cancelButton.visibility = View.INVISIBLE
//            cancelButton.setOnClickListener {
//                onCancelAction.invoke()
//            }
//            findViewById<Button>(R.id.okButton).setOnClickListener {
//                positiveButtonClickAction.invoke()
//                offlineLoginDialog?.dismiss()
//            }
//        }
//
//        offlineLoginDialog?.show()
//    }
//
//    fun showSelectSensorDialog(
//        context: Context,
//        sensorNameList: Array<String?>,
//        isCanceledOnClickOutside: Boolean,
//        onSensorSelected: (selectedSensorName: String) -> Unit,
//        onCancelAction: () -> Unit
//    ) {
//        selectSensorDialog = AlertDialog.Builder(context)
//            .setTitle(R.string.title_select_tps_dialog)
//            .setItems(sensorNameList) { _, which ->
//                onSensorSelected.invoke(sensorNameList[which]!!)
//            }
//            .setNegativeButton(R.string.action_cancel) { _, _ ->
//                onCancelAction()
//            }
//            .create()
//        selectSensorDialog?.setCanceledOnTouchOutside(isCanceledOnClickOutside)
//        selectSensorDialog?.show()
//    }
//
//    fun showSendFeedbackDialog(context: Context, type: Int, postAction: (text: String) -> Unit) {
//
//        sendFeedbackDialog = Dialog(context)
//        sendFeedbackDialog?.setContentView(R.layout.custom_happening)
//
//        val window: Window = sendFeedbackDialog?.window!!
//
//        val width = (context.resources.displayMetrics.widthPixels * 0.80).toInt()
//        val height = (context.resources.displayMetrics.heightPixels * 0.40).toInt()
//        window.setLayout(width, height)
//
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        val customName: EditText = sendFeedbackDialog!!.findViewById(R.id.custom_name_editText)
//        val imgClose: ImageView = sendFeedbackDialog!!.findViewById(R.id.img_close)
//        val btnSend: Button = sendFeedbackDialog!!.findViewById(R.id.btn_save)
//
//        customName.height = (context.resources.displayMetrics.heightPixels * 0.20).toInt()
//
//        if (type == 0)
//            customName.hint = context.getString(R.string.hint_feedback)
//        else customName.hint = context.getString(R.string.hint_report_issue)
//        btnSend.text = context.getString(R.string.send)
//        sendFeedbackDialog?.show()
//
//        imgClose.setOnClickListener {
//            sendFeedbackDialog?.dismiss()
//        }
//
//        btnSend.setOnClickListener {
//            postAction.invoke(customName.text.toString())
//            sendFeedbackDialog?.dismiss()
//        }
//    }
//
//    fun dismissAllDialogs() {
//        removeItemDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        messageDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        changeEmailDialogView?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        okCancelDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        connectBiofeedbackDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        baselineDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        frameOverlayDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        biofeedbackOverlayDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        addItemDialogView?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        turnOnBluetoothDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        disconnectTpsDeviceDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        offlineLoginDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        selectSensorDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//        sendFeedbackDialog?.apply {
//            if (this.isShowing)
//                this.dismiss()
//        }
//    }
}
