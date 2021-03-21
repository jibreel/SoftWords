package jibreelpowell.com.softwords.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import jibreelpowell.com.softwords.generate.GenerateViewModel
import jibreelpowell.com.softwords.history.HistoryViewModel
import jibreelpowell.com.softwords.settings.SettingsViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    abstract fun bindHistoryViewModel(historyViewModel: HistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GenerateViewModel::class)
    abstract fun bindGenerateViewModel(generateViewModel: GenerateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: SoftWordsViewModelFactory): ViewModelProvider.Factory
}