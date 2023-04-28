package com.example.industrio

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.industrio.ui.theme.IndustrioTheme
import com.example.industrio.navigation.nav_graph.SetupNavGraph
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        FirebaseApp.initializeApp(this)
        setContent {
            IndustrioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SetupNavGraph(navController = rememberNavController())
                }
            }
        }
    }
}



//    @ExperimentalPagerApi
//    @Preview(showBackground = true)
//    @Composable
//    fun PreviewFunction() {
//
//        val navController = rememberNavController()
//
//        Surface(modifier = Modifier.fillMaxSize()) {
//            MainFunction(navController = navController)
//        }
//    }
//
//
//    @ExperimentalPagerApi
//    @Composable
//    fun OnBoardScreen(navController: NavController) {
//        val items = ArrayList<OnBoardingData>()
//
//        items.add(
//            OnBoardingData(
//                R.raw.intro_4,
//                "Mechanical Service platform",
//                "Empower your small industry with hassle-free mechanical services. Explore our on-demand platform today."
//            )
//        )
//
//        items.add(
//            OnBoardingData(
//                R.raw.intro_2,
//                "Trusted Technicians",
//                "Reliable technicians at your service. Solve your problems with trusted expertise."
//            )
//        )
//
//        items.add(
//            OnBoardingData(
//                R.raw.intro_3,
//                "Discussion Forum",
//                "Join our Discussion Forum for problem-solving and community support."
//            )
//        )
//        val pagerState = rememberPagerState(
//            pageCount = items.size,
//            initialOffscreenLimit = 2,
//            infiniteLoop = false,
//            initialPage = 0
//        )
//
//        OnBoardingPager(
//            item = items,
//            pagerState = pagerState,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color = Color.White),
//            navController
//        )
//    }
//
//    @ExperimentalPagerApi
//    @Composable
//    fun OnBoardingPager(
//        item: List<OnBoardingData>,
//        pagerState: PagerState,
//        modifier: Modifier = Modifier,
//        navController: NavController
//    ) {
//        Box(modifier = modifier) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                HorizontalPager(
//                    state = pagerState
//                ) { page ->
//                    Column(
//                        modifier = Modifier
//                            .padding(60.dp)
//                            .fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        /* Image(
//                         painter = painterResource(id = item[page].image),
//                         contentDescription = item[page].title,
//                         modifier = Modifier
//                             .height(250.dp)
//                             .fillMaxWidth()
//                     )*/
//                        LoaderIntro(
//                            modifier = Modifier
//                                .size(320.dp)
//                                .fillMaxWidth()
//                                .paddingFromBaseline(60.dp, 10.dp)
//                                .align(alignment = Alignment.CenterHorizontally), item[page].image
//                        )
//                        Text(
//                            text = item[page].title,
//                            modifier = Modifier.padding(top = 50.dp),
//                            color = Color.Black,
//                            fontWeight = FontWeight.Bold,
//                            fontSize =23.sp,
//                            textAlign = TextAlign.Center,
//                        )
//
//                        Text(
//                            text = item[page].desc,
//                            modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
//                            color = Color.Black,
//                            textAlign = TextAlign.Center,
//                                    fontSize =17.sp
//                        )
//                    }
//                }
//
//                PagerIndicator(item.size, pagerState.currentPage)
//            }
//            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
//                BottomSection(pagerState.currentPage,navController)
//            }
//        }
//    }
//
//    @ExperimentalPagerApi
//    @Composable
//    fun rememberPagerState(
//        @IntRange(from = 0) pageCount: Int,
//        @IntRange(from=0) initialPage: Int = 0,
//        @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
//        @IntRange(from = 1) initialOffscreenLimit: Int = 1,
//        infiniteLoop: Boolean = false
//    ): PagerState = rememberSaveable(
//        saver = PagerState.Saver
//    ) {
//        PagerState(
//            pageCount = pageCount,
//            currentPage = initialPage,
//            currentPageOffset = initialPageOffset,
//            offscreenLimit = initialOffscreenLimit,
//            infiniteLoop = infiniteLoop
//        )
//    }
//
//    @Composable
//    fun PagerIndicator(
//        size: Int,
//        currentPage: Int
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.padding(top = 60.dp)
//        ) {
//            repeat(size) {
//                Indicator(isSelected = it == currentPage)
//            }
//        }
//    }
//
//    @Composable
//    fun Indicator(isSelected: Boolean) {
//        val width = animateDpAsState(targetValue = if (isSelected) 25.dp else 10.dp)
//
//        Box(
//            modifier = Modifier
//                .padding(3.dp)
//                .height(10.dp)
//                .width(width.value)
//                .clip(CircleShape)
//                .background(
//                    if (isSelected) Color.Blue else Color.LightGray.copy(alpha = 0.5f)
//                )
//        )
//    }
//
//    @Composable
//    fun BottomSection(currentPager: Int, navController: NavController
//    ) {
//
//        Row(
//            modifier = Modifier
//                .padding(bottom = 20.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = if (currentPager != 2) Arrangement.SpaceBetween else Arrangement.Center
//        ) {
//            if (currentPager == 2) {
//
//                OutlinedButton(
//                    onClick = {
////                        navController.navigate(Screens.Signin.route)
////                        NavGraph(navController = navController)
////                        navigateToScreen()
//                         navController.navigate(Screens.Signin.route)
//                              },
//                    shape = RoundedCornerShape(50),
//                    colors = ButtonDefaults.buttonColors(contentColor = Color.Black)
//                ) {
//                    Text(
//                        text = "Get Started",
//                        modifier = Modifier
//                            .padding(vertical = 8.dp, horizontal = 40.dp),
//                        color = Color.Black
//                    )
//                }
//
//            } else {
//                SkipNextButton(text = "Skip", modifier = Modifier.padding(start = 20.dp), navController = navController)
////                SkipNextButton(text = "Next", modifier = Modifier.padding(end = 20.dp))
//            }
//        }
//    }
//
//    @Composable
//    fun SkipNextButton(text: String, modifier: Modifier, navController: NavController) {
//        Text(
//            text = text,
//            color = Color.Black,
//            modifier = modifier
//                .clickable { navController.navigate(Screens.Signin.route) },
//            fontSize = 18.sp,
//            style = MaterialTheme.typography.h1,
//            fontWeight = FontWeight.Medium
//        )
//    }
