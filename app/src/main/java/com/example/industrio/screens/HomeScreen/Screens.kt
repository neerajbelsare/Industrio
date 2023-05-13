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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.industrio.R
import com.example.industrio.navigation.AuthScreen
import com.example.industrio.ui.theme.MainColor
import com.example.industrio.navigation.Profile
import com.example.industrio.navigation.nav_graph.Graph
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.firebase.auth.FirebaseAuth
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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
@Composable
fun GridItem(icon: ImageVector, text: String) {
    Column(
        modifier = Modifier.padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = text )
        Text(text = text,
        modifier = Modifier,
        fontSize = 12.sp,
        color = Color(0xFF2F365C)
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier
            .height(50.dp))
        Row(modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .fillMaxWidth()
            .background(color = Color.White)) {
            Spacer(Modifier.weight(0.3f))
            GridItem(icon = Icons.Filled.Home, text = "Chat with Doctor")
            Spacer(Modifier.weight(1f))
            GridItem(icon = Icons.Filled.Settings, text = "Find Technicians")
            Spacer(Modifier.weight(1f))
            GridItem(icon = Icons.Filled.Person, text = "View Articles")
            Spacer(Modifier.weight(0.3f))
        }
        Row(modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .background(color = Color.White)) {
            Spacer(Modifier.weight(0.3f))
            GridItem(icon = Icons.Filled.Search, text = "Buy Medicines")
            Spacer(Modifier.weight(0.9f))
            GridItem(icon = Icons.Filled.Mail, text = "Mail")
            Spacer(Modifier.weight(1f))
            GridItem(icon = Icons.Filled.Phone, text = "Phone")
            Spacer(Modifier.weight(0.3f))
        }
        Row(modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .fillMaxWidth()
            .background(color = Color.White)) {
            GridItem(icon = Icons.Filled.CalendarToday, text = "Calendar")
            Spacer(Modifier.weight(0.7f))
            GridItem(icon = Icons.Filled.CameraAlt, text = "Camera")
            Spacer(Modifier.weight(1f))
            GridItem(icon = Icons.Filled.PlayArrow, text = "Play")
            Spacer(Modifier.weight(0.3f))
        }

        Spacer(modifier = Modifier.height(20.dp))

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

        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 3.dp, bottom = 5.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            elevation = 10.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.card_special), contentDescription = null)
        }

        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            elevation = 10.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.card1), contentDescription = null)
        }

        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            elevation = 10.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.card2), contentDescription = null)
        }

        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            elevation = 10.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.card3), contentDescription = null)
        }

        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            elevation = 10.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.card4), contentDescription = null)
        }

        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            elevation = 10.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.card5), contentDescription = null)
        }

        Card(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            elevation = 10.dp,
        ) {
            Image(painter = painterResource(id = R.drawable.card6), contentDescription = null)
        }

        Spacer(modifier = Modifier
            .height(80.dp))
    }
}

@Composable
fun ChatScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Music View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun ExploreScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {

    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
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
    val user = profileViewModel.user.value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(110.dp)
            .background(Color.White, RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (user != null) {
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
    val user = profileViewModel.user.value

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
                    .size(32.dp),
                imageVector = item.icon,
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
            icon = appIcons.Person,
            title = "Account",
            subTitle = "Manage your account"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.ThumbUp,
            title = "My Dashboard",
            subTitle = "Manage additional account settings"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.ShoppingCart,
            title = "Orders",
            subTitle = "Orders history"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Person,
            title = "Addresses",
            subTitle = "Your saved addresses"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Star,
            title = "Saved Cards",
            subTitle = "Your saved debit/credit cards"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Settings,
            title = "Settings",
            subTitle = "App notification settings"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Info,
            title = "Help Center",
            subTitle = "FAQs and customer support"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Build,
            title = "Offers and Coupons",
            subTitle = "Offers and coupon codes for you"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Abc,
            title = "Sign Out",
            subTitle = "Sign out from the app"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Abc,
            title = "",
            subTitle = ""
        )
    )
}

data class OptionsData(val icon: ImageVector, val title: String, val subTitle: String)