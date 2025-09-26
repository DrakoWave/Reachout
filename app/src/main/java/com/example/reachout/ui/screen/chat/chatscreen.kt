package com.example.reachout.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reachout.ui.screen.auth.AuthViewModel
import com.example.reachout.ui.theme.Brown
import com.example.reachout.ui.theme.Creme
import com.example.reachout.ui.theme.MutedGreen

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = viewModel(),
    authViewModel: AuthViewModel,
    otherUserId: String
) {

    authViewModel.fetchUserData()
    val currentUserId = authViewModel.userId.value
    val messages by chatViewModel.messages.collectAsState()

    // Start listening
    LaunchedEffect(currentUserId, otherUserId) {
        chatViewModel.listenForMessages(currentUserId, otherUserId)
    }

    var newMessage by remember { mutableStateOf("") }

    Column (modifier = Modifier.background(brush = Brush.verticalGradient(colors = listOf(Creme, Color.White)))){
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = false
        ) {
            items(messages) { msg ->
                MessageRow(msg = msg, currentUserId = currentUserId)
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {
            TextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message") }
            )
            Button(
                onClick = {
                    if (newMessage.isNotBlank()) {
                        chatViewModel.sendMessage(currentUserId, otherUserId, newMessage)
                        newMessage = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MutedGreen),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(56.dp).padding(start = 5.dp)
            ) {
                Text(text = "Send", color = Color.White)
            }
        }
    }
}

@Composable
fun MessageRow(msg: ChatMessage, currentUserId: String) {
    Row(
        horizontalArrangement = if (msg.senderId == currentUserId) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Surface(
            color = if (msg.senderId == currentUserId) MutedGreen else Brown,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = msg.text,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
    }
}

