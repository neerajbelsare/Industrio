package com.example.industrio.navigation

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.industrio.screens.SigninScreen.SignInScreen
import com.example.preecure.screens.SignupScreen.SignUpScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.industrio.LoaderIntro
import com.example.industrio.OnBoardingData
import com.example.industrio.screens.HomeScreen.Home
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import com.example.industrio.R

//@Composable
//fun isUserSignedIn(): Boolean {
//    val currentUser = FirebaseAuth.getInstance().currentUser
//    return currentUser != null
//}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph (navController: NavHostController){
    val isSignedIn = isUserSignedIn()
    lateinit var startDest: String
    if (isSignedIn) {
        startDest = Screens.Home.route
    } else {
        startDest = Screens.Onboard.route
    }

    NavHost(
        navController = navController,
        startDestination = startDest)
    {
        composable(route = Screens.Onboard.route){
            OnBoardScreen(navController)
        }
        composable(route = Screens.Signin.route){
            SignInScreen(navController)
        }
        composable(route = Screens.Signup.route){
            SignUpScreen(navController)
        }
        composable(route = Screens.Home.route){
            Home(navController)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun OnBoardScreen(navController: NavController) {
    val items = ArrayList<OnBoardingData>()

    items.add(
        OnBoardingData(
            R.raw.intro_4,
            "On Demand Service platform",
            "Empower your small industry with hassle-free mechanical services. Explore our on-demand platform today."
        )
    )

    items.add(
        OnBoardingData(
            R.raw.intro_2,
            "Trusted Technicians",
            "Reliable technicians at your service. Solve your problems with trusted expertise."
        )
    )

    items.add(
        OnBoardingData(
            R.raw.intro_3,
            "Discussion Forum",
            "Join our Discussion Forum for problem-solving and community support."
        )
    )
    val pagerState = rememberPagerState(
        pageCount = items.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    OnBoardingPager(
        item = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        navController
    )
}

@ExperimentalPagerApi
@Composable
fun OnBoardingPager(
    item: List<OnBoardingData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(10f)
            ) { page ->
                Column(
                    modifier = Modifier
                        .padding(60.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /* Image(
                     painter = painterResource(id = item[page].image),
                     contentDescription = item[page].title,
                     modifier = Modifier
                         .height(250.dp)
                         .fillMaxWidth()
                 )*/
                    LoaderIntro(
                        modifier = Modifier
                            .size(320.dp)
                            .fillMaxWidth()
                            .paddingFromBaseline(60.dp, 10.dp)
                            .align(alignment = Alignment.CenterHorizontally), item[page].image
                    )
                    Text(
                        text = item[page].title,
                        modifier = Modifier.padding(top = 50.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize =23.sp,
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = item[page].desc,
                        modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize =17.sp
                    )
                }
            }
            PagerIndicator(item.size, pagerState.currentPage)

        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomSection(pagerState.currentPage,navController)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun rememberPagerState(
    @IntRange(from = 0) pageCount: Int,
    @IntRange(from=0) initialPage: Int = 0,
    @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
    @IntRange(from = 1) initialOffscreenLimit: Int = 1,
    infiniteLoop: Boolean = false
): PagerState = rememberSaveable(
    saver = PagerState.Saver
) {
    PagerState(
        pageCount = pageCount,
        currentPage = initialPage,
        currentPageOffset = initialPageOffset,
        offscreenLimit = initialOffscreenLimit,
        infiniteLoop = infiniteLoop
    )
}

@Composable
fun PagerIndicator(
    size: Int,
    currentPage: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 60.dp, bottom = 1.dp)

    ) {
        repeat(size) {
            Indicator(isSelected = it == currentPage)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 30.dp else 15.dp)

    Box(
        modifier = Modifier
            .padding(3.dp)
            .height(5.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) Color.Green else Color.LightGray.copy(alpha = 0.5f)
            )
    )
}

@Composable
fun BottomSection(currentPager: Int, navController: NavController) {

    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (currentPager != 2) Arrangement.SpaceBetween else Arrangement.Center
    ) {
        if (currentPager == 2) {

            OutlinedButton(
                onClick = {navController.navigate(Screens.Signin.route)},
                shape = RoundedCornerShape(60),
                colors = ButtonDefaults.buttonColors(Color.Cyan)
            ) {
                Text(
                    text = "Get Started",

                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 40.dp),

                    color = Color.Black,

                )
            }

        } else {
            SkipNextButton(text = "Skip", modifier = Modifier.padding(start = 20.dp), navController = navController)
//                SkipNextButton(text = "Next", modifier = Modifier.padding(end = 20.dp))
        }
    }
}

@Composable
fun SkipNextButton(text: String, modifier: Modifier, navController: NavController) {
    Text(
        text = text,
        color = Color.Black,
        modifier = modifier
            .clickable { navController.navigate(Screens.Signin.route) },
        fontSize = 18.sp,
        style = MaterialTheme.typography.h1,
        fontWeight = FontWeight.Medium
    )


//    TextButton(onClick = {navController.navigate(Screens.Signup.route)}) {
//        Text(text = text)

    }

