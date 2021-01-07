package jibreelpowell.com.softwords.activityutils

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule() {

    @ActivityScope
    @Provides
    fun provideActivity(activity: Activity): Activity {
        return activity
    }
}