package viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {
    val auth: FirebaseAuth= FirebaseAuth.getInstance()

    fun signup(email: String,password:String,onResult:(Boolean,String?)->Unit)
    {
        if (email.isNotEmpty() && password.isNotEmpty()){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                onResult(task.isSuccessful,task.exception?.message)
            }
    }else{
            onResult(false, "Email and password must not be empty")
        }
    }

    fun signin(email: String,password:String,onResult:(Boolean,String?)->Unit)
    {
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    onResult(task.isSuccessful,task.exception?.message)
                }
        }else{
            onResult(false, "Email and password must not be empty")
        }
    }
}