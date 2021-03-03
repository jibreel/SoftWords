package jibreelpowell.com.softwords.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import jibreelpowell.com.softwords.history.HistoryViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    abstract fun bindHistoryViewModel(historyViewModel: HistoryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: SoftWordsViewModelFactory): ViewModelProvider.Factory
}