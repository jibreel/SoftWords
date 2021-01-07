package jibreelpowell.com.softwords.app

import dagger.BindsInstance
import dagger.Component
import jibreelpowell.com.softwords.activityutils.ActivityComponent
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