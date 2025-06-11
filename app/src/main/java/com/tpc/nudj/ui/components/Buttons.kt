package com.tpc.nudj.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDarkModeEnabled: Boolean
) {
    val isSystemDark = isSystemInDarkTheme()
    val useDarkTheme = isDarkModeEnabled && isSystemDark

    val buttonColor = if (useDarkTheme) Purple else Orange
    val contentColor = Color.White
    val disabledBackgroundColor = buttonColor.copy(alpha = 0.6f)
    val disabledTextColor = contentColor.copy(alpha = 0.6f)

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .padding(16.dp)
        ,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor ,
            contentColor = contentColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledTextColor
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = ClashDisplay
            ),
            modifier = modifier
                .padding(horizontal = 16.dp)
        )
    }
}


@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDarkModeEnabled: Boolean
) {
    val isSystemDark = isSystemInDarkTheme()
    val useDarkTheme = isDarkModeEnabled && isSystemDark

    val buttonColor = Color.White
    val contentColor = if (useDarkTheme) Purple else Orange
    val disabledBackgroundColor = buttonColor.copy(alpha = 0.6f)
    val disabledTextColor = contentColor.copy(alpha = 0.6f)

    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .padding(16.dp)
        ,
        border = BorderStroke(1.dp, contentColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = buttonColor ,
            contentColor = contentColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledTextColor
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = ClashDisplay
            ),
            modifier = modifier
                .padding(horizontal = 16.dp)
        )
    }
}


@Composable
fun TertiaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDarkModeEnabled: Boolean
) {
    val isSystemDark = isSystemInDarkTheme()
    val useDarkTheme = isDarkModeEnabled && isSystemDark

    val contentColor = if (useDarkTheme) Color.White else Purple

    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .padding(16.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = TextDecoration.Underline,
                fontFamily = ClashDisplay
            ),
            modifier = modifier
                .padding(horizontal = 16.dp)
        )
    }
}


@Preview(showBackground = true, showSystemUi = false)
@Preview(showBackground = true, showSystemUi = false, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrimaryButtonPreview(modifier: Modifier = Modifier) {
    PrimaryButton(
        text = "Edit",
        onClick = {
            // onClick logic
        },
        enabled = true,
        isDarkModeEnabled = true
    )
}


@Preview(showBackground = true, showSystemUi = false, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, showSystemUi = false)
@Composable
fun SecondaryButtonPreview(modifier: Modifier = Modifier) {
    SecondaryButton(
        text = "Edit",
        onClick = {
            // onClick logic
        },
        enabled = true,
        isDarkModeEnabled = true
    )
}


@Preview(showBackground = true, showSystemUi = false, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TertiaryButtonPreview(modifier: Modifier = Modifier) {
    TertiaryButton(
        text = "Edit",
        onClick = {
            // onClick logic
        },
        enabled = true,
        isDarkModeEnabled = true
    )
}


