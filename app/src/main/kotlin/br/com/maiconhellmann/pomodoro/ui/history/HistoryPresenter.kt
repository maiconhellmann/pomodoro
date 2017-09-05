package br.com.maiconhellmann.pomodoro.ui.history

import br.com.maiconhellmann.pomodoro.data.DataManager
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscribeBy
import rx.schedulers.Schedulers

/**
 * Created by hellmanss on 9/4/17.
 */
class HistoryPresenter(private var dataManager: DataManager) : HistoryContract.Presenter() {
    override fun getPomodoroList() {
        unsubscribeOnDetach(
            dataManager.getPomodoroList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onNext ={
                            if(it.isEmpty()){
                                view.showEmptyList()
                            }else{
                                view.showPomodoroList(it)
                            }
                        },
                        onError = {
                            view.showEmptyList()
                            view.showError(it)
                        }

                )
        )
    }
}