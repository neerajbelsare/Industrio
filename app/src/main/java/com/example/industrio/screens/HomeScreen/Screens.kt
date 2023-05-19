package com.example.industrio.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.industrio.R
import com.example.industrio.navigation.AuthScreen
import com.example.industrio.ui.theme.MainColor
import com.example.industrio.navigation.Profile
import com.example.industrio.navigation.nav_graph.Graph
import com.example.industrio.screens.ForumScreen.ForumViewModel
import com.example.industrio.screens.ForumScreen.Question
import com.example.industrio.screens.ForumScreen.Reply
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.firebase.auth.FirebaseAuth
import com.valentinilk.shimmer.shimmer
import io.getstream.chat.android.client.uploader.FileUploader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = MainColor,
    unSelectedColor: Color = Color.Gray,
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 4000,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(pagerState.currentPage) {
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }

        // you can remove the surface in case you don't want
        // the transparant bacground
        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(40.dp)
                    ),
                value = "",
                onValueChange = { /* Handle search input change */ },
                placeholder = { Text("Search") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.body1
            )

            IconButton(
                onClick = { /* Handle notification icon click */ },
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(40.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        val userDetails = profileViewModel.userDetails.collectAsState()

        Spacer(modifier = Modifier
            .height(10.dp))

        Text(
            text = "Welcome,",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp),
            textAlign = TextAlign.Left,
            fontSize = 18.sp,
            color = Color.Gray
        )

        if (userDetails != null) {
            userDetails.value?.let { user ->
                Text(
                    text = user.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp),
                    textAlign = TextAlign.Left,
                    fontSize = 36.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Spacer(modifier = Modifier
            .height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_poster_home),
            contentDescription = "Card - Home Page",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier
            .height(50.dp))

        Text(
            text = "Top Categories",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp),
            textAlign = TextAlign.Left,
            fontSize = 21.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier
            .height(20.dp))

        Spacer(modifier = Modifier
            .height(30.dp))

        val images = listOf(
            R.drawable.feature1, R.drawable.feature2, R.drawable.feature3, R.drawable.feature4
        )

        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            AutoSlidingCarousel(
                itemsCount = images.size,
                itemContent = { index ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(images[index])
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.height(200.dp)
                    )
                }
            )
        }

        Spacer(modifier = Modifier
            .height(80.dp))
    }
}

@Composable
fun MainScreen(viewModel: ForumViewModel, navController: NavController) {
    val questions by viewModel.questions.observeAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(40.dp)
                    ),
                value = "",
                onValueChange = {  },
                placeholder = { Text("Search") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.body1
            )

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(40.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(30.dp)
        )

        Text(
            text = "Discussion Forum",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = "Engage, learn, and inspire through discussions",
            modifier = Modifier
                .fillMaxWidth().padding(top = 15.dp),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray
        )

        if (questions.isNotEmpty()) {
            QuestionList(questions)
        } else {
            Text("No questions found.")
        }
        FloatingActionButton(
            onClick = { navController.navigate("questionForm") },
            content = { Icon(Icons.Filled.Add, "Add") },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End) // Aligns the floating button to the bottom right
        )
    }
}

@Composable
fun QuestionList(questions: List<Question>) {
    LazyColumn {
        items(questions) { question ->
            QuestionItem(question)
        }
    }
}
@Composable
fun QuestionItem(question: Question) {
    val selectedQuestionId = remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { selectedQuestionId.value = question.id }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question.text,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Category: ${question.category}",
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
            Text(
                text = "User ID: ${question.userId}",
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
            Text(
                text = "Timestamp: ${question.timestamp}",
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun QuestionFormScreen(viewModel: ForumViewModel, navigateBack: () -> Unit) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val userId = currentUser?.uid ?: ""

    val categories = listOf(
        "Plumbing",
        "Equipment Installation",
        "Maintenance and Repairs",
        "Preventive Maintenance",
        "Machinery Alignment",
        "Piping Systems",
        "HVAC Systems",
        "Conveyor Systems",
        "Welding and Fabrication",
        "Instrumentation and Control Systems",
        "Waste Management Systems",
        "Energy Management",
        "Safety Systems"
    )

    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var questionText by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Post a Question", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        DropdownMenu(
            expanded = false,
            onDismissRequest = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        selectedCategory = category
                    }
                ) {
                    Text(text = category)
                }
            }
        }
        Text(
            text = "Selected Category: $selectedCategory",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = questionText,
            onValueChange = { questionText = it },
            label = { Text("Question") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.postQuestion(selectedCategory, questionText, userId)
                navigateBack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Post")
        }
    }
}


