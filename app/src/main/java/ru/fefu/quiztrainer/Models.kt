package ru.fefu.quiztrainer

data class Item(
    val ask: String,
    val variants: List<String>,
    val correct: Int
)

object Screens {
    object Start
    object Quiz
    object End
}

data class State(
    val screen: Any,
    val items: List<Item>,
    val current: Int = 0,
    val selected: Int? = null,
    val correct: Int = 0
)

sealed interface Actions {
    object Begin : Actions
    data class Pick(val id: Int) : Actions
    object Continue : Actions
    object Repeat : Actions
}