package com.lifespandh.ireflexions.models

data class Membership(
    val name: String
)

enum class MembershipLevel(val level: Int) {
    NoSubscription(1),
    Basic(2),
    Plus(3),
    Premium(4)
}