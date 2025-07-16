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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.screens.auth.PreHomeScreen.ClubCardState
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.Purple

@Composable
fun ClubNameCard(
    club: ClubCardState,
    baseColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    val backgroundColor = if (club.isSelected) Color.Green else baseColor

    Box(
        modifier = Modifier
            .width(176.dp)
            .height(211.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = club.club.clubName,
            fontFamily = ClashDisplay,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.W600,
            fontSize = 17.8.sp,
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            textAlign = TextAlign.Center,
            maxLines = 4,
            lineHeight = 20.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        )
    }
}

