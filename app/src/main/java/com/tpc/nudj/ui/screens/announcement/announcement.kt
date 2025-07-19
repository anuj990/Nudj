package com.tpc.nudj.ui.screens.announcement

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.R
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.EditTextBackgroundColorLight
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme

val recentAnnouncement = listOf<String>(" "," "," "," ")
@Composable
fun AnnouncementScreen(
    recentannouncements: List<String> = recentAnnouncement,
    onBackClicked: () -> Unit,
    NavigatetoMyClub : () -> Unit
){
    AnnouncementScreenContent(
        recentannouncements = recentannouncements,
        onBackClicked = onBackClicked,
        NavigatetoMyClub = NavigatetoMyClub
    )
}

@Composable
fun AnnouncementScreenContent(
    recentannouncements: List<String>,
    onBackClicked: () -> Unit,
    NavigatetoMyClub : () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(onBackClicked = onBackClicked)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(620.dp)
                .padding(horizontal = 20.dp, vertical = 15.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(10.dp)
                    ),
            colors = CardDefaults.cardColors(
                containerColor = EditTextBackgroundColorLight,)
        ){
            Text(
                text = "Announcements!",
                modifier = Modifier.padding(vertical = 30.dp, horizontal = 30.dp),
                fontFamily = ClashDisplay,
                fontWeight = FontWeight.W600,
                fontSize = 25.sp,
                lineHeight = 30.sp,
                color = colorResource(R.color.purple_18),
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(recentannouncements){announcement ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 30.dp, bottom = 15.dp)
                    ){
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(LocalAppColors.current.announcementcircle, shape = RoundedCornerShape(25.dp))
                        )
                        Text(
                            text = announcement,
                            modifier = Modifier.padding(start = 5.dp),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                fontWeight = FontWeight.W600
                            ),
                            color = Color.Black
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = NavigatetoMyClub,
            colors = ButtonDefaults.buttonColors(
                containerColor = LocalAppColors.current.appTitle
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "Go to My Clubs",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = ClashDisplay,
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.white_FE)
                )
            )
        }
    }
}

@Composable
fun TopBar(
    onBackClicked:()->Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ){
        IconButton(
            onClick = onBackClicked
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back Arrow",
                tint= LocalAppColors.current.appTitle,
                modifier = Modifier.size(25.dp)
            )
        }
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium.copy(
                fontFamily = ClashDisplay,
                color = LocalAppColors.current.appTitle,
            ),
            modifier= Modifier.align(Alignment.Center)
        )
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AnnouncementScreenPreview(){
    NudjTheme {
        AnnouncementScreenContent(recentannouncements = recentAnnouncement, onBackClicked = {}, NavigatetoMyClub = {})
    }
}
