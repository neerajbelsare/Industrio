package com.example.industrio.screens.HomeScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.industrio.R
import com.example.industrio.navigation.AuthScreen
import com.example.industrio.navigation.FormScreen
import com.example.industrio.navigation.Profile
import com.example.industrio.navigation.nav_graph.Graph
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.CustomTopAppBarWithBackButton
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm.Technician
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm.TechnicianInfo
import com.example.industrio.screens.ForumScreen.ForumViewModel
import com.example.industrio.screens.ForumScreen.Question
import com.example.industrio.screens.ForumScreen.Reply
import com.example.industrio.screens.TechniciansScreen.TechnicianListModel
import com.example.industrio.ui.theme.MainColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.firebase.auth.FirebaseAuth
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.s


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
                    painter = painterResource(R.drawable.ic_filter),
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
            text = "Categories",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp),
            textAlign = TextAlign.Left,
            fontSize = 21.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier
            .height(20.dp))

        val itemList = listOf( ItemData(1, "Plumbing", R.drawable.ic_plumbing),
            ItemData(2, "Equipment \nInstallation", R.drawable.ic_equipment),
            ItemData(3, "Maintenance \nand Repairs", R.drawable.ic_repair),
            ItemData(4, "Preventive \nMaintenance", R.drawable.ic_preventive),
            ItemData(5, "Machinery \nAlignment", R.drawable.ic_align),
            ItemData(6, "Piping \nSystems", R.drawable.ic_lock),
            ItemData(7, "HVAC \nSystems", R.drawable.ic_hvac),
            ItemData(8, "Conveyor \nSystems", R.drawable.ic_conveyer),
            ItemData(9, "Welding and \nFabrication", R.drawable.ic_welding),
            ItemData(10, "Instrumentation \nand Control \nSystems", R.drawable.ic_instrument),
            ItemData(11, "Waste \nManagement \nSystems", R.drawable.ic_waste),
            ItemData(12, "Energy \nManagement", R.drawable.ic_energy),
            ItemData(13, "Safety \nSystems", R.drawable.ic_safety))

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 30.dp)
        ) {
            itemList.forEach { item ->
               Item(item = item)
                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        Spacer(modifier = Modifier
            .height(10.dp))

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
fun Item(item: ItemData) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFA03A), Color(0xFFEE583D)),
                        start = Offset(0f, 0f),
                        end = Offset(100f, 100f)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(id = item.icon),
                contentDescription = "Item Icon",
                tint = Color(0xffffffff)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = item.title,
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF9C9C9C)
        )

    }
}


data class ItemData(val id: Int, val title: String, val icon: Int)

@OptIn(ExperimentalTextApi::class)
@Composable
fun MainScreen(viewModel: ForumViewModel, navController: NavController) {

}

