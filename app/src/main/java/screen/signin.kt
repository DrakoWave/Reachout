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
import app.component.Normal
import app.component.Passwordcomp
import app.component.Textfieldcomp
import app.component.Title
import app.component.clickable
import app.component.clickableregistertext
import app.component.spacer
import com.example.reachout.R


@Composable
fun signin(onSignUpClick: () -> Unit={}) {

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
            Textfieldcomp(value=stringResource(id = R.string.email), iconval = R.drawable.emailicon)   //TextField for Email
            Passwordcomp(value=stringResource(id = R.string.password), iconval = R.drawable.password)  //TextField for Password
            spacer()
            clickable(value=stringResource(id=R.string.login)) //Signin Button
            spacer()
            clickableregistertext(onSignInClick = onSignUpClick)
        }
    }
}

@Preview
@Composable
fun Defaultsviewofignin() {
    //signin()
}
