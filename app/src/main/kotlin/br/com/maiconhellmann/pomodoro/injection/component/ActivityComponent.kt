package br.com.maiconhellmann.pomodoro.injection.component

import br.com.maiconhellmann.pomodoro.injection.PerActivity
import br.com.maiconhellmann.pomodoro.injection.module.ActivityModule
import br.com.maiconhellmann.pomodoro.ui.main.MainActivity
import dagger.Subcomponent

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
}
