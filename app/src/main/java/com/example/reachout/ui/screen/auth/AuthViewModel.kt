package com.example.reachout.ui.screen.auth

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID


class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val email = mutableStateOf("")
    val role = mutableStateOf("")
    val userId = mutableStateOf("")   // ✅ Added

    fun signup(
        email: String, password: String, firstName: String,
        lastName: String, role: String, onResult: (Boolean, String?) -> Unit
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid
                        if (uid != null) {
                            val userMap = hashMapOf(
                                "firstName" to firstName,
                                "lastName" to lastName,
                                "email" to email,
                                "role" to role
                            )

                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(uid)
                                .set(userMap)
                                .addOnSuccessListener { onResult(true, null)
                                    userId.value = uid}
                                .addOnFailureListener { e -> onResult(false, e.message) }

                               // ✅ store UID here
                        } else {
                            onResult(false, "UserID not found")
                        }
                    } else {
                        onResult(false, task.exception?.message)
                    }
                }
        } else {
            onResult(false, "Email and password must not be empty")
        }
    }

    fun signin(emailInput: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (emailInput.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(emailInput, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        fetchUserData()

                        onResult(true, null)
                    } else {
                        onResult(false, task.exception?.message)
                    }
                }
        } else {
            onResult(false, "Email and password must not be empty")
        }
    }


    fun fetchUserData() {
        val uid = auth.currentUser?.uid

        if (uid != null) {
            FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        userId.value=doc.id
                        firstName.value = doc.getString("firstName") ?: ""
                        lastName.value = doc.getString("lastName") ?: ""
                        email.value = doc.getString("email") ?: ""
                        role.value = doc.getString("role") ?: ""
                    }
                }
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()  // Sign out from Firebase
        userId.value = ""
        email.value = ""
        firstName.value = ""
        lastName.value = ""
        role.value = ""
    }
}



