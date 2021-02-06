package jibreelpowell.com.softwords.generate

import dagger.Subcomponent
import jibreelpowell.com.softwords.activityutils.ActivityScope
import jibreelpowell.com.softwords.mainactivity.MainActivity

@ActivityScope
@Subcomponent
interface GenerateComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): GenerateComponent
    }

    fun inject(activity: MainActivity)
}