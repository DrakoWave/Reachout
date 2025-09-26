package com.example.reachout.ui.screen.home

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class FeedViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()


    data class Post(
        val id: String = "",
        val title: String = "",
        val description: String = "",
        val price: String = "",
        val imageUrl: String = "",
        val author: String = "",
        val creatorEmail: String = "",
        val timestamp: Long = 0,
        val authorName: String=""
    )

    data class PurchaseWithPost(
        val purchaseId: String = "",
        val postId: String = "",
        val postTitle: String = "",
        val clientId: String = "",
        val clientEmail: String = "",
        val creatorId: String = "",
        val creatorEmail: String = "",
        val status: String = "pending"
    )

    private val _purchases = MutableStateFlow<List<PurchaseWithPost>>(emptyList())
    val purchases: StateFlow<List<PurchaseWithPost>> = _purchases

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _pinnedPosts = MutableStateFlow<List<Post>>(emptyList())
    val pinnedPosts: StateFlow<List<Post>> = _pinnedPosts


    fun uploadpost(
        imageUri: Uri,
        title: String,
        description: String,
        price: String,
        creatorId: String,
        creatorName: String,
        creatorEmail: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val storageRef = storage.reference
        val postRef = storageRef.child("posts/${UUID.randomUUID()}.jpg")

        postRef.putFile(imageUri).addOnSuccessListener {
            postRef.downloadUrl.addOnSuccessListener { uri ->
                val postDoc = firestore.collection("posts").document()
                val postId = postDoc.id

                val postData = hashMapOf(
                    "id" to postId,
                    "title" to title,
                    "description" to description,
                    "price" to price,
                    "imageUrl" to uri.toString(),
                    "author" to creatorId,
                    "authorName" to creatorName,
                    "creatorEmail" to creatorEmail,
                    "timestamp" to System.currentTimeMillis()
                )

                postDoc.set(postData)
                    .addOnSuccessListener { onSuccess(postId) }
                    .addOnFailureListener { e -> onFailure(e.message ?: "Unknown error") }
            }
        }.addOnFailureListener { e -> onFailure(e.message ?: "Upload failed") }
    }

    fun fetchFeed() {
        firestore.collectionGroup("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null && !snapshot.isEmpty) {
                    _posts.value = snapshot.toObjects(Post::class.java)
                }
            }
    }

    fun createPurchase(post: Post, clientId: String, clientEmail: String) {
        val purchaseId = firestore.collection("purchases").document().id

        val purchase = PurchaseWithPost(
            purchaseId = purchaseId,
            postId = post.id,
            postTitle = post.title,
            clientId = clientId,
            clientEmail = clientEmail,
            creatorId = post.author,
            creatorEmail = post.creatorEmail,
            status = "pending"
        )

        firestore.collection("purchases").document(purchaseId)
            .set(purchase)
            .addOnSuccessListener {
                _purchases.value = _purchases.value + purchase
            }
    }

    fun fetchPurchases() {
        firestore.collection("purchases")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null && !snapshot.isEmpty) {
                    _purchases.value = snapshot.toObjects(PurchaseWithPost::class.java)
                }
            }
    }

    fun confirmPurchase(purchaseId: String) {
        _purchases.value = _purchases.value.map {
            if (it.purchaseId == purchaseId) it.copy(status = "confirmed") else it
        }
        firestore.collection("purchases").document(purchaseId)
            .update("status", "confirmed")
    }

    fun removePurchase(purchaseId: String) {
        // Update local state
        _purchases.value = _purchases.value.filterNot { it.purchaseId == purchaseId }

        // Remove from Firestore
        firestore.collection("purchases").document(purchaseId)
            .delete()
            .addOnFailureListener { e ->
                // If delete fails, restore previous state (optional rollback)
                fetchPurchases()
            }
    }

    fun togglePin(post: Post) {
        _pinnedPosts.value = if (_pinnedPosts.value.contains(post)) {
            _pinnedPosts.value - post  // unpin
        } else {
            _pinnedPosts.value + post  // pin
        }
    }
}
