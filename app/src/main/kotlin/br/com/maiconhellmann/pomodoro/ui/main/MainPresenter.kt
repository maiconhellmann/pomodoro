package br.com.maiconhellmann.pomodoro.ui.main

import br.com.maiconhellmann.pomodoro.data.DataManager
import br.com.maiconhellmann.pomodoro.injection.ConfigPersistent
import rx.Subscription
import javax.inject.Inject

class MainPresenter

constructor(private val dataManager: DataManager) : MainContract.Presenter() {

    private var subscription: Subscription? = null

    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }
}
