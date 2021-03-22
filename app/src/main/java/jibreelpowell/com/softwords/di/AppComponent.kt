package jibreelpowell.com.softwords.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import jibreelpowell.com.softwords.app.App
import jibreelpowell.com.softwords.di.modules.NetworkModule
import jibreelpowell.com.softwords.di.modules.StorageModule
import jibreelpowell.com.softwords.di.modules.UtilityModule
import jibreelpowell.com.softwords.di.modules.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, NetworkModule::class, ViewModelModule::class, UtilityModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun generateComponent(): GenerateComponent.Factory

    fun inject(app: App)
}