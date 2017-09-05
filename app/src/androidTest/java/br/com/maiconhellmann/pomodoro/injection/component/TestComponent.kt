package br.com.maiconhellmann.pomodoro.injection.component

import br.com.maiconhellmann.pomodoro.injection.module.ApplicationTestModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : ApplicationComponent
