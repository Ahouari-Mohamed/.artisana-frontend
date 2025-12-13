package com.example.artisana.core.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

sealed class AuthError {
    object UserNotFound : AuthError()
    object WrongPassword : AuthError()
    object UserCollision : AuthError()
    object WeakPassword : AuthError()
    object InvalidEmailFormat : AuthError()
    data class Unknown(val message: String?) : AuthError()
}

sealed class AuthResult {
    data class Success(val user: FirebaseUser?) : AuthResult()
    data class Error(val error: AuthError) : AuthResult()
}

sealed class EmailCheckResult {
    object Exists : EmailCheckResult()
    object NotExist : EmailCheckResult()
    data class Error(val error: AuthError) : EmailCheckResult()
}


class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    suspend fun checkEmail(email: String): EmailCheckResult {
        return try {
            val result = auth.fetchSignInMethodsForEmail(email.trim()).await()
            if (result.signInMethods?.isNotEmpty() == true) {
                EmailCheckResult.Exists
            } else {
                EmailCheckResult.NotExist
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "checkEmail error: ${e.message}")
            EmailCheckResult.Error(AuthError.Unknown(e.message))
        }
    }

    suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email.trim(), password).await()
            AuthResult.Success(result.user)
        } catch (e: Exception) {
            val error = when (e) {
                is FirebaseAuthInvalidUserException -> AuthError.UserNotFound
                is FirebaseAuthInvalidCredentialsException -> AuthError.WrongPassword
                else -> AuthError.Unknown(e.message)
            }
            AuthResult.Error(error)
        }
    }

    suspend fun signUp(
        name: String,
        prenom: String,
        email: String,
        password: String
    ): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.sendEmailVerification()?.await()
            val profileUpdates = userProfileChangeRequest {
                displayName = "$name $prenom"
            }
            result.user?.updateProfile(profileUpdates)?.await()
            AuthResult.Success(result.user)
        } catch (e: Exception) {
            Log.w("AuthRepository", "signUp with email failed", e)
            val error = when (e) {
                is FirebaseAuthUserCollisionException -> AuthError.UserCollision
                is FirebaseAuthWeakPasswordException -> AuthError.WeakPassword
                is FirebaseAuthInvalidCredentialsException -> AuthError.InvalidEmailFormat
                else -> AuthError.Unknown(e.message)
            }
            AuthResult.Error(error)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

}
