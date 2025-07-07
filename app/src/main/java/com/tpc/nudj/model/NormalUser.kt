package com.tpc.nudj.model

import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.model.enums.Gender

data class NormalUser(
    val userid :String = "",
    val firstName :String = "",
    val lastname :String = "",
    val email :String = "",
    val gender: Gender = Gender.PREFER_NOT_TO_DISCLOSE,
    val branch : Branch = Branch.CSE,
    val batch : Int = 2024,
    val profilePictureUrl :String? = null,
    val bio :String = "",
)