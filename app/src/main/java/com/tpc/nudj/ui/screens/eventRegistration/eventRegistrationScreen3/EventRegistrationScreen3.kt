package com.tpc.nudj.ui.screens.eventRegistration.eventRegistrationScreen3

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tpc.nudj.R
import com.tpc.nudj.ui.screens.eventRegistration.EventRegistrationUiState
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import java.time.LocalDateTime
import java.time.Month

@Composable
fun EventRegisterScreen3(
    uiState: EventRegistrationUiState,
    onImagePicked: (Uri?) -> Unit,
    changeBatchSelection: (String, Set<String>) -> Unit,
) {
    val currentYear = LocalDateTime.now().year
    val currentMonth = LocalDateTime.now().month
    val batchList =
        if (currentMonth >= Month.AUGUST) {
            setOf(
                "All Batches",
                currentYear.toString(),
                (currentYear - 1).toString(),
                (currentYear - 2).toString(),
                (currentYear - 3).toString()
            )
        } else {
            setOf(
                "All Batches",
                (currentYear - 1).toString(),
                (currentYear - 2).toString(),
                (currentYear - 3).toString(),
                (currentYear - 4).toString()
            )
        }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            imageUri = uri
            onImagePicked(uri)
        }
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.padding(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 44.dp)
            ) {
                Text(
                    text = "For which batch?",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                Spacer(modifier = Modifier.padding(4.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    batchList.forEachIndexed { index, batch ->
                        val isSelected = uiState.selectedBatches.contains(batch)
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .border(
                                    width = 1.8F.dp,
                                    LocalAppColors.current.editTextBorder,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    changeBatchSelection(batch, batchList)
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(if (isSelected) Orange else LocalAppColors.current.editTextBackground)
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = batch,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = ClashDisplay,
                                        color = if (isSystemInDarkTheme() || isSelected) Color.White else Color.Black.copy(
                                            alpha = 0.4f
                                        )
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }

            }
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 44.dp)
            ) {
                Text(
                    text = "Event Banner",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                Card(
                    modifier = Modifier
                        .border(
                            width = 1.8F.dp,
                            LocalAppColors.current.editTextBorder,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = LocalAppColors.current.editTextBackground
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        if (imageUri == null) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = if (isSystemInDarkTheme()) painterResource(R.drawable.frame3light) else painterResource(
                                        R.drawable.frame3
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Event Banner",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontFamily = ClashDisplay,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    )
                                    Spacer(modifier = Modifier.padding(20.dp))
                                    Button(
                                        modifier = Modifier,
                                        onClick = {
                                            pickImageLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Orange
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(
                                            text = "Upload",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontFamily = ClashDisplay, color = Color.White
                                            ),
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .data(imageUri)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "event banner",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        if (imageUri != null) {
                            IconButton(
                                onClick = {
                                    imageUri = null
                                    onImagePicked(null)
                                },
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = Orange,
                                ),
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "cancel the image selection",
                                    tint = Color.White,
                                    modifier = Modifier.size(23.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EventScreen3Preview() {
    NudjTheme {
        EventRegisterScreen3(
            onImagePicked = {},
            uiState = EventRegistrationUiState(),
            changeBatchSelection = { _, _ -> }
        )
    }
}