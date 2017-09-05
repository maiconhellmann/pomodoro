package br.com.maiconhellmann.pomodoro.data.model

/**
 * Created by hellmanss on 9/5/17.
 */
enum class PomodoroStatus(val id: Long) {
    RUNNING(1L),
    STOPPED(2L),
    FINISHED(3L),
    CREATED(4L);

    companion object {
        fun findById(filter: Long) = values().first { it.id == filter }
    }
}