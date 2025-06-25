package com.tpc.nudj.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tpc.nudj.R
import com.tpc.nudj.ui.theme.LocalAppColors

@Composable
fun NudjLogo(modifier: Modifier = Modifier){
    Box(modifier = modifier,
        contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.nudjlogo),
            contentDescription = "App Logo",
            modifier = Modifier.size(width = 132.dp, height = 59.dp),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                color = LocalAppColors.current.appTitle
            )
        )
    }
}