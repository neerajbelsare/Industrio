package com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.industrio.R
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.CustomTopAppBarWithBackButton
import com.example.industrio.navigation.TechnicianScreen

@Composable
fun TechnicianForm2(navController: NavController,
                technicianFormViewModel: TechnicianFormViewModel = viewModel()
) {
    val allInputsFilled = technicianFormViewModel.qualificationUrl != "https://drive.google.com/file/d/1jaMTdkE-IxTEHRVsHaDwUNTEHm-U8xVw/view?usp=sharing"
            || technicianFormViewModel.identityUrl != "https://drive.google.com/file/d/1jaMTdkE-IxTEHRVsHaDwUNTEHm-U8xVw/view?usp=sharing"
            || technicianFormViewModel.profileUrl != "https://drive.google.com/file/d/1jaMTdkE-IxTEHRVsHaDwUNTEHm-U8xVw/view?usp=sharing"

    Scaffold(
        topBar = {
            CustomTopAppBarWithBackButton(
                navController = navController,
                appBarTitle = "Doctor Application Form",
                backgroundColor = Color.White
            )
        },
        backgroundColor = Color.White
    ) {
        it
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.technician),
                    contentDescription = "Register as a Doctor",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                    uri?.let {
                        technicianFormViewModel.uploadImageToStorage(it)
                            .addOnSuccessListener { imageUrl ->
                                technicianFormViewModel.saveImageUrlToFirestore(imageUrl.toString(), 1)
                            }
                    }
                }

            val launcher1 =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                    uri?.let {
                        technicianFormViewModel.uploadImageToStorage(it)
                            .addOnSuccessListener { imageUrl ->
                                technicianFormViewModel.saveImageUrlToFirestore(imageUrl.toString(), 2)
                            }
                    }
                }

            val launcher2 =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                    uri?.let {
                        technicianFormViewModel.uploadImageToStorage(it)
                            .addOnSuccessListener { imageUrl ->
                                technicianFormViewModel.saveImageUrlToFirestore(imageUrl.toString(), 3)
                            }
                    }
                }

            Text(
                text = "Upload Government-verified Document for Identity and Address Verification",
                modifier = Modifier
                    .padding(start = 3.dp, bottom = 10.dp),
                color = Color(0xFF474747),
                fontFamily = FontFamily(
                    Font(
                        R.font.googlesansdisplay_bold,
                        FontWeight.Bold
                    )
                )
            )
            Image(
                painter = painterResource(id = R.drawable.file_upload),
                contentDescription = "Upload Document",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { launcher.launch("*/*") }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Upload Document Proof for Educational Qualification",
                modifier = Modifier
                    .padding(start = 3.dp, bottom = 10.dp),
                color = Color(0xFF474747),
                fontFamily = FontFamily(
                    Font(
                        R.font.googlesansdisplay_bold,
                        FontWeight.Bold
                    )
                )
            )
            Image(
                painter = painterResource(id = R.drawable.file_upload),
                contentDescription = "Upload Document",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { launcher1.launch("*/*") }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Profile Photo",
                modifier = Modifier
                    .padding(start = 3.dp, bottom = 10.dp),
                color = Color(0xFF474747),
                fontFamily = FontFamily(
                    Font(
                        R.font.googlesansdisplay_bold,
                        FontWeight.Bold
                    )
                )
            )
            Image(
                painter = painterResource(id = R.drawable.defaultprofile),
                contentDescription = "Upload Document",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(132.dp)
                    .clip(CircleShape)
                    .border(7.dp, Color(0xFFF7F7F7), CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .scale(1f, 1f)
                    .clickable { launcher2.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(28.dp))

            if (technicianFormViewModel.isLoading) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RoundedCornerShape(40.dp),
                    enabled = allInputsFilled
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp),
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                }
            } else {
                Button(
                    onClick = { navController.navigate(TechnicianScreen.TechnicianConfirm.route) },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RoundedCornerShape(40.dp),
                    enabled = allInputsFilled
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

