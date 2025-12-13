package com.example.artisana.core.viewmodels

import androidx.lifecycle.ViewModel
import com.example.artisana.core.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _isUserLoggedIn = MutableStateFlow(authRepository.isUserLoggedIn())
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        _isUserLoggedIn.value = auth.currentUser != null
    }

    init {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun signOut() {
        authRepository.signOut()
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}