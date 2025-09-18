package screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.component.Title
import app.component.Normal
import app.component.Textfieldcomp
import app.component.Passwordcomp
import app.component.clickable
import app.component.clickablelogintext
import app.component.spacer
import com.example.reachout.R


// composible for signup page
@Composable
fun signup(onSignInClick: () -> Unit={}) {
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
            Textfieldcomp(value=stringResource(id = R.string.firstname), iconval = R.drawable.profilepic)  //TextField for First name
            Textfieldcomp(value=stringResource(id = R.string.lastname), iconval = R.drawable.profilepic)   //TextField for Last Name
            Textfieldcomp(value=stringResource(id = R.string.email), iconval = R.drawable.emailicon)   //TextField for Email
            Passwordcomp(value=stringResource(id = R.string.password), iconval = R.drawable.password)  //TextField for Password
            spacer()
            clickable(value=stringResource(id=R.string.register)) //signup Button
            spacer()
            clickablelogintext(onSignUpClick = onSignInClick)


            }
    }
    
}

// composible preview

@Preview
@Composable
fun Defaultviewofsignup() {
    signup()
}