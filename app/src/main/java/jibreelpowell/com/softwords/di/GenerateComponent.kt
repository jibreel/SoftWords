package jibreelpowell.com.softwords.di

import dagger.Subcomponent
import jibreelpowell.com.softwords.generate.GenerateFragment
import jibreelpowell.com.softwords.history.HistoryFragment
import jibreelpowell.com.softwords.mainactivity.MainActivity
import jibreelpowell.com.softwords.settings.SettingsFragment

@ActivityScope
@Subcomponent
interface GenerateComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): GenerateComponent
    }

    fun inject(activity: MainActivity)
    fun inject(generateFragment: GenerateFragment)
    fun inject(historyFragment: HistoryFragment)
    fun inject(settingsFragment: SettingsFragment)
}