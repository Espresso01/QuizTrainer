package ru.fefu.quiztrainer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

@Composable
fun QuizRoot(state: State, onEvent: (Actions) -> Unit) {
    when (state.screen) {
        Screens.Start ->
            WelcomeScreen { onEvent(Actions.Begin) }

        Screens.Quiz -> {
            val q = state.items[state.current]
            QuestionScreen(
                index = state.current + 1,
                total = state.items.size,
                question = q,
                selectedIndex = state.selected,
                onSelect = { onEvent(Actions.Pick(it)) },
                onNext = { onEvent(Actions.Continue) }
            )
        }

        Screens.End ->
            ResultScreen(
                correct = state.correct,
                total = state.items.size,
                onRestart = { onEvent(Actions.Repeat) }
            )
    }
}

@Composable
private fun WelcomeScreen(onStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.spacedBy(100.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Тест по \"Тетрадь смерти\" \uD83D\uDC80",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 64.sp
        )

        Button(
            onClick = onStart,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 5.dp,
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            Text(
                "НАЧАТЬ",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun QuestionScreen(
    index: Int,
    total: Int,
    question: Item,
    selectedIndex: Int?,
    onSelect: (Int) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {

            Text(
                "Вопрос $index / $total",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(24.dp))

            Text(
                question.ask,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                lineHeight = 32.sp
            )

            Spacer(Modifier.height(32.dp))

            question.variants.forEachIndexed { i, ans ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable { onSelect(i) },
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedIndex == i,
                            onClick = { onSelect(i) },
                            modifier = Modifier.size(28.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Red,
                                unselectedColor = Color.Gray,
                            )
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(
                            ans,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onNext,
                enabled = selectedIndex != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = MaterialTheme.shapes.large,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                ),
                contentPadding = PaddingValues(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(
                    "Следующий вопрос",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ResultScreen(correct: Int, total: Int, onRestart: () -> Unit) {
    val percent = correct * 100 / total

    val resultText = when {
        percent < 50 -> "Ну плохо..."
        percent < 80 -> "Пойдет"
        else -> "Супеrrrr"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ваши итоги года:",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "$correct / $total",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Red
        )

        Text(
            "$percent%",
            fontSize = 46.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            resultText,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onRestart,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 5.dp
            ),
            contentPadding = PaddingValues(vertical = 18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
        ) {
            Text(
                "ПОВТОРИМ?)",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}