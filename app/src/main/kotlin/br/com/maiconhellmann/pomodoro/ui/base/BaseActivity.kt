package br.com.maiconhellmann.pomodoro.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.maiconhellmann.pomodoro.PomodoroApplication
import br.com.maiconhellmann.pomodoro.injection.component.ActivityComponent
import br.com.maiconhellmann.pomodoro.injection.component.ConfigPersistentComponent
import br.com.maiconhellmann.pomodoro.injection.module.ActivityModule
import br.com.maiconhellmann.pomodoro.injection.module.PresenterModule
import br.com.maiconhellmann.pomodoro.util.extension.snackbar
import timber.log.Timber
import java.util.*
import java.util.concurrent.atomic.AtomicLong

open class BaseActivity: AppCompatActivity() {
    private var  progressDialog: ProgressDialog? = null

    companion object {
        @JvmStatic private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        @JvmStatic private val NEXT_ID = AtomicLong(0)
        @JvmStatic private val componentsMap = HashMap<Long, ConfigPersistentComponent>()
    }

    private var activityId: Long = 0
    lateinit var activityComponent: ActivityComponent
        get

    @Suppress("UsePropertyAccessSyntax")
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        activityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()

        if (componentsMap[activityId] != null)
            Timber.i("Reusing ConfigPersistentComponent id = $activityId")

        val configPersistentComponent = componentsMap.getOrPut(activityId, {
            Timber.i("Creating new ConfigPersistentComponent id=$activityId")

            val component = (applicationContext as PomodoroApplication).applicationComponent

            component + PresenterModule()
        })

        activityComponent = configPersistentComponent + ActivityModule(this)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, activityId)
    }

    override fun onDestroy() {
        if (!isChangingConfigurations) {
            Timber.i("Clearing ConfigPersistentComponent id=$activityId")
            componentsMap.remove(activityId)
        }

        super.onDestroy()
    }

    override fun setTitle(titleId: Int) {
        actionBar?.title = getString(titleId)
        supportActionBar?.title = getString(titleId)
    }

    fun displayHomeAsUpEnabled(){
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    open fun hideProgressIndicator() {
        progressDialog?.dismiss()
        progressDialog = null
    }


    open fun showProgressIndicator(title: String?, message: String? = null, cancelable: Boolean = true) {
        val dialog = ProgressDialog(this)

        if (title != null) {
            dialog.setTitle(title)
        }

        if (message != null) {
            dialog.setMessage(message)
        }

        dialog.isIndeterminate = true
        dialog.setCancelable(cancelable)
        dialog.show()

        progressDialog = dialog
    }
    open fun showProgressIndicator() {
        showProgressIndicator("aguarde")
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    fun getParentView(): View? {
        return findViewById(android.R.id.content)
    }

    fun snack(resId: Int, duration: Int = Snackbar.LENGTH_SHORT){
        getParentView()?.snackbar(resId, duration)
    }

    fun snack(msg: String, duration: Int = Snackbar.LENGTH_SHORT){
        getParentView()?.snackbar(msg, duration)
    }

    fun longSnack(resId: Int){
        getParentView()?.snackbar(resId, Snackbar.LENGTH_LONG)
    }
    fun longSnack(msg: String){
        getParentView()?.snackbar(msg, Snackbar.LENGTH_LONG)
    }
}
