package br.com.maiconhellmann.pomodoro.injection.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(DbModule::class))
class DataModule {

    /**
     * Prove o sharedPreferences
     */
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("pomodoro", MODE_PRIVATE)
    }
}
