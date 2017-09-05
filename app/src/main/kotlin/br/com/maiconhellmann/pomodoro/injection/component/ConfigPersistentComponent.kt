package br.com.maiconhellmann.pomodoro.injection.component

import br.com.maiconhellmann.pomodoro.injection.ConfigPersistent
import br.com.maiconhellmann.pomodoro.injection.module.ActivityModule
import br.com.maiconhellmann.pomodoro.injection.module.PresenterModule
import br.com.maiconhellmann.pomodoro.ui.history.HistoryFragment
import br.com.maiconhellmann.pomodoro.ui.pomodoro.PomodoroFragment
import dagger.Subcomponent

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check [BaseActivity] to see how this components
 * survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Subcomponent(modules = arrayOf(PresenterModule::class))
interface ConfigPersistentComponent {
    //inject fragments
    fun inject(fragment: PomodoroFragment)
    fun inject(fragment: HistoryFragment)

    operator fun plus(activityModule: ActivityModule): ActivityComponent
}
