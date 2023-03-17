package com.lifespandh.ireflexions.utils

import com.lifespandh.ireflexions.BuildConfig

const val BASE_URL = BuildConfig.BASE_URL

const val USERS = "users/"

const val LOGIN_USER = BASE_URL + USERS + "authenticate"
const val REGISTER_USER = BASE_URL + USERS + "api/token/refresh/"