package com.example.reachout.ui.screen

import android.util.Log
import android.widget.Button
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
import app.component.Title
import app.component.Normal
import app.component.Textfieldcomp
import app.component.Passwordcomp
import app.component.clickable
import app.component.clickablelogintext
import app.component.spacer
import com.example.reachout.R
import viewmodels.AuthViewModel


// composible for signup page
@Composable
fun signup(onSignInClick: () -> Unit={},
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
            Textfieldcomp(value=lastname.value,onValueChange={lastname.value=it}, iconval = R.drawable.profilepic,label="labelname")   //TextField for Last Name
            Textfieldcomp(value=email.value,onValueChange={email.value=it}, iconval = R.drawable.emailicon,label="email")   //TextField for Email
            Passwordcomp(value=password.value,onValueChange={password.value=it}, iconval = R.drawable.password,label="password")  //TextField for Password
            spacer()
            spacer()
            clickablelogintext(onSignUpClick = onSignInClick)

            clickable(value=stringResource(id=R.string.register),
                onClick={
                    viewModel.signup(email.value,password.value)
                    {success,error->
                        if (success) {
                            Log.d("Auth", "Signed up user: ${firstname.value} ${lastname.value}, Email: ${email.value}")
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
//   signup()
}