package jibreelpowell.com.softwords.di

import dagger.BindsInstance
import dagger.Component
import jibreelpowell.com.softwords.app.App
import jibreelpowell.com.softwords.generate.GenerateComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class])
interface AppComponent {
    fun inject(app: App)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: App): AppComponent
    }

    fun generateComponent(): GenerateComponent.Factory
}