package com.tpc.nudj.ui.screens.auth.detailsInput

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tpc.nudj.ui.theme.NudjTheme

@Composable
fun DetailsInputScreen(){

}

@Composable
private fun DetailsInputScreenLayout(){

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsInputScreenPreview(){
    NudjTheme {
        DetailsInputScreenLayout()
    }
}