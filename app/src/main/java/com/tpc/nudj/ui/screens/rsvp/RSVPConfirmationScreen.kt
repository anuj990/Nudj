package com.tpc.nudj.ui.screens.rsvp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.components.ConfirmationCard
import com.tpc.nudj.ui.components.TopBar
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme

import java.time.LocalTime

@Composable

fun RSVPConfirmationScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.landingPageAppTitle)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        TopBar(
            onBackClicked = {}
        )

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Confirmed!",
            style = MaterialTheme.typography.displayMedium.copy(
                fontFamily = ClashDisplay,
                color = Color.White
            ),
        )

        Spacer(modifier = Modifier.height(24.dp))

        ConfirmationCard(
            clubName = "The Programming Club",
            eventName = "ABCDEFGHXX",
            venue = "CC 2nd Floor",
            date = 17,
            month = 10,
            time = LocalTime.of(16,30)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "See in Calendar",
            color = Color.White,
            textDecoration = TextDecoration.Underline,
            fontSize = 18.sp
        )
    }


}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RSVPConfirmationScreenPreview() {
    NudjTheme {
        RSVPConfirmationScreen()
    }

}
