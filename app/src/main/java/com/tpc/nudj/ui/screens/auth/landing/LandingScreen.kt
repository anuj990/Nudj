package com.tpc.nudj.ui.screens.auth.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.SecondaryButton
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme


@Composable
fun LandingScreen(
    onClickLogin: ()-> Unit,
    onClickSignup: ()-> Unit
){
    val colors= LocalAppColors.current

    Box(
        modifier =Modifier
            .fillMaxSize()
            .background(colors.landingPageBackground),

    ){
        Text(
            text="Nudj",
            fontFamily = ClashDisplay,
            fontWeight = FontWeight.SemiBold,
            fontSize = 110.sp,
            color = colors.landingPageAppTitle,
            modifier = Modifier.align(Alignment.Center)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        ){
            SecondaryButton(
                text = "Login",
                onClick = onClickLogin,
                enabled = true,
                isDarkModeEnabled = true
            )


            PrimaryButton(
                text = "Sign up",
                onClick = onClickSignup,
                enabled = true,
                isDarkModeEnabled = true

            )
        }
    }

}
@Preview(showBackground = true, showSystemUi = false)
@Preview(showBackground = true, showSystemUi = false, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LandingScreenPreview(){
    NudjTheme {
        LandingScreen(
            {},{}
        )
    }
}


