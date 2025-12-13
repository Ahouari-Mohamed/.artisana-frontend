package com.example.artisana.features.viewmodels

import androidx.lifecycle.ViewModel
import com.example.artisana.core.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val user: FirebaseUser? = null,
    val isLoggedOut: Boolean = false
)

class ProfileViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        _uiState.value = ProfileUiState(user = authRepository.getCurrentUser())
    }

    fun signOut() {
        authRepository.signOut()
        _uiState.value = _uiState.value.copy(isLoggedOut = true)
    }
}
