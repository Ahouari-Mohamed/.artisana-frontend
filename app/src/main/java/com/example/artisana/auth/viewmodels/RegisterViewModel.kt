package com.example.artisana.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artisana.core.repositories.AuthError
import com.example.artisana.core.repositories.AuthRepository
import com.example.artisana.core.repositories.AuthResult
import com.example.artisana.core.repositories.EmailCheckResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val prenom: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val termsAccepted: Boolean = false,
    val nameError: String = "",
    val prenomError: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val isLoading: Boolean = false,
    val registrationStep: RegistrationStep = RegistrationStep.INFO,
    val isRegistrationSuccessful: Boolean = false,
    val errorMessage: String = ""
)

class RegisterViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = "") }
    }

    fun onPrenomChange(prenom: String) {
        _uiState.update { it.copy(prenom = prenom, prenomError = "") }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = "") }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = "") }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = "") }
    }

    fun onTermsAcceptedChange(accepted: Boolean) {
        _uiState.update { it.copy(termsAccepted = accepted) }
    }

    fun onNextStepClick() {
        if (!validateInfoStep()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "", emailError = "") }

            val email = _uiState.value.email.trim()

            when (val result = authRepository.checkEmail(email)) {
                is EmailCheckResult.Exists -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            emailError = "Un compte existe déjà avec cette adresse e-mail."
                        )
                    }
                }
                is EmailCheckResult.NotExist -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registrationStep = RegistrationStep.PASSWORD
                        )
                    }
                }
                is EmailCheckResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Erreur lors de la vérification de l'email."
                        )
                    }
                }
            }
        }
    }

    fun onRegisterClick() {
        if (!validatePasswordStep()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "", emailError = "", passwordError = "") }

            val state = _uiState.value

            val result = authRepository.signUp(
                name = state.name,
                prenom = state.prenom,
                email = state.email,
                password = state.password
            )

            _uiState.update { current ->
                when (result) {
                    is AuthResult.Success -> current.copy(
                        isLoading = false,
                        isRegistrationSuccessful = true
                    )
                    is AuthResult.Error -> {
                        val emailError =
                            if (result.error is AuthError.UserCollision)
                                "Un compte existe déjà avec cette adresse e-mail."
                            else if (result.error is AuthError.InvalidEmailFormat)
                                "Format d'email invalide."
                            else ""

                        val passwordError =
                            if (result.error is AuthError.WeakPassword)
                                "Le mot de passe est trop faible."
                            else ""

                        val generic =
                            if (emailError.isEmpty() && passwordError.isEmpty())
                                "Une erreur inattendue est survenue."
                            else ""

                        current.copy(
                            isLoading = false,
                            emailError = emailError,
                            passwordError = passwordError,
                            errorMessage = generic,

                            registrationStep = if (result.error is AuthError.UserCollision)
                                RegistrationStep.INFO
                            else
                                current.registrationStep
                        )
                    }
                }
            }
        }
    }


    private fun validateInfoStep(): Boolean {
        val currentState = _uiState.value
        var isValid = true

        if (currentState.name.isBlank()) {
            _uiState.update { it.copy(nameError = "Entrez votre nom") }
            isValid = false
        }

        if (currentState.prenom.isBlank()) {
            _uiState.update { it.copy(prenomError = "Entrez votre prénom") }
            isValid = false
        }

        if (currentState.email.isBlank()) {
            _uiState.update { it.copy(emailError = "Veuillez entrer votre email") }
            isValid = false
        } else if (!isValidEmail(currentState.email)) {
            _uiState.update { it.copy(emailError = "Format d'email invalide") }
            isValid = false
        }

        if (!currentState.termsAccepted) {
            isValid = false
        }

        return isValid
    }

    private fun validatePasswordStep(): Boolean {
        val currentState = _uiState.value
        var isValid = true

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

    fun resetRegistrationState() {
        _uiState.update { it.copy(isRegistrationSuccessful = false) }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = "") }
    }

    fun goBackToInfoStep() {
        _uiState.update { it.copy(registrationStep = RegistrationStep.INFO) }
    }
}