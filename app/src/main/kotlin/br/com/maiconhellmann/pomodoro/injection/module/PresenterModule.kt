package br.com.maiconhellmann.pomodoro.injection.module

import br.com.maiconhellmann.pomodoro.data.DataManager
import br.com.maiconhellmann.pomodoro.injection.ConfigPersistent
import br.com.maiconhellmann.pomodoro.ui.history.HistoryContract
import br.com.maiconhellmann.pomodoro.ui.history.HistoryPresenter
import br.com.maiconhellmann.pomodoro.ui.main.MainContract
import br.com.maiconhellmann.pomodoro.ui.main.MainPresenter
import br.com.maiconhellmann.pomodoro.ui.pomodoro.PomodoroContract
import br.com.maiconhellmann.pomodoro.ui.pomodoro.PomodoroPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{

    @Provides
    @ConfigPersistent
    fun providesMainPresenter(dataManager: DataManager): MainContract.Presenter {
        return MainPresenter(dataManager)
    }

    @Provides
    @ConfigPersistent
    fun providesPomodoroPresenter(dataManager: DataManager): PomodoroContract.Presenter {
        return PomodoroPresenter(dataManager)
    }

    @Provides
    @ConfigPersistent
    fun providesHistoryPresenter(dataManager: DataManager): HistoryContract.Presenter{
        return HistoryPresenter(dataManager)
    }
}