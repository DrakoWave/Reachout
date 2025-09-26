package com.example.reachout.ui.common

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.reachout.R
import com.example.reachout.ui.common.Profilebutton
import com.example.reachout.ui.screen.auth.AuthViewModel
import com.example.reachout.ui.screen.home.FeedViewModel
import com.example.reachout.ui.screen.chat.ChatMessage
import com.example.reachout.ui.screen.chat.ChatViewModel
import com.example.reachout.ui.theme.Brown
import com.example.reachout.ui.theme.Creme
import com.example.reachout.ui.theme.Darkred
import com.example.reachout.ui.theme.Gray
import com.example.reachout.ui.theme.LightMutedGreen
import com.example.reachout.ui.theme.MutedGreen

// ==================== ORIGINAL COMPONENTS ====================

@Composable
fun Normal(value: String) {
    Text(
        text = value,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal
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
            fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun Textfieldcomp(value: String, onValueChange: (String) -> Unit, iconval: Int, label: String) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .alpha(0.5F)
            .padding(bottom = 5.dp),
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconval),
                contentDescription = "",
                Modifier
                    .size(30.dp)
                    .alpha(0.5F)
            )
        })
}

@Composable
fun Passwordcomp(value: String, onValueChange: (String) -> Unit, iconval: Int, label: String) {
    val passwordVisibility = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .alpha(0.5F)
            .padding(bottom = 5.dp),
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconval),
                contentDescription = "",
                Modifier
                    .size(25.dp)
                    .alpha(0.5F)
            )
        },
        trailingIcon = {
            val Eye = if (passwordVisibility.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (passwordVisibility.value) {
                stringResource(id = R.string.hidepassword)
            } else {
                stringResource(id = R.string.showpassword)
            }
            IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                Icon(imageVector = Eye, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun clickable(value: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(45.dp)
            .padding(top = 10.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(45.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            colorResource(id = R.color.darkolive), colorResource(id = R.color.brown)
                        )
                    ), shape = RoundedCornerShape(50.dp)
                )
        ) {
            Text(
                value,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(45.dp)
                    .padding(top = 10.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold, fontSize = 20.sp, fontStyle = FontStyle.Normal
                )
            )
        }
    }
}

@Composable
fun spacer() {
    Text(text = "", modifier = Modifier.heightIn(40.dp))
}

@Composable
fun clickablelogintext(onSignUpClick: () -> Unit) {
    val initialText = "Already have an account?"
    val loginText = " Login"
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        val annotatedString = buildAnnotatedString {
            append(initialText)
            pushStringAnnotation(tag = loginText, annotation = loginText)
            withStyle(style = SpanStyle(color = colorResource(id = R.color.darkolive))) {
                append(loginText)
            }
            pop()
        }
        ClickableText(text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent", "{${span.item}}")
                if ((span.item == loginText)) {
                    onSignUpClick()
                }
            }
        })
    }
}

@Composable
fun clickableregistertext(onSignInClick: () -> Unit) {
    val initialText = "Don't have an account?"
    val registerText = " Register"
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        val annotatedString = buildAnnotatedString {
            append(initialText)
            pushStringAnnotation(tag = registerText, annotation = registerText)
            withStyle(style = SpanStyle(color = colorResource(id = R.color.darkolive))) {
                append(registerText)
            }
            pop()
        }
        ClickableText(text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent", "{${span.item}}")
                if ((span.item == registerText)) {
                    onSignInClick()
                }
            }
        })
    }
}

@Composable
fun shopbutton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.size(48.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.shopping),
            tint = if (enabled) MutedGreen else Color.Gray,
            contentDescription = "ShoppingBag",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun sharebutton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier.size(48.dp)) {
        Icon(
            painter = painterResource(R.drawable.share),
            tint = Gray,
            contentDescription = "ShareButton",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun librarybutton(
    post: FeedViewModel.Post,
    feedViewModel: FeedViewModel,
    modifier: Modifier = Modifier
) {
    val pinnedPosts by feedViewModel.pinnedPosts.collectAsState()
    val isPinned = pinnedPosts.contains(post)

    IconButton(
        onClick = { feedViewModel.togglePin(post) },
        modifier = modifier.size(48.dp)
    ) {
        Icon(

            painter =if (isPinned) painterResource(R.drawable.openbook) else painterResource(R.drawable.bookclosed),
            tint =  Brown, // highlighted when pinned
            contentDescription = "Pin Post",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun postprofileinfo(
    modifier: Modifier = Modifier,
    content: Int = R.drawable.profilepic,
    value: String = "Anonymus",
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .padding(horizontal = 3.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color.White)
                .border(3.dp, Brown, shape = RoundedCornerShape(100.dp))
        ) {
            IconButton(onClick = onClick, modifier = Modifier.size(48.dp)) {
                Icon(
                    painter = painterResource(id = content),
                    tint = Brown,
                    contentDescription = "profilepicture",
                )
            }
        }
        Text(
            text = value,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif, fontSize = 17.sp, fontWeight = FontWeight.Light
            )
        )
    }
}

@Composable
fun Bar(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun Postimage(imageUrl: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .height(328.dp)
            .clip(RoundedCornerShape(8.dp)).padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop,modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(10.dp))
        )
    }
}


