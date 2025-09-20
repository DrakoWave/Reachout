package app.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reachout.R
import com.example.reachout.ui.screen.signin
import com.example.reachout.ui.screen.signup

@Composable
fun Normal(value: String) {
    Text(
        text = value,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun Title(value: String) {
    Text(
        text = value,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(60.dp),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun Textfieldcomp(value: String, onValueChange:(String)-> Unit, iconval: Int, label: String) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .alpha(0.5F)
            .padding(bottom = 5.dp),
        label= {Text(text=label) },
        keyboardOptions = KeyboardOptions.Default,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(painter = painterResource(id=iconval), contentDescription = "", Modifier
                .size(30.dp)
                .alpha(0.5F))
        }
    )

}

@Composable
fun Passwordcomp(value: String, onValueChange: (String)-> Unit, iconval: Int, label: String) {
    val passwordVisibility= remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .alpha(0.5F)
            .padding(bottom = 5.dp),

        label= {Text(text=label) },

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

        value = value,

        onValueChange = onValueChange,

        leadingIcon = {
            Icon(painter = painterResource(id=iconval), contentDescription = "", Modifier
                .size(25.dp)
                .alpha(0.5F))
        },

        trailingIcon = {

            val Eye= if(passwordVisibility.value)
            {
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }

            var description= if(passwordVisibility.value)
            {
                stringResource(id=R.string.hidepassword)
            }else{
                stringResource(id =R.string.showpassword)
            }

            IconButton(onClick = {passwordVisibility.value=!passwordVisibility.value}) {
                Icon(imageVector=Eye, contentDescription = description)
            }

        },

        visualTransformation = if(passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()



    )
}

@Composable
fun clickable(value: String,onClick:()-> Unit) {

    Button(onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(45.dp)
            .padding(top = 10.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.Transparent)){

            Box(modifier = Modifier
                .fillMaxWidth()
                .heightIn(45.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            colorResource(id = R.color.darkolive),
                            colorResource(id = R.color.brown)
                        )
                    ),
                    shape = RoundedCornerShape(50.dp)
                )) {
                Text(
                    value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(45.dp)
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal
                    )
                )
            }

    }


}

@Composable
fun spacer()
{
    Text(text="",modifier = Modifier.heightIn(40.dp))
}

@Composable
fun nav(navController: NavHostController = rememberNavController()) {
    NavHost(navController=navController, startDestination ="register"){
        composable ("register"){ signup(onSignInClick={navController.navigate("login")}) }
        composable ("login"){ signin(onSignUpClick={navController.navigate("register")})}}
}

@Composable
fun clickablelogintext(onSignUpClick:() -> Unit) {
    val initialText="Already have an account?"
    val loginText=" Login"

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
    val annotatedString= buildAnnotatedString { append(initialText)
        pushStringAnnotation(tag = loginText, annotation = loginText)
    withStyle(style = SpanStyle(color = colorResource(id = R.color.darkolive))) {
        append(loginText)
    }
    pop()
    }

    ClickableText(text = annotatedString, onClick = {offset ->
        annotatedString.getStringAnnotations(offset,offset).
                firstOrNull()?.also{span ->
                    Log.d("ClickableTextComponent","{${span.item}}")
                    if((span.item==loginText)){
                        onSignUpClick()
                    }
        }
    })}
}

@Composable
fun clickableregistertext(onSignInClick:()->Unit) {
    val initialText="Don't have an account?"
    val registerText=" Register"

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        val annotatedString= buildAnnotatedString { append(initialText)
            pushStringAnnotation(tag = registerText, annotation = registerText)
            withStyle(style = SpanStyle(color = colorResource(id = R.color.darkolive))) {
                append(registerText)
            }
        pop()
        }

        ClickableText(text = annotatedString, onClick = {offset ->
            annotatedString.getStringAnnotations(offset,offset).
            firstOrNull()?.also{span ->
                Log.d("ClickableTextComponent","{${span.item}}")
                if((span.item==registerText)){
                    onSignInClick()
                }
            }
        })}
}


