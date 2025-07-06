package com.tpc.nudj.FirestoreDetailsFetch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userType = MutableStateFlow<UserType>(UserType.Loading)
    val userTypeState: StateFlow<UserType> = _userType

    fun checkUserType() {
        viewModelScope.launch {
            userRepository.checkUserTypeAndNavigate(
                onNormalUser = { _userType.value = UserType.NormalUser },
                onClubUser = { _userType.value = UserType.ClubUser },
                onUserNotFound = { _userType.value = UserType.NotFound }
            )
        }
    }

    sealed class UserType {
        object Loading : UserType()
        object NormalUser : UserType()
        object ClubUser : UserType()
        object NotFound : UserType()
    }
}
