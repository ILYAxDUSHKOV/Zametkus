package com.example.zametkus.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

    /*
    Тут было определение светлой и темной темы.
    Похоже, что это не очень то и нужно.
    В моем приложении будет одна тема.
    Все необходимые определения цветов есть в xml файле.
    */
@Composable
fun ZametkusTheme(content: @Composable() () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MyLightGray)

    MaterialTheme(
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}