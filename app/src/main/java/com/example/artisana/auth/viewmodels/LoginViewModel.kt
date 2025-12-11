package com.example.artisana.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artisana.core.repositories.AuthError
import com.example.artisana.core.repositories.AuthRepository
import com.example.artisana.core.repositories.AuthResult
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

    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = "") }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = "") }
    }

    fun onLoginClick() {
        if (!validateForm()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }

            val result = authRepository.signIn(_uiState.value.email, _uiState.value.password)

            _uiState.update {
                when (result) {
                    is AuthResult.Success -> it.copy(isLoading = false, isLoginSuccessful = true)
                    is AuthResult.Error -> {
                        val (emailError, passwordError) = when (result.error) {
                            is AuthError.UserNotFound -> Pair("Aucun compte n'est associé à cette adresse e-mail.", "")
                            is AuthError.WrongPassword -> Pair("", "Mot de passe incorrect.")
                            else -> Pair("", "") // For other errors, you might want a generic message
                        }
                        it.copy(
                            isLoading = false,
                            emailError = emailError,
                            passwordError = passwordError,
                            errorMessage = if (emailError.isEmpty() && passwordError.isEmpty()) "Une erreur inattendue est survenue." else ""
                        )
                    }
                }
            }
        }
    }
    private fun validateForm(): Boolean {
        var isValid = true
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isBlank()) {
            _uiState.update { it.copy(emailError = "Veuillez entrer votre email") }
            isValid = false
        }

        if (password.isBlank()) {
            _uiState.update { it.copy(passwordError = "Veuillez entrer votre mot de passe") }
            isValid = false
        }

        return isValid
    }

    fun resetLoginState() {
        _uiState.value = LoginUiState()
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = "") }
    }
}
