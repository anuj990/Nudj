package com.tpc.nudj.repository.follow

import com.tpc.nudj.model.Follow

interface FollowRepository {
    suspend fun followClub(userId: String, clubId: String): Boolean
    suspend fun unfollowClub(userId: String, clubId: String): Boolean
    suspend fun fetchFollowingClubs(userId: String): List<Follow>
}