@Composable
fun QuestionItem(question: Question, onItemClick: (Question) -> Unit) {
    val selectedQuestionId = remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 5.dp)
            .clickable {
                selectedQuestionId.value = question.id
                onItemClick(question)
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question.title,
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = question.description,
                style = MaterialTheme.typography.body2,
                color = Color(0xFF9E9E9E),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            for(category in question.category) {
                if (category == "Plumbing") {
                    Text(
                        text = "    Plumbing    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFFFC982D))
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                if (category == "Equipment Installation") {
                    Text(
                        text = "   Equipment Installation   ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFF3BA5FF))
                    )
                }

                if (category == "Maintenance and Repairs") {
                    Text(
                        text = "    Maintenance and Repairs     ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFF0A6900))
                    )
                }

                if (category == "Preventive Maintenance") {
                    Text(
                        text = "   Preventive Maintenance   ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFFFFF52F))
                    )
                }

                if (category == "Machinery Alignment") {
                    Text(
                        text = "   Machinery Alignment   ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFFFF882B))
                    )
                }

                if (category == "Piping Systems") {
                    Text(
                        text = "   Piping Systems    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFFFF4343))
                    )
                }

                if (category == "HVAC Systems") {
                    Text(
                        text = "    HVAC Systems    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFF3C8BFF))
                    )
                }

                if (category == "Conveyor Systems") {
                    Text(
                        text = "    Conveyor Systems    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFFF734FF))
                    )
                }

                if (category == "Welding and Fabrication") {
                    Text(
                        text = "    Welding and Fabrication    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFF4D74FF))
                    )
                }

                if (category == "Instrumentation and Control Systems") {
                    Text(
                        text = "    Instrumentation and Control Systems    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFF008A63))
                    )
                }

                if (category == "Waste Management Systems") {
                    Text(
                        text = "    Waste Management Systems    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFFFFA22F))
                    )
                }

                if (category == "Energy Management") {
                    Text(
                        text = "    Energy Management    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .background(color = Color(0xFFFF3030))
                            .padding(end = 10.dp)
                    )
                }

                if (category == "Safety Systems") {
                    Text(
                        text = "    Safety Systems    ",
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = Color(0xFF305DFF))
                    )
                }
            }
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

    val selectedCategories = remember { mutableStateListOf<String>() }

    var questionTitle by remember { mutableStateOf("") }
    var questionDesc by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = questionTitle,
            onValueChange = { questionTitle = it },
            label = { Text("Question Title", fontSize = 24.sp) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            maxLines = 1,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = questionDesc,
            onValueChange = { questionDesc = it },
            label = { Text("Question Description", fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        categories.forEach { category ->
            Row(
                Modifier.padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = selectedCategories.contains(category),
                    onCheckedChange = { checked ->
                        if (checked) {
                            selectedCategories.add(category)
                        } else {
                            selectedCategories.remove(category)
                        }
                    }
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.postQuestion(selectedCategories, questionTitle, questionDesc, userId)
                navigateBack()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 20.dp)
        ) {
            Text("Post")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun QuestionDetailsScreen(
    viewModel: ForumViewModel,
    question: Question,
    navController: NavController
) {
    viewModel.fetchReplies(question.id)
    val replies by viewModel.replies.observeAsState(emptyList())
    val replyText = remember { mutableStateOf("") }

    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val userId = currentUser?.uid ?: ""

    Scaffold(
        topBar = {
            CustomTopAppBarWithBackButton(
                navController = navController,
                appBarTitle = "Question Thread",
                backgroundColor = Color.White
            )
        },
        backgroundColor = Color.White
    ) {
        it
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .shadow(elevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = question.title,
                        style = MaterialTheme.typography.h6
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = question.description,
                        style = MaterialTheme.typography.body2,
                        color = Color(0xFF9E9E9E),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    for(category in question.category) {
                        if (category == "Plumbing") {
                            Text(
                                text = "    Plumbing    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFFFC982D))
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        if (category == "Equipment Installation") {
                            Text(
                                text = "   Equipment Installation   ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFF3BA5FF))
                            )
                        }

                        if (category == "Maintenance and Repairs") {
                            Text(
                                text = "    Maintenance and Repairs     ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFF0A6900))
                            )
                        }

                        if (category == "Preventive Maintenance") {
                            Text(
                                text = "   Preventive Maintenance   ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFFFFF52F))
                            )
                        }

                        if (category == "Machinery Alignment") {
                            Text(
                                text = "   Machinery Alignment   ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFFFF882B))
                            )
                        }

                        if (category == "Piping Systems") {
                            Text(
                                text = "   Piping Systems    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFFFF4343))
                            )
                        }

                        if (category == "HVAC Systems") {
                            Text(
                                text = "    HVAC Systems    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFF3C8BFF))
                            )
                        }

                        if (category == "Conveyor Systems") {
                            Text(
                                text = "    Conveyor Systems    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFFF734FF))
                            )
                        }

                        if (category == "Welding and Fabrication") {
                            Text(
                                text = "    Welding and Fabrication    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFF4D74FF))
                            )
                        }

                        if (category == "Instrumentation and Control Systems") {
                            Text(
                                text = "    Instrumentation and Control Systems    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFF008A63))
                            )
                        }

                        if (category == "Waste Management Systems") {
                            Text(
                                text = "    Waste Management Systems    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFFFFA22F))
                            )
                        }

                        if (category == "Energy Management") {
                            Text(
                                text = "    Energy Management    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .background(color = Color(0xFFFF3030))
                                    .padding(end = 10.dp)
                            )
                        }

                        if (category == "Safety Systems") {
                            Text(
                                text = "    Safety Systems    ",
                                style = MaterialTheme.typography.caption,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(color = Color(0xFF305DFF))
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${replies.size} Replies",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

                if (replies.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                            items(replies) { reply ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .shadow(elevation = 3.dp)
                                ) {
                                    ReplyItem(reply)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                } else {
                    Image(painter = painterResource(id = R.drawable.no_replies), contentDescription = "No Questions Found.",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(260.dp))
                }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.width(280.dp),
                    value = replyText.value,
                    onValueChange = { replyText.value = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MainColor,
                        unfocusedBorderColor = Color(0xFFDADADA),
                    ),
                    label = { Text("Write a reply") },
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        viewModel.postReply(question.id, replyText.value, userId)
                        replyText.value = ""
                    },
                    enabled = replyText.value.isNotBlank()
                ) {
                    Icon(painterResource(id = R.drawable.ic_send), contentDescription = "Send")
                }
            }
        }
    }
}

@Composable
fun ReplyItem(reply: Reply) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(reply.text)
        Spacer(modifier = Modifier.height(8.dp))
        Text("User ID: ${reply.userId}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ChatScreen(viewModel: ForumViewModel, navController: NavController) {
    viewModel.fetchQuestions()
    val questions by viewModel.questions.observeAsState(emptyList())

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
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
                onValueChange = { },
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
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(40.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Discussion Forum",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color(0xFFFFA500),
                        Color(0xFFFF9800)
                    ),
                    tileMode = TileMode.Mirror
                ),
                fontSize = 30.sp
            )
        )

        Text(
            text = "Engage, learn, and inspire through discussions",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(50.dp))

        FloatingActionButton(
            onClick = { navController.navigate("questionForm") },
            content = { Icon(Icons.Filled.Add, "Add") },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
        )

        Text(
            text = "Recent Questions",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            fontSize = 18.sp,
            color = Color(0xFF838383),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        if (questions.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                items(questions) { question ->
                    QuestionItem(question) {
                        navController.navigate("questionDetails/${question.id}")
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        } else {
            Image(painter = painterResource(id = R.drawable.not_found), contentDescription = "No Questions Found.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(260.dp))
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TechnicianItem(technician: Technician, onItemClick: (Technician) -> Unit) {
    val selectedTechnicianName = remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 2.dp)
            .clickable {
                selectedTechnicianName.value = technician.name
                onItemClick(technician)
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            GlideImage(
                model = technician.profileUrl,
                contentDescription = "Your Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 20.dp)
                    .size(132.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .scale(1f, 1f)
            )



            Spacer(modifier = Modifier.height(8.dp))

            Text(text = technician.name)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "    ${technician.speciality}    ",
                color = Color(0xFF9B9B9B))
        }
    }
}

@Composable
fun TechnicianListScreen(viewModel: TechnicianListModel, navController: NavController) {
    val technicians by viewModel.technicians.observeAsState(emptyList())

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
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
                onValueChange = { },
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
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(40.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        if (technicians.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                items(technicians) { technician ->
                    TechnicianItem(technician) {
                        navController.navigate("technicianDetails/${technician.name}")
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.not_found),
                contentDescription = "No Questions Found.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(260.dp)
            )
        }
    }
}

@Composable
fun ExploreScreen(viewModel: TechnicianListModel, navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .wrapContentSize(Alignment.Center)
//    ) {
//        var location by remember { mutableStateOf("Your Location") }
//        val context = LocalContext.current
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(
//                onClick = {context.startActivity(Intent(context, MapActivity::class.java))},
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Text(text = "Access Maps")
//            }
//        }
//    }

    val technicians by viewModel.technicians.observeAsState(emptyList())

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
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
                onValueChange = { },
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
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(40.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Technicians",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color(0xFFFFA500),
                        Color(0xFFFF9800)
                    ),
                    tileMode = TileMode.Mirror
                ),
                fontSize = 30.sp
            )
        )

        Text(
            text = "Your Gateway to Qualified and Experienced Technicians",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(50.dp))

        val itemList = listOf( ItemData(1, "Mechanic", R.drawable.ic_plumbing),
            ItemData(2, "Welding", R.drawable.ic_equipment),
            ItemData(3, "Pipes", R.drawable.ic_repair),
            ItemData(4, "Plumbing", R.drawable.ic_preventive),
            ItemData(5, "Machinery", R.drawable.ic_align)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Text(
                text = "Top-Rated Specialities",
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                color = Color(0xFF838383),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 30.dp)
        ) {
            itemList.forEach { item ->
                Item(item = item)
                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Text(
                text = "Top-Rated Technicians",
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                color = Color(0xFF838383),
                fontWeight = FontWeight.Bold
            )

            TextButton(
                onClick = {navController.navigate("technicianList")},
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Text(text = "See All")
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        if (technicians.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                items(technicians) { technician ->
                    TechnicianItem(technician) {
                        navController.navigate("technicianDetails/${technician.name}")
                    }

                    Spacer(modifier =Modifier.width(8.dp))
                }
//                item {
//                    Spacer(modifier = Modifier.height(60.dp))
//                }
            }
        } else {
            Image(painter = painterResource(id = R.drawable.not_found), contentDescription = "No Questions Found.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(260.dp))
        }



    }
}

@Composable
fun TechnicianDetailsScreen(
    viewModel: TechnicianListModel,
    technician: Technician,
    navController: NavController
) {
    viewModel.fetchTechnicians()

    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val userId = currentUser?.uid ?: ""

    Scaffold(
        topBar = {
            CustomTopAppBarWithBackButton(
                navController = navController,
                appBarTitle = "Question Thread",
                backgroundColor = Color.White
            )
        },
        backgroundColor = Color.White
    ) {
        it
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .shadow(elevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = technician.name,
                        style = MaterialTheme.typography.h6
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = technician.phone,
                        style = MaterialTheme.typography.body2,
                        color = Color(0xFF9E9E9E),
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
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