package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    init {
        Log.i("GameViewModel", "GameViewModel created!")
    }

    /**
     * Before ViewModel is destroyed, onCleared() is called to reclaim resources.
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}