@Composable
fun QuestionDetailsScreen(
    viewModel: ForumViewModel,
    question: Question
) {
    val replies by viewModel.replies.observeAsState(emptyList())
    val replyText = remember { mutableStateOf("") }

    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val userId = currentUser?.uid ?: ""

    Column {
        Text(question.text)
        if (replies.isNotEmpty()) {
            ReplyList(replies)
        } else {
            Text("No replies found.")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = replyText.value,
                onValueChange = { replyText.value = it },
                label = { Text("Write a reply") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.postReply(question.id, replyText.value, userId)
                    replyText.value = ""
                },
                enabled = replyText.value.isNotBlank()
            ) {
                Text("Submit")
            }
        }
    }
}


@Composable
fun ReplyList(replies: List<Reply>) {
    LazyColumn {
        items(replies) { reply ->
            ReplyItem(reply)
        }
    }
}

@Composable
fun ReplyItem(reply: Reply) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(reply.text)
        Spacer(modifier = Modifier.height(8.dp))
        Text("User ID: ${reply.userId}")
    }
}



@Composable
fun ChatScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        navController.navigate(Graph.FORUM)
    }
}

@Composable
fun ExploreScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        var location by remember { mutableStateOf("Your Location") }
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {context.startActivity(Intent(context, MapActivity::class.java))},
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Access Maps")
            }
        }
    }
}

@Composable
fun MapScreen() {
    val mapOptions = remember { GoogleMapOptions() }
    val mapViewRef = remember { mutableStateOf<MapView?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                MapView(context, mapOptions).apply {
                    onCreate(Bundle())
                    mapViewRef.value = this
                }
            },
            update = { view ->
                val mapView = mapViewRef.value
                if (mapView != null && mapView != view) {
                    mapViewRef.value = null
                    mapView.onDestroy()
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Search bar
        OutlinedTextField(
            value = "", // Implement your own state for search query
            onValueChange = {}, // Implement your own logic for handling search query changes
            placeholder = { Text(text = "Search for a location") },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = Color(0xFFAFAFAF)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MainColor,
                unfocusedBorderColor = Color(0xFFC7C7C7)
            ),
            shape = RoundedCornerShape(15.dp)
        )

        // Button
        Button(
            onClick = { /* Implement your logic for button click */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(text = "Button")
        }
    }
}


class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeView = ComposeView(this)
        composeView.setContent {
            MapScreen()
        }

        setContentView(composeView)
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(bottom = 60.dp)
    ) {
        ProfileEcommerce(navController = navController)
    }
}

private val optionsList: ArrayList<OptionsData> = ArrayList()

