package jibreelpowell.com.softwords.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import jibreelpowell.com.softwords.di.AppComponent
import jibreelpowell.com.softwords.di.DaggerAppComponent

class App: Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        component = DaggerAppComponent
            .factory()
            .create(applicationContext)
    }
}