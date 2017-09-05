package br.com.maiconhellmann.pomodoro.ui.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the [_view] that
 * can be accessed from the children classes by calling [view].
 */
open class BasePresenter<T : MvpView>: Presenter<T> {

    private var _view: T? = null
    var subscriptions: CompositeSubscription = CompositeSubscription()

    val view: T
        get() { return _view ?: throw MvpViewNotAttachedException() }

    override fun attachView(view: T) {
        _view = view
    }

    override fun detachView() {
        _view = null
        this.subscriptions.unsubscribe()
    }
    protected fun unsubscribeOnDetach(subscription: Subscription) {
        subscriptions.add(subscription)
    }

    class MvpViewNotAttachedException : RuntimeException(
            "Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}