@Composable
fun Post(
    post: FeedViewModel.Post,
    feedViewModel: FeedViewModel,
    authViewModel: AuthViewModel
) {
    val showConfirmDialog = remember { mutableStateOf(false) }
    val isUserLoaded = authViewModel.userId.value.isNotEmpty() && authViewModel.email.value.isNotEmpty()
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color.White.copy(0.2f))
                )
            )
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // ---------------- Profile Bar ----------------
        Bar(
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            postprofileinfo(value = post.authorName) {}
        }

        // ---------------- Post Image ----------------
        Postimage(imageUrl = post.imageUrl)

        // ---------------- Price ----------------
        Text(
            text = "Price: ${post.price}₹",
            color = MutedGreen,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // ---------------- Collapsible Description ----------------
        Text(
            text = if (expanded.value) post.description else post.description.take(100) + if (post.description.length > 100) "..." else "",
            color = Brown,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { expanded.value = !expanded.value }
        )

        // ---------------- Action Buttons ----------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            shopbutton(enabled = isUserLoaded) { showConfirmDialog.value = true }
            sharebutton { }
            librarybutton(post = post, feedViewModel = feedViewModel)
        }
    }

    // ---------------- Confirm Purchase Dialog ----------------
    if (showConfirmDialog.value) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog.value = false },
            title = { Text("Confirm Purchase") },
            text = { Text("Are you sure you want to purchase '${post.title}'?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(MutedGreen),
                    onClick = {
                        feedViewModel.createPurchase(
                            post = post,
                            clientId = authViewModel.userId.value,
                            clientEmail = authViewModel.email.value
                        )
                        showConfirmDialog.value = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    onClick = { showConfirmDialog.value = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}


@Composable
fun Homebutton(modifier: Modifier = Modifier, onhomeclick: () -> Unit) {
    IconButton(onClick = onhomeclick, modifier = Modifier.size(48.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.home_fill),
            tint = Color.White,
            contentDescription = "HomeButton",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun Profilebutton(modifier: Modifier = Modifier, onprofileclick: () -> Unit) {
    IconButton(onClick = onprofileclick, modifier = Modifier.size(48.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.profilesection),
            tint = Color.White,
            contentDescription = "Profilesection",
            modifier = Modifier.size(30.dp)
        )
    }
}


@Composable
fun PendingPurchaseList(
    modifier: Modifier,
    currentUserId: String,
    role: String, // "creator" or "client"
    viewModel: FeedViewModel,
    onMessage: (String) -> Unit //  pass callback for starting chat
) {
    // Collect purchases from ViewModel
    val purchases by viewModel.purchases.collectAsState()

    // Filter only purchases relevant to this user
    val relevantPurchases = purchases.filter { purchase ->
        (role == "client" && purchase.clientId == currentUserId) ||
                (role == "creator" && purchase.creatorId == currentUserId)
    }

    Text(
        text = "ShoppingCart",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = MutedGreen,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    if (relevantPurchases.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No pending purchases", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(relevantPurchases, key = { it.purchaseId }) { purchase ->
                PendingPurchaseBar(
                    purchase = purchase,
                    role = role,
                    onConfirm = { pid -> viewModel.confirmPurchase(pid) },
                    onRemove = { viewModel.removePurchase(purchase.purchaseId) },
                    onMessage = { otherUserId -> onMessage(otherUserId) })
            }
        }
    }
}

@Composable
fun PendingPurchaseBar(
    purchase: FeedViewModel.PurchaseWithPost,
    role: String,
    onConfirm: (String) -> Unit,
    onMessage: (String) -> Unit,
    onRemove: (String) -> Unit // 👈 new callback for remove
) {
    val otherId = if (role == "creator") purchase.clientId else purchase.creatorId
    val otherEmail = if (role == "creator") purchase.clientEmail else purchase.creatorEmail
    val isCreator = role == "creator"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(LightMutedGreen, MutedGreen)
                    )
                )
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        purchase.postTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
                Text(
                    "Status: ${purchase.status}",
                    color = if (purchase.status == "pending") Darkred else Color.White
                )
                Text("From: $otherEmail", fontSize = 12.sp, color = Creme)


                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    // Message button
                    Button(
                        onClick = { onMessage(otherId) },
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text("Message", color = Brown)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Confirm button (only for creators & pending)

                    Button(
                        onClick = { onConfirm(purchase.purchaseId) },
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text("Confirm", color = MutedGreen)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Remove button (only for creators & pending)
                    Button(
                        onClick = { onRemove(purchase.purchaseId) },
                        colors = ButtonDefaults.buttonColors(Brown),
                    ) {
                        Text("Remove", color = Creme)
                    }
                }
            }
        }
    }
}


