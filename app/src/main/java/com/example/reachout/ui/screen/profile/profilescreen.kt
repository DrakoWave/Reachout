package com.example.reachout.ui.screen.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.reachout.ui.theme.Creme
import com.example.reachout.ui.common.Createbutton
import com.example.reachout.ui.common.LogoutButton
import com.example.reachout.ui.common.PendingPurchaseBar
import com.example.reachout.ui.common.PendingPurchaseList
import com.example.reachout.ui.common.spacer
import com.example.reachout.ui.screen.auth.AuthViewModel
import com.example.reachout.ui.screen.home.FeedViewModel
import com.example.reachout.ui.theme.Brown


@Composable
fun ProfileView(
    authViewModel: AuthViewModel = viewModel(),
    feedViewModel: FeedViewModel = viewModel(),
    navController: NavHostController // 👈 add navController
) {
    // Fetch purchases and user info
    LaunchedEffect(Unit) {
        authViewModel.fetchUserData()
        feedViewModel.fetchPurchases()
        feedViewModel.fetchFeed() // optional, if you want posts displayed
    }

    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Creme
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            spacer()

            // Create Post button
            if (authViewModel.role.value == "creator") {
                Row(
                    modifier = Modifier
                        .background(color = Brown, shape = RoundedCornerShape(20.dp))
                        .height(100.dp)
                        .width(300.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Createbutton(
                        authViewModel = authViewModel,
                        feedViewModel = feedViewModel,
                        selectedImageUri = selectedImageUri.value,
                        onImageSelected = { uri -> selectedImageUri.value = uri }
                    )
                    Text(text = "Create", color = Color.White)
                }

                spacer()
            }

            // Pending purchases list that takes remaining space
            PendingPurchaseList(
                modifier = Modifier.fillMaxHeight().weight(1f),
                currentUserId = authViewModel.userId.value,
                role = authViewModel.role.value,
                viewModel = feedViewModel,
                onMessage = { otherUserId ->
                    navController.navigate("chat/$otherUserId")
                }
            )

            // Logout button at the bottom
            LogoutButton(authViewModel) {
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }
        }
    }

}






