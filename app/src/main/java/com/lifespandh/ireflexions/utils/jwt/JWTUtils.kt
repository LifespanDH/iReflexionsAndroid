package com.lifespandh.ireflexions.utils.jwt

import com.auth0.android.jwt.JWT

fun String.isJWTExpired(): Boolean {
    val jwt = JWT(this)
    val expired = jwt.isExpired(0)
    return expired
}