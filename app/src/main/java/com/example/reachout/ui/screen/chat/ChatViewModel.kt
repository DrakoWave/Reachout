package com.example.reachout.ui.screen.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Long = 0
)

class ChatViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun sendMessage(senderId: String, receiverId: String, text: String) {
        val msgId = firestore.collection("messages").document().id
        val msg = ChatMessage(
            id = msgId,
            senderId = senderId,
            receiverId = receiverId,
            text = text,
            timestamp = System.currentTimeMillis()
        )
        firestore.collection("messages").document(msgId).set(msg)
    }

    fun listenForMessages(currentUserId: String, otherUserId: String) {
        firestore.collection("messages")
            .whereIn("senderId", listOf(currentUserId, otherUserId))
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val msgs = snapshot.toObjects(ChatMessage::class.java)
                        .filter {
                            (it.senderId == currentUserId && it.receiverId == otherUserId) ||
                                    (it.senderId == otherUserId && it.receiverId == currentUserId)
                        }
                        .sortedBy { it.timestamp }
                    _messages.value = msgs
                }
            }
    }
}
