package com.example.industrio.screens.AccountNavScreens.ProfileScreen.CompanyForm

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.industrio.R
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.CustomTopAppBarWithBackButton
import com.example.industrio.navigation.CompanyScreen

@Composable
fun CompanyForm2(navController: NavController,
             companyFormViewModel: CompanyFormViewModel = viewModel()
) {
    val allInputsFilled = companyFormViewModel.imageUrl != "https://drive.google.com/file/d/1jaMTdkE-IxTEHRVsHaDwUNTEHm-U8xVw/view?usp=sharing"
            || companyFormViewModel.proofUrl != "https://drive.google.com/file/d/1jaMTdkE-IxTEHRVsHaDwUNTEHm-U8xVw/view?usp=sharing"

    Scaffold(
        topBar = {
            CustomTopAppBarWithBackButton(
                navController = navController,
                appBarTitle = "Company Application Form",
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
                    painter = painterResource(id = R.drawable.company),
                    contentDescription = "Register as a Lab",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                    uri?.let {
                        companyFormViewModel.uploadImageToStorage(it)
                            .addOnSuccessListener { imageUrl ->
                                companyFormViewModel.saveImageUrlToFirestore(imageUrl.toString(), 1)
                            }
                    }
                }

            val launcher1 =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                    uri?.let {
                        companyFormViewModel.uploadImageToStorage(it)
                            .addOnSuccessListener { imageUrl ->
                                companyFormViewModel.saveImageUrlToFirestore(imageUrl.toString(), 2)
                            }
                    }
                }

            Text(
                text = "Upload a Picture of your Lab",
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
                text = "Upload Document Proof for additional verification",
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

            if (companyFormViewModel.isLoading) {
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
                    onClick = { navController.navigate(CompanyScreen.CompanyConfirm.route) },

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

