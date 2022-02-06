package jibreelpowell.com.softwords.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import jibreelpowell.com.softwords.BuildConfig
import jibreelpowell.com.softwords.di.AppComponent
import jibreelpowell.com.softwords.di.DaggerAppComponent
import jibreelpowell.com.softwords.di.koin.modules.Modules
import jibreelpowell.com.softwords.storage.AppDatabase
import jibreelpowell.com.softwords.utils.SchedulerProvider
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import javax.inject.Inject

class App: Application() {
    lateinit var component: AppComponent

    lateinit var database: AppDatabase
    lateinit var schedulerProvider: SchedulerProvider

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        component = DaggerAppComponent
            .factory()
            .create(applicationContext)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(Modules.utilityModule, Modules.networkModule, Modules.storageModule)
        }

        component.inject(this)

        database.checkInitialization(schedulerProvider)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}