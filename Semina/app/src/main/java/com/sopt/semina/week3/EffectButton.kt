package com.sopt.semina.week3

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EffectButton(
    modifier: Modifier = Modifier,
    viewModel: SideEffectButtonViewModel = viewModel()
) {
    val context: Context = LocalContext.current

    val state = viewModel.count
    val enabled = viewModel.isButtonEnabled

    LaunchedEffect(state) {
        if (state == 5) {
            Toast.makeText(context, "카운트 5", Toast.LENGTH_SHORT).show()
        }
    }

    Button(
        modifier = modifier,
        onClick = { viewModel.onButtonClick() },
        shape = RoundedCornerShape(15.dp),
        enabled = enabled
    ) {
        Text(
            state.toString(),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        )
    }
}

@Preview
@Composable
fun EffectButtonPreveiw() {
    EffectButton(
        modifier = TODO(),
        viewModel = TODO()
    )
}