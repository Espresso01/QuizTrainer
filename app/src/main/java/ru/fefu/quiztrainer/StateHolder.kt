package ru.fefu.quiztrainer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class StateHolder {

    var uiState by mutableStateOf(
        State(
            screen = Screens.Start,
            items = defaultItems()
        )
    )
        private set

    fun onEvent(event: Actions) {
        when (event) {

            Actions.Begin -> {
                uiState = uiState.copy(
                    screen = Screens.Quiz,
                    current = 0,
                    selected = null,
                    correct = 0
                )
            }

            is Actions.Pick -> {
                uiState = uiState.copy(
                    selected = event.id
                )
            }

            Actions.Continue -> {
                val index = uiState.current
                val selected = uiState.selected ?: return
                val item = uiState.items[index]

                val newCorrect =
                    uiState.correct +
                            if (selected == item.correct) 1 else 0

                if (index + 1 < uiState.items.size) {
                    uiState = uiState.copy(
                        current = index + 1,
                        selected = null,
                        correct = newCorrect
                    )
                } else {
                    uiState = uiState.copy(
                        screen = Screens.End,
                        correct = newCorrect
                    )
                }
            }

            Actions.Repeat -> {
                uiState = uiState.copy(
                    screen = Screens.Start,
                    current = 0,
                    selected = null,
                    correct = 0
                )
            }
        }
    }
}

private fun defaultItems(): List<Item> = listOf(
    Item(
        "Как зовут главного героя, который нашел Тетрадь смерти?",
        listOf("Лайт Ягами", "Эл", "Рюк", "Миса Амане"), 0
    ),
    Item(
        "Кто является владельцем первой Тетради смерти?",
        listOf("Синигами Рем", "Рюк", "Миками Теру", "Синигами Джелус"), 1
    ),
    Item(
        "Какой псевдоним использует Лайт, убивая преступников?",
        listOf("Вера", "Ангелина", "Кира", "Кристина"), 2
    ),
    Item(
        "Какое настоящее имя у детектива, известного как Эл?",
        listOf("Наоми Мисора", "Эл", "Рюдзаки", "Эл Лоулайт"), 3
    ),
    Item(
        "Как зовут еще одну обладательницу тетради, которая влюблена в Лайта?",
        listOf("Наоми Мисора", "Миса Амане", "Киёми Такада", "Рем"), 1
    ),
    Item(
        "Что должен сделать человек, чтобы получить глаза синигами?",
        listOf("Убить синигами", "Отдать половину своей оставшейся жизни",
            "Заключить договор с синигами", "Украсть их у синигами"), 1
    ),
    Item(
        "Как называется организация, созданная для поимки Киры?",
        listOf("Интерпол", "ФБР", "ФСБ",
            "Специальный оперативный отряд по расследованию Киры"), 3
    ),
    Item(
        "Что происходит с владельцем Тетради смерти после смерти?",
        listOf("Он попадает в рай", "Он попадает в ад", "Он становится синигами",
            "Он попадает в небытие"), 3
    ),
    Item(
        "Какой десерт постоянно ест Эл?",
        listOf("Шоколад", "Мороженое", "Пирожные", "Конфеты"), 2
    ),
    Item(
        "Какая у Лайта была оценка на вступительных экзаменах?",
        listOf(
            "Первый в школе", "Первый в городе", "Первый в стране", "Первый в мире"), 2
    )
)