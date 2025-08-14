package com.tpc.nudj.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tpc.nudj.R
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    haveToGoBack:Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium.copy(
                fontFamily = ClashDisplay,
                color = LocalAppColors.current.appTitle
            ),
            modifier = Modifier.align(Alignment.Center)
        )
        if(haveToGoBack) {
            IconButton(
                onClick = { onBackClicked() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.back_navigation),
                    modifier = Modifier.size(25.dp),
                    tint = LocalAppColors.current.appTitle
                )
            }
        }
    }
}