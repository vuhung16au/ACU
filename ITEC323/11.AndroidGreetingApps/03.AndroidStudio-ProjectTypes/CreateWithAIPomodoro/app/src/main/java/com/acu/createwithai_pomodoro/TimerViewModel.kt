package com.acu.createwithai_pomodoro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Represents the different modes of the Pomodoro timer.
 */
enum class PomodoroMode(val durationMinutes: Int, val label: String) {
    FOCUS(25, "Focus"),
    SHORT_BREAK(5, "Short Break"),
    LONG_BREAK(15, "Long Break")
}

/**
 * ViewModel responsible for managing the Pomodoro timer logic.
 * Handles countdown, mode switching, play/pause, and reset actions.
 */
class TimerViewModel : ViewModel() {

    private val _currentMode = MutableStateFlow(PomodoroMode.FOCUS)
    val currentMode: StateFlow<PomodoroMode> = _currentMode.asStateFlow()

    private val _timeLeft = MutableStateFlow(PomodoroMode.FOCUS.durationMinutes * 60L)
    val timeLeft: StateFlow<Long> = _timeLeft.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _onTimerFinished = MutableSharedFlow<Unit>()
    val onTimerFinished: SharedFlow<Unit> = _onTimerFinished.asSharedFlow()

    private var timerJob: Job? = null

    /**
     * Switches the timer to a different mode and resets the countdown.
     */
    fun setMode(mode: PomodoroMode) {
        if (_currentMode.value == mode) return
        
        pauseTimer()
        _currentMode.value = mode
        _timeLeft.value = mode.durationMinutes * 60L
    }

    /**
     * Starts the countdown timer.
     */
    fun toggleTimer() {
        if (_isRunning.value) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        if (_isRunning.value || _timeLeft.value <= 0) return
        
        _isRunning.value = true
        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value -= 1
            }
            _isRunning.value = false
            _onTimerFinished.emit(Unit)
        }
    }

    /**
     * Pauses the countdown timer.
     */
    fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
        timerJob = null
    }

    /**
     * Resets the timer to the current mode's initial duration.
     */
    fun resetTimer() {
        pauseTimer()
        _timeLeft.value = _currentMode.value.durationMinutes * 60L
    }

    /**
     * Formats the remaining time as MM:SS string.
     */
    fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "%02d:%02d".format(minutes, remainingSeconds)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
