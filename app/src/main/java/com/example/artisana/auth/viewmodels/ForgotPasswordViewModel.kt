package com.example.artisana.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ForgotPasswordUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val isLoading: Boolean = false,
    val isResetSuccessful: Boolean = false,
    val errorMessage: String = ""
)

class ForgotPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = "") }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = "") }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = "") }
    }

    fun onResetPasswordClick() {
        if (!validateInputs()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }

            try {
                // Implement actual password reset logic here
                // Example: authRepository.resetPassword(email, password)

                // Simulate API call
                kotlinx.coroutines.delay(1000)

                // On success
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isResetSuccessful = true
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Échec de la réinitialisation. Veuillez réessayer."
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
            _uiState.update { it.copy(passwordError = "Veuillez entrer un mot de passe") }
            isValid = false
        } else if (!isValidPassword(currentState.password)) {
            _uiState.update {
                it.copy(passwordError = "8 caractères min, lettres, chiffres et symboles")
            }
            isValid = false
        }

        if (currentState.confirmPassword.isBlank()) {
            _uiState.update { it.copy(confirmPasswordError = "Confirmez votre mot de passe") }
            isValid = false
        } else if (currentState.password != currentState.confirmPassword) {
            _uiState.update {
                it.copy(confirmPasswordError = "Les mots de passe ne correspondent pas")
            }
            isValid = false
        }

        return isValid
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val hasMinLength = password.length >= 8
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasMinLength && hasLetter && hasDigit && hasSpecialChar
    }

    fun resetPasswordState() {
        _uiState.update { it.copy(isResetSuccessful = false) }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = "") }
    }
}