@Composable
fun ProfileEcommerce(navController: NavController, context: Context = LocalContext.current.applicationContext) {
    // This indicates if the optionsList has data or not
    // Initially, the list is empty. So, its value is false.
    var listPrepared by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            optionsList.clear()

            // Add the data to optionsList
            prepareOptionsData()

            listPrepared = true
        }
    }

    if (listPrepared) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {
                // User's image, name, email and edit button
                UserDetails(context = context)
            }

            // Show the options
            items(optionsList) {
                    item ->
                OptionsItemStyle(item = item, context = context, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun UserDetails(context: Context, profileViewModel: ProfileViewModel = viewModel()) {
    val userDetails = profileViewModel.userDetails.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(110.dp)
            .background(Color.White, RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (userDetails != null) {
            // User's image
            GlideImage(
                model = profileViewModel.imageUrl,
                contentDescription = "Your Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(72.dp)
                    .clip(CircleShape),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(weight = 3f, fill = false)
                        .padding(start = 16.dp)
                ) {
                    userDetails.value?.let { user ->
                        Text(
                            text = user.name,
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.googlesansdisplay_bold,
                                        FontWeight.Bold
                                    )
                                ),
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = user.email,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.googlesansdisplay_regular,
                                        FontWeight.Normal
                                    )
                                ),
                                color = Color.Gray,
                                letterSpacing = (0.8).sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(110.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .shimmer(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(Color(0xffbbbbbb))
                        .padding(start = 20.dp)
                        .shimmer()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(weight = 3f, fill = false)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "",
                            modifier = Modifier
                                .width(200.dp)
                                .height(20.dp)
                                .background(Color(0xffbbbbbb))
                                .shimmer()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "",
                            modifier = Modifier
                                .width(160.dp)
                                .height(20.dp)
                                .background(Color(0xffbbbbbb))
                                .shimmer()
                        )
                    }
                }
            }
        }
    }
}

// Row style for options
@Composable
private fun OptionsItemStyle(item: OptionsData, context: Context, navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val user = profileViewModel.userDetails.collectAsState()

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(10.dp))
                .clickable(enabled = true) {
                    when (item.title) {
                        "Account" -> navController.navigate(Graph.PROFILE)
                        "My Health" -> navController.navigate(Profile.DashboardScreen.route)
                        "Orders" -> navController.navigate(Profile.OrdersScreen.route)
                        "Addresses" -> navController.navigate(Profile.AddressesScreen.route)
                        "Saved Cards" -> navController.navigate(Profile.CardsScreen.route)
                        "Settings" -> navController.navigate(Profile.SettingsScreen.route)
                        "Help Center" -> navController.navigate(Profile.HelpScreen.route)
                        "Offers and Coupons" -> navController.navigate(Profile.OffersScreen.route)
                        "Sign Out" -> {
                            val auth = FirebaseAuth.getInstance()
                            auth.signOut()
                            navController.navigate(AuthScreen.SignInScreen.route)
                        }
                    }
                }
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                tint = MaterialTheme.colors.primary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(weight = 3f, fill = false)
                        .padding(start = 16.dp)
                ) {

                    // Title
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.googlesansdisplay_medium,
                                    FontWeight.Medium
                                )
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Sub title
                    Text(
                        text = item.subTitle,
                        style = TextStyle(
                            fontSize = 14.sp,
                            letterSpacing = (0.8).sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.googlesansdisplay_regular,
                                    FontWeight.Normal
                                )
                            ),
                            color = Color.Gray
                        )
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

private fun prepareOptionsData() {

    val appIcons = Icons.Outlined

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_profile,
            title = "Account",
            subTitle = "Manage your account"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_sliders,
            title = "My Dashboard",
            subTitle = "Manage additional account settings"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_truck,
            title = "Orders",
            subTitle = "Orders history"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_mail,
            title = "Addresses",
            subTitle = "Your saved addresses"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_card,
            title = "Saved Cards",
            subTitle = "Your saved debit/credit cards"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_settings,
            title = "Settings",
            subTitle = "App notification settings"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_help,
            title = "Help Center",
            subTitle = "FAQs and customer support"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_coupon,
            title = "Offers and Coupons",
            subTitle = "Offers and coupon codes for you"
        )
    )

    optionsList.add(
        OptionsData(
            icon = R.drawable.ic_sign_out,
            title = "Sign Out",
            subTitle = "Sign out from the app"
        )
    )
}

data class OptionsData(val icon: Int, val title: String, val subTitle: String)