package com.lifespandh.ireflexions.utils.jwt

import com.auth0.android.jwt.JWT
import com.lifespandh.ireflexions.utils.sharedPrefs.JWT_EMAIL
import com.lifespandh.ireflexions.utils.sharedPrefs.JWT_NAME

fun String.isJWTExpired(): Boolean {
    val jwt = JWT(this)
    val expired = jwt.isExpired(0)
    return expired
}

fun String.getEmailFromJWT(): String? {
    val jwt = JWT(this)
    val email = jwt.getClaim(JWT_EMAIL)
    return email.asString()
}

fun String.getNameFromJWT(): String? {
    val jwt = JWT(this)
    val name = jwt.getClaim(JWT_NAME)
    return name.asString()
}