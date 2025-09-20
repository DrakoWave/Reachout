package com.example.reachout.ui.screen

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
import app.component.Normal
import app.component.Passwordcomp
import app.component.Textfieldcomp
import app.component.Title
import app.component.clickable
import app.component.clickableregistertext
import app.component.spacer
import com.example.reachout.R
import viewmodels.AuthViewModel


@Composable
fun signin(onSignUpClick: () -> Unit={},
           viewModel: AuthViewModel= viewModel()
)
{

    val email= remember{
        mutableStateOf("")
    }

    val password= remember{
        mutableStateOf("")
    }

    Surface(//area of Interface

    modifier = Modifier.
        fillMaxSize()
        .background(color = Color.White)
        .padding(10.dp)){

        Column (modifier = Modifier.fillMaxSize().padding(20.dp)){
            spacer()
            Normal(value = stringResource(id = R.string.topbasicsignin))
            Title(value=stringResource(id = R.string.loginaccount))
            spacer()
            Textfieldcomp(value=email.value,onValueChange={email.value=it},iconval = R.drawable.emailicon,label="email")   //TextField for Email
            Passwordcomp(value=password.value,onValueChange={password.value=it},iconval = R.drawable.password,label="password")  //TextField for Password
            spacer()
            spacer()
            clickableregistertext(onSignInClick = onSignUpClick)
            clickable(value=stringResource(id=R.string.login),
                onClick={
                    viewModel.signin(email.value,password.value)
                    {success,error->
                        if (success) {
                            Log.d("Auth","Email: ${email.value}")
                        } else {
                            Log.e("Auth","Signup failed: $error")
                        }
                    }
                }) //Signin Button

        }
    }
}

@Preview
@Composable
fun Defaultsviewofignin() {
    //signin()
}
