package com.example.reachout.ui.screen.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reachout.ui.common.Title
import com.example.reachout.ui.common.Normal
import com.example.reachout.ui.common.Textfieldcomp
import com.example.reachout.ui.common.Passwordcomp
import com.example.reachout.ui.common.clickable
import com.example.reachout.ui.common.clickablelogintext
import com.example.reachout.ui.common.spacer
import com.example.reachout.R


// composible for signup page
@Composable
fun signup(onSignInClick: () -> Unit={},
           onregistersuccess:()->Unit={},
           viewModel: AuthViewModel= viewModel()
)
{

    val firstname= remember{
        mutableStateOf("")
    }
    val lastname= remember{
        mutableStateOf("")
    }
    val email= remember{
        mutableStateOf("")
    }
    val password= remember{
        mutableStateOf("")
    }
    val role= remember{
        mutableStateOf("")
    }

    Surface (  //area for interface
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ){
        Column (modifier = Modifier.fillMaxSize().padding(20.dp)){
            spacer()
            Normal(value = stringResource(id = R.string.topbasicsignup))
            Title(value=stringResource(id = R.string.createaccount))
            spacer()
            Textfieldcomp(value=firstname.value,onValueChange={firstname.value=it}, iconval = R.drawable.profilepic,label="firstname")  //TextField for First name
            Textfieldcomp(value=lastname.value,onValueChange={lastname.value=it}, iconval = R.drawable.profilepic,label="lastname")   //TextField for Last Name
            Textfieldcomp(value=email.value,onValueChange={email.value=it}, iconval = R.drawable.emailicon,label="email")   //TextField for Email
            Passwordcomp(value=password.value,onValueChange={password.value=it}, iconval = R.drawable.password,label="password")  //TextField for Password
            Textfieldcomp(value=role.value,onValueChange={role.value=it}, iconval = R.drawable.profilepic,label="role")
            spacer()
            spacer()
            clickablelogintext(onSignUpClick = onSignInClick)

            clickable(value=stringResource(id=R.string.register),
                onClick={
                    viewModel.signup(email.value,password.value,firstname.value,lastname.value,role=role.value)
                    {success,error->
                        if (success) {
                            Log.d("Auth", "Signed up user: ${firstname.value} ${lastname.value}, Email: ${email.value},Role:${role.value}")
                            onregistersuccess()
                        } else {
                            Log.e("Auth", "Signup failed: $error")
                        }
                    }
                }) //signup Button

            }
    }
    
}

// composible preview

@Preview
@Composable
fun Defaultviewofsignup() {
  signup()
}