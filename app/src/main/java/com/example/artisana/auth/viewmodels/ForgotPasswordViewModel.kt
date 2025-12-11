package com.example.artisana.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
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

    private val firebaseAuth = FirebaseAuth.getInstance()

    /** Update email typed by user */
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = "") }
    }

    /** Optionally update password and confirm password if needed */
    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = "") }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = "") }
    }

    /** Call this when user clicks "Reset Password" */
    fun onResetPasswordClick() {
        val currentState = _uiState.value

        // Validate email first
        if (currentState.email.isBlank()) {
            _uiState.update { it.copy(emailError = "Veuillez entrer votre email") }
            return
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            _uiState.update { it.copy(emailError = "Format d'email invalide") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "", isResetSuccessful = false) }

            firebaseAuth.sendPasswordResetEmail(currentState.email)
                .addOnCompleteListener { task ->
                    _uiState.update { it.copy(isLoading = false) }

                    if (task.isSuccessful) {
                        _uiState.update { it.copy(isResetSuccessful = true) }
                    } else {
                        _uiState.update {
                            it.copy(
                                errorMessage = task.exception?.localizedMessage
                                    ?: "Échec de la réinitialisation."
                            )
                        }
                    }
                }
        }
    }

    fun resetPasswordState() {
        _uiState.update { it.copy(isResetSuccessful = false) }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = "") }
    }

    fun changePassword(oldPassword: String, newPassword: String, onResult: (Boolean, String?) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email ?: return

        // Re-authenticate
        val credential = EmailAuthProvider.getCredential(email, oldPassword)
        user.reauthenticate(credential).addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
                // Update password
                user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        onResult(true, null)
                    } else {
                        onResult(false, updateTask.exception?.localizedMessage)
                    }
                }
            } else {
                onResult(false, "Ancien mot de passe incorrect")
            }
        }
    }

}
