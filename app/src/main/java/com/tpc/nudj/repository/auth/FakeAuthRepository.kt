package com.tpc.nudj.repository.auth

import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.delay

/**
 * Fake implementation of AuthRepository for testing and development.
 */
class FakeAuthRepository : AuthRepository {
    private val userFlow = MutableStateFlow<User?>(null)

    override fun getCurrentUser(): Flow<User?> = userFlow

    override fun isUserAuthenticated(): Boolean = userFlow.value != null

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        delay(500)
        if (email == "test@example.com" && password == "password") {
            val user = User(
                uid = "123",
                email = email,
                displayName = "Test User",
                isEmailVerified = true,
                photoUrl = "https://example.com/photo.jpg"
            )
            userFlow.value = user
            emit(AuthResult.Success(user))
        } else {
            emit(AuthResult.Error("Invalid credentials"))
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        delay(500)
        if (idToken == "valid_token") {
            val user = User(
                uid = "456",
                email = "google@example.com",
                displayName = "Google User",
                isEmailVerified = true,
                photoUrl = "https://example.com/google_photo.jpg"
            )
            userFlow.value = user
            emit(AuthResult.Success(user))
        } else {
            emit(AuthResult.Error("Invalid Google token"))
        }
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String, displayName: String): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        delay(500)
        if (email.contains("@")) {
            val user = User(
                uid = "789",
                email = email,
                displayName = displayName,
                isEmailVerified = false,
                photoUrl = ""
            )
            userFlow.value = user
            emit(AuthResult.VerificationNeeded(email))
        } else {
            emit(AuthResult.Error("Invalid email format"))
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        delay(500)
        if (email == "test@example.com") {
            emit(AuthResult.Success(User(email = email)))
        } else {
            emit(AuthResult.Error("Email not found"))
        }
    }

    override suspend fun sendEmailVerification(): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        delay(500)
        val user = userFlow.value
        if (user != null && !user.isEmailVerified) {
            userFlow.update { it?.copy(isEmailVerified = true) }
            emit(AuthResult.Success(userFlow.value!!))
        } else {
            emit(AuthResult.Error("No user or already verified"))
        }
    }

    override suspend fun signOut() {
        userFlow.value = null
    }
}

