package com.tpc.nudj.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    email: String = "",
    onEmailChange: (String) -> Unit

) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 44.dp)
    ) {
        Text(
            text = "Email",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 2.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            color = Color(0xFFFF5E00)
        )
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = modifier
                .border(width = 1.8F.dp, color = Color(0xFF3F1872),shape = RoundedCornerShape(16.dp))
                .background(Color(0xFFFF5E00).copy(alpha = 0.1f), shape = RoundedCornerShape(24.dp))
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 44.dp)
    ) {
        Text(
            text = "Password",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 2.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            color = Color(0xFFFF5E00)
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = modifier
                .border(width = 1.8F.dp, color = Color(0xFF3F1872),shape = RoundedCornerShape(16.dp))
                .background(Color(0xFFFF5E00).copy(alpha = 0.1f), shape = RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .height(60.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 28.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
        )
    }
}


@Preview(
    name = "EmailFieldPreview",
    showBackground = true
)
@Composable
fun EmailFieldPreview() {
    var email by remember { mutableStateOf("") }
    EmailField(email = email, onEmailChange = { email = it })
}

@Preview(
    name = "PasswordFieldPreview",
    showBackground = true
)
@Composable
fun PasswordFieldPreview() {
    var password by remember { mutableStateOf("") }
    PasswordField(password = password, onPasswordChange = { password = it })
}
