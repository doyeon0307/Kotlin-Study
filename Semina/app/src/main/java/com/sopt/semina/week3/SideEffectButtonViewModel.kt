package com.sopt.semina.week3

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SideEffectButtonViewModel : ViewModel() {
    var count by mutableIntStateOf(0)
        private set

    val isButtonEnabled by derivedStateOf { count < 10 }

    fun onButtonClick() {
        count += 1
    }
}