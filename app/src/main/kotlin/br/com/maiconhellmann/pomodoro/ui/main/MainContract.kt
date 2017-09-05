package br.com.maiconhellmann.pomodoro.ui.main

import br.com.maiconhellmann.pomodoro.ui.base.BasePresenter
import br.com.maiconhellmann.pomodoro.ui.base.MvpView

object MainContract {

    interface View: MvpView {
    }

    abstract class Presenter: BasePresenter<View>() {
    }
}
