package jibreelpowell.com.softwords.di

import dagger.BindsInstance
import dagger.Component
import jibreelpowell.com.softwords.activityutils.ActivityComponent
import jibreelpowell.com.softwords.app.App
import jibreelpowell.com.softwords.app.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: App): AppComponent
    }

    fun activityComponent(): ActivityComponent.Factory
}