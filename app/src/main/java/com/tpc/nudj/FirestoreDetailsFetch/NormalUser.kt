package com.tpc.nudj.FirestoreDetailsFetch

data class NormalUser(
    val userid :String = "",
    val name :String,
    val firstName :String,
    val lastname :String,
    val email :String,
    val branch :String,
    val batch :String,
    val profilePictureUrl :String,
    val bio :String,

)
