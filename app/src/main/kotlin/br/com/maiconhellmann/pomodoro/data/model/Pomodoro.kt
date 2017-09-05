package br.com.maiconhellmann.pomodoro.data.model

import java.util.*

/**
 * Created by hellmanss on 9/4/17.
 */
data class Pomodoro(var id: Long = 0,
                    val startDateTime: Date,
                    var endDateTime: Date,
                    var status: PomodoroStatus = PomodoroStatus.CREATED)


