package br.com.maiconhellmann.pomodoro.ui.history

import br.com.maiconhellmann.pomodoro.data.model.Pomodoro
import br.com.maiconhellmann.pomodoro.ui.base.BasePresenter
import br.com.maiconhellmann.pomodoro.ui.base.MvpView

/**
 * Created by hellmanss on 9/4/17.
 */
object HistoryContract {

    interface View: MvpView{
        fun showEmptyList()
        fun showPomodoroList(pomodoroList: List<Pomodoro>)
        fun showError(t: Throwable)
    }

    abstract class Presenter: BasePresenter<View>(){
        abstract fun getPomodoroList()
    }
}