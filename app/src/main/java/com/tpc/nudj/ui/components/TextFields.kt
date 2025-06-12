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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme

@Composable
fun NudjTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    onValueChange: (String) -> Unit
) {
    val colors = LocalAppColors.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 44.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .align(Alignment.Start),
            fontSize = 18.sp,
            fontFamily = ClashDisplay,
            color = colors.viewText
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .border(width = 1.8F.dp, color = colors.editTextBorder,shape = RoundedCornerShape(16.dp))
                .background(colors.editTextBackground, shape = RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .height(60.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, fontFamily = ClashDisplay),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}


@Preview(name = "Text Field Light Mode", showBackground = true)
@Preview(name = "Text Field Dark Mode", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF3F1872)
@Composable
fun NudjTextFieldPreview() {
    NudjTheme {
        NudjTextField(
            label = "Text Field"
        ) {}
    }
}
