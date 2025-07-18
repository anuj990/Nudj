package com.tpc.nudj.ui.screens.auth.clubRegistration.clubRegistrationScreen2

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.screens.auth.clubRegistration.ClubRegistrationUIState

@Composable
fun ClubsRegisterScreen2(
    uiState: ClubRegistrationUIState,
    onImagePicked: (Uri?) -> Unit,
    onAdditionalDetailsInput: (String) -> Unit,
) {
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
            Spacer(modifier = Modifier.padding(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 44.dp)
            ) {
                Text(
                    text = "Club Logo",
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
                                        text = "Club Logo",
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
                                    contentDescription = "club logo",
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
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 44.dp)
            ) {
                Text(
                    text = "Additional Details",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                NudjTextField(
                    value = uiState.clubAdditionalDetails,
                    onValueChange = { onAdditionalDetailsInput(it) },
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.8F.dp,
                            LocalAppColors.current.editTextBorder,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
        }
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Screen2Preview() {
    NudjTheme {
        ClubsRegisterScreen2(
            uiState = ClubRegistrationUIState(),
            onImagePicked = {},
            onAdditionalDetailsInput = {}
        )
    }
}