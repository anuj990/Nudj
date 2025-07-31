package com.tpc.nudj.ui.screens.eventRegistration.eventRegistrationScreen4

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.screens.eventRegistration.EventRegistrationFAQ
import com.tpc.nudj.ui.screens.eventRegistration.EventRegistrationUiState
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import kotlinx.coroutines.delay

@Composable
fun EventRegisterScreen4(
    uiState: EventRegistrationUiState,
    addQuestion: (EventRegistrationFAQ) -> Unit,
    removeQuestion: (Int) -> Unit
) {
    var showQuestionDialog by remember { mutableStateOf(false) }
    var showAnswerDialog by remember { mutableStateOf(false) }
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    LaunchedEffect(uiState.faqs.size) {
        if (uiState.faqs.isNotEmpty()) {
            lazyListState.animateScrollToItem(uiState.faqs.size)
        }
    }
    if (showQuestionDialog) {
        val questionFocusRequester = remember { FocusRequester() }
        Dialog(
            onDismissRequest = { showQuestionDialog = false },
            content = {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = LocalAppColors.current.editTextBackground
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.padding(4.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Enter your question",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontFamily = ClashDisplay,
                                    color = Orange
                                ),
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            NudjTextField(
                                value = question,
                                onValueChange = {
                                    question = it
                                },
                                singleLine = false,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(questionFocusRequester)
                                    .border(
                                        width = 1.8F.dp,
                                        LocalAppColors.current.editTextBorder,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            )
                            LaunchedEffect(Unit) {
                                delay(100)
                                questionFocusRequester.requestFocus()
                            }
                        }
                        Spacer(modifier = Modifier.padding(6.dp))
                        Row {
                            TextButton(onClick = {
                                showQuestionDialog = false
                            }) {
                                Text(
                                    "OK",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Orange
                                    )
                                )
                            }
                        }
                    }
                }
            }
        )
    }

    if (showAnswerDialog) {
        val answerFocusRequester = remember { FocusRequester() }
        Dialog(
            onDismissRequest = { showAnswerDialog = false },
            content = {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = LocalAppColors.current.editTextBackground
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.padding(4.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Q. $question",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = ClashDisplay,
                                    color = Orange
                                ),
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            NudjTextField(
                                value = answer,
                                onValueChange = {
                                    answer = it
                                },
                                singleLine = false,
                                placeholder = {
                                    Text(
                                        text = "Enter your answer to the question",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontFamily = ClashDisplay,
                                            color = if (!isSystemInDarkTheme()) Color.Black.copy(
                                                alpha = 0.4f
                                            ) else Color.White.copy(
                                                alpha = 0.5f
                                            ),
                                            fontSize = 20.sp
                                        ),
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(answerFocusRequester)
                                    .border(
                                        width = 1.8F.dp,
                                        LocalAppColors.current.editTextBorder,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            )
                            LaunchedEffect(Unit) {
                                delay(100)
                                answerFocusRequester.requestFocus()
                            }
                        }
                        Spacer(modifier = Modifier.padding(6.dp))
                        Row {
                            TextButton(onClick = {
                                showAnswerDialog = false
                            }) {
                                Text(
                                    "OK",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Orange
                                    )
                                )
                            }
                        }
                    }
                }
            }
        )
    }

    LazyColumn(
        state = lazyListState,
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
                    text = "FAQs",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(LocalAppColors.current.editTextBackground)
                            .border(
                                width = 1.8F.dp,
                                LocalAppColors.current.editTextBorder,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(top = 6.dp, bottom = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showQuestionDialog = true },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Spacer(modifier = Modifier.padding(25.dp))
                            if (question == "") {
                                Text(
                                    text = "Question?",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontFamily = ClashDisplay,
                                        color = if (!isSystemInDarkTheme()) Color.Black.copy(
                                            alpha = 0.4f
                                        ) else Color.White.copy(
                                            alpha = 0.5f
                                        ),
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            } else {
                                Text(
                                    text = question,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = TextStyle(fontSize = 20.sp),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(25.dp))
                        }
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = LocalAppColors.current.editTextBorder,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { if (question != "") showAnswerDialog = true },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Spacer(modifier = Modifier.padding(25.dp))
                            if (answer == "" || question == "") {
                                Text(
                                    text = "Answer!!",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontFamily = ClashDisplay,
                                        color = if (!isSystemInDarkTheme()) Color.Black.copy(
                                            alpha = 0.4f
                                        ) else Color.White.copy(
                                            alpha = 0.5f
                                        ),
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            } else {
                                Text(
                                    text = answer,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = TextStyle(fontSize = 20.sp),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(25.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            if (question != "" && answer != "") {
                                addQuestion(
                                    EventRegistrationFAQ(
                                        question = question,
                                        answer = answer
                                    )
                                )
                                question = ""
                                answer = ""
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Orange
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add faqs",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                uiState.faqs.forEachIndexed { index, faq ->
                    val visible = remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        visible.value = true
                    }

                    AnimatedVisibility(
                        visible = visible.value,
                        enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = ClashDisplay,
                                        color = Orange
                                    ),
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                                fontFamily = ClashDisplay,
                                                color = Orange
                                            )
                                        ) {
                                            append("Q:  ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                                fontFamily = ClashDisplay,
                                                color = Orange
                                            )
                                        ) {
                                            append(faq.question + "\n\n")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                                fontFamily = ClashDisplay,
                                                color = Orange
                                            )
                                        ) {
                                            append("A:  ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                                fontFamily = ClashDisplay,
                                                color = Orange
                                            )
                                        ) {
                                            append(faq.answer)
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp),
                                    textAlign = TextAlign.Start
                                )

                                IconButton(
                                    onClick = { removeQuestion(index) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete the question",
                                        tint = Orange,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))

                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Orange,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun EventScreen4Preview() {
    NudjTheme {
        EventRegisterScreen4(
            uiState = EventRegistrationUiState(),
            addQuestion = {},
            removeQuestion = {}
        )
    }
}