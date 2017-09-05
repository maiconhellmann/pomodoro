package br.com.maiconhellmann.pomodoro.ui.pomodoro

import br.com.maiconhellmann.pomodoro.data.DataManager
import br.com.maiconhellmann.pomodoro.data.model.Pomodoro
import br.com.maiconhellmann.pomodoro.data.model.PomodoroStatus
import br.com.maiconhellmann.pomodoro.util.extension.plus
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscribeBy
import rx.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by hellmanss on 9/4/17.
 */
class PomodoroPresenter(val dataManager: DataManager) : PomodoroContract.Presenter() {

    var timerSubscription: Subscription? = null
    var stopTimerSubscription: Subscription? = null
    val pomodoroTimebox = 1500000L
    var pomodoro: Pomodoro? = null

    override fun startTimer() {
        stopTimerSubscription?.unsubscribe()

        pomodoro = Pomodoro(0, Date(), Date() + pomodoroTimebox, status = PomodoroStatus.RUNNING)

        val dif = (pomodoro!!.endDateTime.time - Date().time)/1000

        val timerSubscriber =  Observable.interval(1, TimeUnit.SECONDS)
                .take(dif.toInt())
                .map { dif - it }

        timerSubscription = dataManager.savePomodoro(pomodoro!!)
                .flatMap {
                    Timber.d("flatMap $it")
                    timerSubscriber
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onNext = {
                            Timber.d("timerSubscription timer: $it")
                            view.updateTimer(it)

                            if(it<= 1){
                                view.timerFinished()
                                pomodoro?.status = PomodoroStatus.FINISHED
                                savePomodoro(pomodoro!!)
                            }
                        },
                        onError = {
                            Timber.d("timerSubscription onError: $it")
                            view.showError(it)
                        }
                )


        view.configureStopButton()
    }

    private fun savePomodoro(p: Pomodoro) {
        dataManager.savePomodoro(p)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onCompleted = {
                            Timber.d("savePomodoro onCompleted")
                        },
                        onNext = {
                            Timber.d("savePomodoro timer: $it")
                        },
                        onError = {
                            Timber.d("savePomodoro onError: $it")
                            view.showError(it)
                        }
                )
    }



    override fun stopTimer() {
        timerSubscription?.unsubscribe()
        stopTimerSubscription?.unsubscribe()

        stopTimerSubscription = dataManager.stopRunningPomodoro(pomodoro!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy (
                            onCompleted = {
                                view.configureStartButton()
                                view.showStoppedMessage()
                            },
                            onError = {
                                view.showError(it)
                            }
                    )
    }

    override fun detachView() {
        super.detachView()
        timerSubscription?.unsubscribe()
        stopTimerSubscription?.unsubscribe()
    }
}