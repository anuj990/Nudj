package com.tpc.nudj.ui.screens.auth.detailsInput

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.NudjLogo
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme


@Composable
fun DetailsConfirmationScreen(viewModel: UserDetailViewModel = hiltViewModel()) {
    val userDetails = viewModel.userDetailsUIState.collectAsState()
    DetailsConfirmationScreenLayout(userDetails.value)
}

@Composable
fun DetailsConfirmationScreenLayout(details: UserDetailsUIState) {

    val clashDisplayFont = FontFamily(
        Font(R.font.clash_display_font, weight = FontWeight.Medium)
    )
    Scaffold(
        containerColor = LocalAppColors.current.background
    ) {paddingValues->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NudjLogo()

            Spacer(modifier = Modifier.padding(top = 74.dp))

            Text(
                text = "You Entered:",
                fontFamily = clashDisplayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .padding(33.dp)
                    .width(345.dp)
                    .height(323.dp)
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            Color.White
                        } else {
                            LocalAppColors.current.editTextBackground
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(start = 23.dp, top = 29.dp)
                ) {
                    Text(
                        text = "First Name:   ${details.firstName}",
                        color = Color(0xFF3F1872),
                        fontSize = 20.sp,
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "Last Name:   ${details.lastName}",
                        color = Color(0xFF3F1872),
                        fontSize = 20.sp,
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "Branch:   ${details.branch}",
                        color = Color(0xFF3F1872),
                        fontSize = 20.sp,
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "Batch:   ${details.batch}",
                        color = Color(0xFF3F1872),
                        fontSize = 20.sp,
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        " "
                    },
                    modifier = Modifier
                        .width(131.dp)
                        .height(54.dp).border(
                            width = 2.dp,
                            color = Color(0xFFFF5E00),
                            shape = RoundedCornerShape(31.5.dp)

                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Edit",
                        fontSize = 20.sp,
                        fontFamily = clashDisplayFont,
                        color = Color(0xFFFF5E00),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.width(29.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .width(162.dp)
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5E00))
                ) {
                    Text(
                        text = "Save",
                        fontSize = 20.sp,
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailsConfirmationScreenPreview() {
    NudjTheme(darkTheme = true) {
        DetailsConfirmationScreenLayout(
            details = UserDetailsUIState(
                firstName = "Anshu",
                lastName = "Kashyap",
                branch = "ECE",
                batch = "2024"
            )
        )
    }
}
