package br.com.maiconhellmann.pomodoro.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import br.com.maiconhellmann.pomodoro.PomodoroApplication
import br.com.maiconhellmann.pomodoro.injection.component.ConfigPersistentComponent
import br.com.maiconhellmann.pomodoro.injection.module.PresenterModule
import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong

open class BaseFragment: Fragment(){
    lateinit var baseActivity: BaseActivity


    companion object {
        @JvmStatic private val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
        @JvmStatic private val NEXT_ID = AtomicLong(0)
        @JvmStatic private val componentsMap = HashMap<Long, ConfigPersistentComponent>()
    }

    private var fragmentId: Long = 0
    lateinit var component: ConfigPersistentComponent
        get

    @Suppress("UsePropertyAccessSyntax")
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        fragmentId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()

        if (componentsMap[fragmentId] != null)
            Timber.i("Reusing ConfigPersistentComponent id = $fragmentId")


        component = componentsMap.getOrPut(fragmentId, {
            Timber.i("Creating new ConfigPersistentComponent id=$fragmentId")
            (context.applicationContext as PomodoroApplication).applicationComponent.plus(PresenterModule())
        })
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_FRAGMENT_ID, fragmentId)
    }

    @CallSuper
    override fun onDestroy() {
        if (!activity.isChangingConfigurations) {
            Timber.i("Clearing ConfigPersistentComponent id=$fragmentId")
            componentsMap.remove(fragmentId)
        }

        super.onDestroy()
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(activity is BaseActivity){
            baseActivity = activity as BaseActivity
        }else{
            throw Throwable("Fragment doesn't implements ${BaseActivity::class.java.simpleName}")
        }
    }

    open fun showProgressIndicator(){
        baseActivity.showProgressIndicator()
    }

    open fun hideProgressIndicator(){
        baseActivity.hideProgressIndicator()
    }
}