package br.com.maiconhellmann.pomodoro.data

import br.com.maiconhellmann.pomodoro.data.local.DatabaseHelper
import br.com.maiconhellmann.pomodoro.data.model.Pomodoro
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(private val databaseHelper: DatabaseHelper) {
    fun getPomodoroList() = databaseHelper.findAllPomodoro()
    fun savePomodoro(p: Pomodoro) = databaseHelper.savePomodoro(p)
    fun findRunningPomodoro() = databaseHelper.findRunningPomodoro()
    fun stopRunningPomodoro(pomodoro: Pomodoro) = databaseHelper.stopRunningPomodoro(pomodoro)
}

