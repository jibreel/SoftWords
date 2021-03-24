package jibreelpowell.com.softwords.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import jibreelpowell.com.softwords.BuildConfig
import jibreelpowell.com.softwords.di.AppComponent
import jibreelpowell.com.softwords.di.DaggerAppComponent
import jibreelpowell.com.softwords.storage.AppDatabase
import jibreelpowell.com.softwords.utils.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

class App: Application() {
    lateinit var component: AppComponent

    @Inject lateinit var database: AppDatabase
    @Inject lateinit var schedulerProvider: SchedulerProvider

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        component = DaggerAppComponent
            .factory()
            .create(applicationContext)

        component.inject(this)

        database.checkInitialization(schedulerProvider)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}