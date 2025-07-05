package com.tpc.nudj.utils

import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.NormalUser
import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.model.enums.Gender


object FirestoreUtils {

    fun toMap(clubUser: ClubUser): Map<String, Any?> {
        return mapOf(
            "clubId" to clubUser.clubId,
            "clubName" to clubUser.clubName,
            "description" to clubUser.description,
            "achievements" to clubUser.achievements,
            "clubEmail" to clubUser.clubEmail,
            "clubLogo" to clubUser.clubLogo,
            "clubCategory" to clubUser.clubCategory.name, // Store enum as string
            "additionalDetails" to clubUser.additionalDetails
        )
    }

    fun toMap(normalUser: NormalUser): Map<String, Any?> {
        return mapOf(
            "userid" to normalUser.userid,
            "name" to normalUser.name,
            "firstName" to normalUser.firstName,
            "lastname" to normalUser.lastname,
            "email" to normalUser.email,
            "gender" to normalUser.gender.name, // Store enum as string
            "branch" to normalUser.branch.name, // Store enum as string
            "batch" to normalUser.batch,
            "profilePictureUrl" to normalUser.profilePictureUrl,
            "bio" to normalUser.bio
        )
    }


    fun toClubUser(data: Map<String, Any?>): ClubUser {
        return ClubUser(
            clubId = (data["clubId"] as? String) ?: "",
            clubName = (data["clubName"] as? String) ?: "",
            description = (data["description"] as? String) ?: "",
            achievements = (data["achievements"] as? String) ?: "",
            clubEmail = (data["clubEmail"] as? String) ?: "",
            clubLogo = data["clubLogo"] as? String,
            clubCategory = try {
                ClubCategory.valueOf(data["clubCategory"] as String)
            } catch (e: Exception) {
                ClubCategory.MISCELLANEOUS // Default value
            },
            additionalDetails = (data["additionalDetails"] as? String) ?: ""
        )
    }


    fun toNormalUser(data: Map<String, Any?>): NormalUser {
        return NormalUser(
            userid = (data["userid"] as? String) ?: "",
            name = (data["name"] as? String) ?: "",
            firstName = (data["firstName"] as? String) ?: "",
            lastname = (data["lastname"] as? String) ?: "",
            email = (data["email"] as? String) ?: "",
            gender = try {
                Gender.valueOf(data["gender"] as String)
            } catch (e: Exception) {
                Gender.PREFER_NOT_TO_DISCLOSE // Default value
            },
            branch = try {
                Branch.valueOf(data["branch"] as String)
            } catch (e: Exception) {
                Branch.CSE // Default value
            },
            batch = (data["batch"] as? Long)?.toInt() ?: 2024,
            profilePictureUrl = data["profilePictureUrl"] as? String,
            bio = (data["bio"] as? String) ?: ""
        )
    }

    fun ClubUser.toMap(): Map<String, Any?> = FirestoreUtils.toMap(this)


    fun NormalUser.toMap(): Map<String, Any?> = FirestoreUtils.toMap(this)
}
