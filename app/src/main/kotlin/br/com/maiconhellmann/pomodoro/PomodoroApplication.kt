package br.com.maiconhellmann.pomodoro

import android.app.Application
import br.com.maiconhellmann.pomodoro.injection.component.ApplicationComponent
import br.com.maiconhellmann.pomodoro.injection.component.DaggerApplicationComponent
import br.com.maiconhellmann.pomodoro.injection.module.ApplicationModule
import timber.log.Timber

open class PomodoroApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initDaggerComponent()
    }

    fun initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
