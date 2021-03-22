package jibreelpowell.com.softwords.di.modules

import dagger.Module
import dagger.Provides
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.SchedulerProviderImpl
import javax.inject.Singleton

@Module
class UtilityModule {

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl()
    }
}