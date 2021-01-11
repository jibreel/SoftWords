package jibreelpowell.com.softwords.app

import android.content.Context
import dagger.Module
import dagger.Provides
import jibreelpowell.com.softwords.activityutils.ActivityComponent
import javax.inject.Singleton

@Module(subcomponents = [ActivityComponent::class])
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: App): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideApp(app: App): Context {
        return app
    }
}