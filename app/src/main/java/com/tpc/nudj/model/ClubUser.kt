package com.tpc.nudj.model

import com.tpc.nudj.model.enums.ClubCategory

data class ClubUser(
    val clubId :String = "",
    val clubName :String = "",
    val description: String = "",
    val achievements: String = "",
    val clubEmail:String = "",
    val clubLogo :String? = null,
    val clubCategory : ClubCategory = ClubCategory.MISCELLANEOUS,
    val additionalDetails: String = "",
){
    constructor(): this("","","","","","", ClubCategory.MISCELLANEOUS,"")
    fun toMapClub(): Map<out Any?, Any?>{
        return mapOf(
            "clubId" to clubId,
            "clubName" to clubName,
            "description" to description,
            "achievements" to achievements,
            "clubEmail" to clubEmail,
            "clubLogo" to clubLogo,
            "clubCategory" to clubCategory.name,
            "additionalDetails" to additionalDetails
        )
    }
}