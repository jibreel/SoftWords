package jibreelpowell.com.softwords.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import jibreelpowell.com.softwords.generate.GenerateComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, NetworkModule::class, ViewModelModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun generateComponent(): GenerateComponent.Factory
}