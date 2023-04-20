package com.lifespandh.ireflexions.utils.phone

import android.net.Uri
import android.telephony.PhoneNumberUtils

fun getMessageUri(phoneNumber: String): Uri {
    val normalized = PhoneNumberUtils.normalizeNumber(phoneNumber)
    return Uri.parse("smsto: $normalized")
}