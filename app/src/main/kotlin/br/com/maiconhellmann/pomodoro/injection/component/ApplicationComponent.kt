package br.com.maiconhellmann.pomodoro.injection.component

import android.app.Application
import android.content.Context
import br.com.maiconhellmann.pomodoro.data.DataManager
import br.com.maiconhellmann.pomodoro.data.local.DatabaseHelper
import br.com.maiconhellmann.pomodoro.injection.ApplicationContext
import br.com.maiconhellmann.pomodoro.injection.module.ApplicationModule
import br.com.maiconhellmann.pomodoro.injection.module.DataModule
import br.com.maiconhellmann.pomodoro.injection.module.PresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class))
interface ApplicationComponent {
    //services

    @ApplicationContext fun context(): Context
    fun application(): Application
    fun databaseHelper(): DatabaseHelper
    fun dataManager(): DataManager

    operator fun plus(presenterModule: PresenterModule): ConfigPersistentComponent
}
