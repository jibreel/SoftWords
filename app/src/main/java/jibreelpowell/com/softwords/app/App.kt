package jibreelpowell.com.softwords.app

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import jibreelpowell.com.softwords.BuildConfig
import jibreelpowell.com.softwords.di.koin.modules.Modules
import jibreelpowell.com.softwords.storage.AppDatabase
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.SchedulerProviderImpl
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class App: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(Modules.utilityModule, Modules.networkModule, Modules.storageModule, Modules.historyModule, Modules.viewModelModule)
        }

        val schedulerProvider: SchedulerProvider = SchedulerProviderImpl()
        val database = AppDatabase.createInstance(applicationContext, schedulerProvider)
        database.checkInitialization(schedulerProvider)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}