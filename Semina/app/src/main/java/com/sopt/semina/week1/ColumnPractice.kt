package com.sopt.semina.week1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColumnPractice() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Green )
        )
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Blue)
        )
    }
}

@Preview
@Composable
fun ColumnPracticePreview() {
    ColumnPractice()
}