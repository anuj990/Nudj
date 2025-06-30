package com.tpc.nudj.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.screens.auth.PreHomeScreen.ClubCardState

import com.tpc.nudj.ui.theme.ClashDisplay

import com.tpc.nudj.ui.theme.Purple

@Composable
fun ClubNameCard(
    club: ClubCardState,
    onClick: () -> Unit
) {
    val backgroundColor = if (club.isSelected) Color.Green else Purple

    Box(
        modifier = Modifier
            .width(176.dp)
            .height(211.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = club.clubName,
                fontFamily = ClashDisplay,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}


data class clubDetails(
    val clubName : List<String>,
    val clubCategory : String
)


@Composable
fun categoryName( name : String){
    Text(
        text = name,
        fontFamily = ClashDisplay,
        fontWeight = FontWeight.W500,
        style = MaterialTheme.typography.headlineSmall,
        fontSize = 22.sp,
        lineHeight = 20.sp,
        color = Purple,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}