package com.example.artisana.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val errorMessage: String = ""
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = "") }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = "") }
    }

    fun onLoginClick() {
        if (!validateInputs()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }

            try {
                // Implement actual authentication logic here
                // Example: authRepository.login(uiState.value.email, uiState.value.password)

                // Simulate API call
                kotlinx.coroutines.delay(1000)

                // On success
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Échec de la connexion. Veuillez réessayer."
                    )
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val currentState = _uiState.value
        var isValid = true

        if (currentState.email.isBlank()) {
            _uiState.update { it.copy(emailError = "Veuillez entrer votre email") }
            isValid = false
        } else if (!isValidEmail(currentState.email)) {
            _uiState.update { it.copy(emailError = "Format d'email invalide") }
            isValid = false
        }

        if (currentState.password.isBlank()) {
            _uiState.update { it.copy(passwordError = "Veuillez entrer votre mot de passe") }
            isValid = false
        }

        return isValid
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun resetLoginState() {
        _uiState.update { it.copy(isLoginSuccessful = false) }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = "") }
    }
}