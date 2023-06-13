package com.lifespandh.ireflexions.utils.network

import com.lifespandh.ireflexions.BuildConfig

const val BASE_URL = BuildConfig.BASE_URL

const val USERS = "users/"
const val ON_BOARDING = "onboarding/"
const val IREF_USERS = "iref_users/"

const val REFRESH_TOKEN = BASE_URL + USERS + "api/token/refresh/"

const val LOGIN_USER = BASE_URL + USERS + "authenticate/"
const val LOGIN_CUSTOM_USER = BASE_URL + USERS + "authenticate_custom/"
const val REGISTER_USER = BASE_URL + USERS + "register/"

const val GET_SURVEY_QUESTIONS = BASE_URL + ON_BOARDING + "get_survey_questions/"
const val SAVE_SURVEY_QUESTIONS = BASE_URL + ON_BOARDING + "save_survey_responses/"

const val GET_EXERCISES = BASE_URL + IREF_USERS + "get_exercises/"
const val GET_PROGRAMS = BASE_URL + IREF_USERS + "get_programs/"
const val GET_REGISTERED_PROGRAMS = BASE_URL + IREF_USERS + "get_user_registered_programs/"
const val GET_USER_PROGRAM_PROGRESS = BASE_URL + IREF_USERS + "get_user_program_progress/"
const val ADD_USER_TO_PROGRAM = BASE_URL + IREF_USERS + "add_user_to_program/"

const val GET_COURSES = BASE_URL + IREF_USERS + "get_courses/"
const val GET_LESSONS = BASE_URL + IREF_USERS + "get_lessons/"
const val GET_LESSON_QUESTIONS = BASE_URL + IREF_USERS + "get_lesson_questions/"
const val SAVE_PROGRAM_PROGRESS = BASE_URL + IREF_USERS + "save_program_progress/"

const val GET_SUPPORT_CONTACTS = BASE_URL + IREF_USERS + "get_support_contacts/"
const val ADD_SUPPORT_CONTACT = BASE_URL + IREF_USERS + "add_support_contact/"
const val EDIT_SUPPORT_CONTACT = BASE_URL + IREF_USERS + "edit_support_contact/"
const val DELETE_SUPPORT_CONTACT = BASE_URL + IREF_USERS + "delete_support_contact/"
const val GET_JOURNAL_ENTRIES = BASE_URL + IREF_USERS + "get_journal_entries/"
const val GET_CARE_CENTER_EXERCISES = BASE_URL + IREF_USERS + "get_care_center_exercises/"
const val GET_RESOURCE_CONTENT = BASE_URL + IREF_USERS + "get_library_resources/"

// How Am I Today
const val GET_TRAIT_CATEGORIES = BASE_URL + IREF_USERS + "get_trait_categories/"
const val GET_WHATS_HAPPENING = BASE_URL + IREF_USERS + "get_whats_happening/"
const val GET_ENVIRONMENTAL_CONDITIONS = BASE_URL + IREF_USERS + "get_environmental_conditions/"
const val GET_HOW_AM_I_TODAY_DATA = BASE_URL + IREF_USERS + "get_how_am_i_today_data/"