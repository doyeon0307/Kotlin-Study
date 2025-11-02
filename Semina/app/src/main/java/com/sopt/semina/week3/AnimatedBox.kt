package com.sopt.semina.week3

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class BoxState { Collapsed, Expanded }

@Composable
fun AnimatedBox(
    modifier: Modifier = Modifier,
) {
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }

    val transition = updateTransition(currentState, label = "transition")

    val size by transition.animateDp (label = "size") { state ->
        when (state) {
            BoxState.Collapsed -> 100.dp
            BoxState.Expanded -> 200.dp
        }
    }

    val color by transition.animateColor (label = "color") { state ->
        when (state) {
            BoxState.Collapsed -> Color.Blue
            BoxState.Expanded -> Color.Red
        }
    }

    val rotation by transition.animateFloat (label = "rotation") { state ->
        when (state) {
            BoxState.Collapsed -> 0f
            BoxState.Expanded -> 45f
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .rotate(rotation)
            // 백그라운드가 로테이션 전애 깔리면 회전이 안 보임
            // 회전 이전에 이미 색상에 깔려버려서
            .background(color)
            .clickable {
                currentState =
                    if (currentState == BoxState.Collapsed) {
                        BoxState.Expanded
                    } else {
                        BoxState.Collapsed
                    }
            }
    )
}

@Preview
@Composable
fun AnimatedBoxPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedBox()
    }
}