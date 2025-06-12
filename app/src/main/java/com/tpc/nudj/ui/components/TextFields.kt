package com.tpc.nudj.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    email: String = "",
    onEmailChange: (String) -> Unit

) {
    val colors = LocalAppColors.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 44.dp)
    ) {
        Text(
            text = "Email",
            modifier = Modifier
                .align(Alignment.Start),
            fontSize = 18.sp,
            fontFamily = ClashDisplay,
            color = colors.viewText
        )
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = modifier
                .border(width = 1.8F.dp, color = colors.editTextBorder,shape = RoundedCornerShape(16.dp))
                .background(colors.editTextBackground, shape = RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .height(60.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    password: String = "",
    onPasswordChange: (String) -> Unit
) {
    val colors = LocalAppColors.current
    var passwordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 44.dp)
    ) {
        Text(
            text = "Password",
            modifier = Modifier
                .align(Alignment.Start) ,
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            fontFamily = ClashDisplay,
            color = colors.viewText
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = modifier
                .border(width = 1.8F.dp, colors.editTextBorder,shape = RoundedCornerShape(16.dp))
                .background(colors.editTextBackground, shape = RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .height(60.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Text(
                    text = if (passwordVisible) "Hide" else "Show",
                    color = colors.viewText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { passwordVisible = !passwordVisible }
                )
            }
        )
    }
}


@Preview(
    name = "EmailFieldPreview-LightTheme",
    showBackground = true
)
@Preview(
    name = "EmailFieldPreview-DarkTheme",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF3F1872
)
@Composable
fun EmailFieldPreview() {
    NudjTheme {
        var email by remember { mutableStateOf("") }
        EmailField(email = email, onEmailChange = { email = it })
    }
}

@Preview(
    name = "PasswordFieldPreview-LightTheme",
    showBackground = true
)
@Preview(
    name = "PasswordFieldPreview-DarkTheme",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF3F1872
)
@Composable
fun PasswordFieldPreview() {
    NudjTheme {
        var password by remember { mutableStateOf("") }
        PasswordField(password = password, onPasswordChange = { password = it })
    }
}
