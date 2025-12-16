package ru.fefu.quiztrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import ru.fefu.quiztrainer.ui.theme.QuizTrainerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizTrainerTheme {
                val holder = remember { StateHolder() }

                QuizRoot(
                    state = holder.uiState,
                    onEvent = holder::onEvent
                )
            }
        }
    }
}