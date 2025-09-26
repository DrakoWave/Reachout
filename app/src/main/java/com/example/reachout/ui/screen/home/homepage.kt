package com.example.reachout.ui.screen.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.reachout.ui.common.Feedscreen
import com.example.reachout.ui.common.Normal
import com.example.reachout.ui.screen.auth.AuthViewModel
import com.example.reachout.ui.screen.home.FeedViewModel
import com.example.reachout.ui.theme.Creme

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    feedViewModel: FeedViewModel = viewModel()
) {
    // Fetch feed only once when this composable is first displayed
    LaunchedEffect(Unit) {
        feedViewModel.fetchFeed()
        authViewModel.fetchUserData()
    }

    Surface (modifier= Modifier.fillMaxSize(), color = Creme){

        Feedscreen(
            authViewModel = authViewModel,
            feedViewModel = feedViewModel
        )
    }
}

@Preview
@Composable
private fun testdisplay() {
    //home()
}