@Composable
fun Navbar(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {

    val role = viewModel.role.value

    Row {
        Bar(
            modifier = Modifier
                .background(color = MutedGreen)
                .fillMaxWidth()
                .height(78.dp)
                .padding(horizontal = 10.dp)
        ) {
            Homebutton(onhomeclick = { navController.navigate("home") })
            Profilebutton(
                onprofileclick =
                    { navController.navigate("profile") })

        }
    }
}


@Composable
fun Feedscreen(
    authViewModel: AuthViewModel,
    feedViewModel: FeedViewModel
) {
    LaunchedEffect(Unit) { feedViewModel.fetchFeed() }
    val pinnedPosts by feedViewModel.pinnedPosts.collectAsState()

    val posts = feedViewModel.posts.collectAsState().value

    if (posts.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No posts yet!", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),

        ) {

            // Show pinned posts first
            items(pinnedPosts, key = { it.id }) { post ->
                Post(post = post, feedViewModel = feedViewModel, authViewModel = authViewModel)
            }

            // Show regular posts excluding pinned
            items(posts.filter { it !in pinnedPosts }, key = { it.id }) { post ->
                Post(post = post, feedViewModel = feedViewModel, authViewModel = authViewModel)
            }
        }
    }
}


// ==================== FIXED CREATE / UPLOAD COMPONENTS ====================

@Composable
fun Insertimg(selectedImageUri: Uri?, onImageSelected: (Uri) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (selectedImageUri != null) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            IconButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.insertimage),
                    tint = Creme,
                    contentDescription = "Insert Image",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

@Composable
fun Createsheet(
    selectedImageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    onUpload: (Uri, String, String, String) -> Unit
) {
    val uploadTitle = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Textfieldcomp(
            value = uploadTitle.value,
            onValueChange = { uploadTitle.value = it },
            iconval = R.drawable.write,
            label = "Upload Title"
        )

        Textfieldcomp(
            value = price.value,
            onValueChange = { price.value = it },
            iconval = R.drawable.money,
            label = "Price"
        )

        Insertimg(
            selectedImageUri = selectedImageUri, onImageSelected = onImageSelected
        )

        TextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .alpha(0.5f)
        )

        IconButton(onClick = {
            selectedImageUri?.let { uri ->
                onUpload(uri, uploadTitle.value, price.value, description.value)
            }
        }) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = "Upload",
                tint = Brown,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Createbutton(
    authViewModel: AuthViewModel,
    feedViewModel: FeedViewModel,
    selectedImageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    val showBottomSheet = remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = { showBottomSheet.value = true }, modifier = Modifier.size(48.dp)) {
            Icon(
                painterResource(id = R.drawable.add),
                contentDescription = "create new post",
                tint = Color.White,
                modifier = Modifier.size(39.dp)
            )
        }

        if (showBottomSheet.value) {
            ModalBottomSheet(onDismissRequest = { showBottomSheet.value = false }) {
                Createsheet(
                    selectedImageUri = selectedImageUri,
                    onImageSelected = onImageSelected
                ) { uri, title, price, description ->
                    feedViewModel.uploadpost(
                        imageUri = uri,
                        title = title,
                        description = description,
                        price = price,
                        creatorId = authViewModel.userId.value,
                        creatorName = authViewModel.firstName.value + " " + authViewModel.lastName.value,
                        creatorEmail = authViewModel.email.value,
                        onSuccess = { showBottomSheet.value = false },
                        onFailure = { error -> /* handle error */ }
                    )
                }
            }
        }
    }
}


@Composable
fun LogoutButton(
    authViewModel: AuthViewModel,
    onLogoutConfirmed: () -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.End) {
    // The actual logout button
    Button(
        onClick = { showConfirmDialog = true },
        colors = ButtonDefaults.buttonColors(containerColor = Creme)
    ) {
        Text(text = "Logout", color = Brown)
    }
}

    // Confirmation dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text(text = "Confirm Logout") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        authViewModel.logout() // Perform logout
                        showConfirmDialog = false
                        onLogoutConfirmed()    // Navigate to login or other action
                    },
                    colors = ButtonDefaults.buttonColors(Brown)
                ) {
                    Text("Yes", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false },
                    colors = ButtonDefaults.buttonColors(MutedGreen)
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}







