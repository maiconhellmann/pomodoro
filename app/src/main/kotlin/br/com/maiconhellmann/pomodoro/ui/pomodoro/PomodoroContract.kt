package br.com.maiconhellmann.pomodoro.ui.pomodoro

import br.com.maiconhellmann.pomodoro.ui.base.BasePresenter
import br.com.maiconhellmann.pomodoro.ui.base.MvpView

/**
 * Created by hellmanss on 9/4/17.
 */
object PomodoroContract {
    interface View: MvpView{
        fun timerFinished()
        fun showError(t: Throwable)
        fun updateTimer(t: Long)
        fun showStoppedMessage()
        fun configureStartButton()
        fun configureStopButton()

    }
    abstract class Presenter: BasePresenter<View>(){
        abstract fun startTimer()
        abstract fun stopTimer()
    }
}