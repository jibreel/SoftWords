package jibreelpowell.com.softwords.app

import android.app.Application

class App: Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
            .factory()
            .create(this)

        component.inject(this)
    }
}