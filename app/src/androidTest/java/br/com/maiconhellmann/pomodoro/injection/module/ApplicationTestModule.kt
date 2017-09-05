package br.com.maiconhellmann.pomodoro.injection.module

import android.app.Application
import android.content.Context
import br.com.maiconhellmann.pomodoro.data.DataManager
import br.com.maiconhellmann.pomodoro.data.local.DatabaseHelper
import br.com.maiconhellmann.pomodoro.injection.ApplicationContext
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationTestModule(val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    fun databaseHelper(): DatabaseHelper {
        return mock()
    }

    @Provides
    fun dataManager(): DataManager {
        return mock()
    }
}
