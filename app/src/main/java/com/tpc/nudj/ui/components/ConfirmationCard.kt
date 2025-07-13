package com.tpc.nudj.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.R
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import java.time.LocalTime

@Composable
fun ConfirmationCard(
    clubName: String,
    eventName: String,
    venue: String,
    date: Int,
    month:Int,
    time: LocalTime,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            ),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF1E6)

        )

    ) {
        Column (
            modifier = Modifier
                .padding(24.dp)
        ){
            Box(
                modifier = Modifier
                    .background(Color(0xFF381F71), RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(clubName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Event name- $eventName",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Black
                )
            )

            Divider(modifier = Modifier
                .padding(vertical = 16.dp),
                color = Color.Black
            )

            Row(){
                Column (){
                    Text("Venue", color = Color(0xFFFF5B00), fontWeight = FontWeight.Bold)
                    Text("Room no: $venue", fontSize = 16.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Date and Time", color = Color(0xFFFF5B00), fontWeight = FontWeight.Bold)
                    Text("${numToMonth(month)} $date,$time", fontSize = 16.sp)
                }
                Image(
                    painter = painterResource(id = R.drawable.confirm_img), // Replace with your asset
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Divider(modifier = Modifier
                .padding(vertical = 16.dp),
                color = Color.Black
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.download),
                        contentDescription = "download",
                    )
                    Text("Download", modifier = Modifier.padding(start = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Share", modifier = Modifier.padding(end = 8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = "share",
                    )

                }
            }


        }
    }
}

fun numToMonth(num:Int): String{
    when{
        num == 1 -> return "Jan"
        num == 2 -> return "Feb"
        num == 3 -> return "Mar"
        num == 4 -> return "Apr"
        num == 5 -> return "May"
        num == 6 -> return "June"
        num == 7 -> return "July"
        num == 8 -> return "Aug"
        num == 9 -> return "Sept"
        num == 10 -> return "Oct"
        num == 11 -> return "Nov"
        num == 12-> return "Dec"
    }
    return ""
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RSVPConfirmationScreenPreview() {
    NudjTheme {
        ConfirmationCard(
            clubName = "TPC",
            eventName = "ABCDEFGHXX",
            venue = "CC 2nd Floor",
            date = 17 ,
            month = 12,
            time = LocalTime.of(16,30)
        )
    }

}
