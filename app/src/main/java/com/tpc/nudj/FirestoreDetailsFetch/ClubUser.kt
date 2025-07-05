package com.tpc.nudj.FirestoreDetailsFetch

data class ClubUser(
    val clubid :String = "",
    val clubname :String,
    val clubemail:String,
    val clublogo :String,
    val contactnumber :String,
    val pointofcontactname:String,
    val pointofcontactdetail:String,
    val clubcategory :String,
)
