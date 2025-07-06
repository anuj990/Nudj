package com.tpc.nudj.model

import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.model.enums.Gender

data class NormalUser(
    val userid :String = "",
    val name :String = "",
    val firstName :String = "",
    val lastname :String = "",
    val email :String = "",
    val gender: Gender = Gender.PREFER_NOT_TO_DISCLOSE,
    val branch : Branch = Branch.CSE,
    val batch : Int = 2024,
    val profilePictureUrl :String? = null,
    val bio :String = "",
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        Gender.PREFER_NOT_TO_DISCLOSE,
        Branch.CSE,
        2024,
        "",
        ""
    )
    fun toMapNormal(): Map<out Any?, Any?> {
        return mapOf(
            "userid" to userid,
            "name" to name,
            "firstname" to firstName,
            "lastname" to lastname,
            "email" to email,
            "gender" to gender.name,
            "branch" to  branch.name,
            "batch" to batch,
            "profilePictureUrl" to profilePictureUrl,
            "bio" to bio

        )
    }
}