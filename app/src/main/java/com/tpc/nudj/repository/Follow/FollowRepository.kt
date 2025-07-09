package com.tpc.nudj.repository.Follow

import com.tpc.nudj.model.Follow

interface FollowRepository {
    suspend fun FollowClub(userId: String, clubId: String): Boolean
    suspend fun UnfollowClub(userId: String, clubId: String): Boolean
    suspend fun FetchFollowingClubs(userId: String): List<Follow>
}