package jibreelpowell.com.softwords.activityutils

import android.app.Activity
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import jibreelpowell.com.softwords.mainactivity.MainActivity

@ActivityScope
@Subcomponent()
interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: Activity): ActivityComponent
    }

    fun inject(activity: MainActivity)
}