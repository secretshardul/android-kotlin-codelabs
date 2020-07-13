package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    /** Encapsulation
     * Only this class has write access to _word and _score. These MutableLiveData fields are private.
     * Outside classes should have read-only access. word and score are public but of LiveData type.
     * LiveData fields can't be modified.
     */
    private val _word = MutableLiveData<String>() // Mutable because value can change

    // Pass value using Kotlin getter
    val word: LiveData<String> // Not mutable
        get() = _word

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    /** Display remaining game time to user. Game ends when time becomes 0 **/
    private val _currentTime = MutableLiveData<Long>()
    // Map time in MM:SS format
    val currentTime: LiveData<String>
        get() = Transformations.map(_currentTime) {
            DateUtils.formatElapsedTime(it)
        }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /** Companion object properties are tied to the class rather than to instances **/
    companion object {
        // Time when game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total game time
        private const val COUNTDOWN_TIME = 7000L
    }

    /** Start timer when ViewModel is initialized. **/
    private val timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
        override fun onTick(millisUntilFinished: Long) {
            _currentTime.value = millisUntilFinished / ONE_SECOND // Save time to LiveData
        }

        override fun onFinish() {
            _currentTime.value = DONE
            onGameFinish() // End game when timer stops
        }
    }

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()

        timer.start()
    }

    /**
     * Before ViewModel is destroyed, onCleared() is called to reclaim resources.
     */
    override fun onCleared() {
        super.onCleared()
        timer.cancel() // Cancel timer to avoid memory leaks.
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    /**
     * Moves to the next word in the list. If list is empty then reset it.
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        } else {
            // Remove a word from the list
            _word.value = wordList.removeAt(0)
        }
    }

    /** Call to end game **/
    fun onGameFinish() {
        _eventGameFinish.value = true
    }

    /** Method for the game completed event. Reset _eventGameFinish value. **/
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
}
