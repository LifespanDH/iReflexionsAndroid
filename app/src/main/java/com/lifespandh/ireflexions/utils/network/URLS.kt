package com.lifespandh.ireflexions.utils

import com.lifespandh.ireflexions.BuildConfig

const val BASE_URL = BuildConfig.BASE_URL

const val USERS = "users/"
const val ON_BOARDING = "onboarding/"
const val IREF_USERS = "iref_users"

const val REFRESH_TOKEN = BASE_URL + USERS + "api/token/refresh/"

const val LOGIN_USER = BASE_URL + USERS + "authenticate/"
const val LOGIN_CUSTOM_USER = BASE_URL + USERS + "authenticate_custom/"
const val REGISTER_USER = BASE_URL + USERS + "register/"

const val GET_SURVEY_QUESTIONS = BASE_URL + ON_BOARDING + "get_survey_questions/"
const val SAVE_SURVEY_QUESTIONS = BASE_URL + ON_BOARDING + "save_survey_responses/"

const val GET_EXERCISES = BASE_URL + IREF_USERS + "get_exercises/"