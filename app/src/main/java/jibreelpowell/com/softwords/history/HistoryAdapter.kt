package jibreelpowell.com.softwords.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import jibreelpowell.com.softwords.R
import jibreelpowell.com.softwords.databinding.ItemHistoryBinding
import jibreelpowell.com.softwords.history.item.HistoryItemPresenter
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.utils.DataBoundListAdapter
import javax.inject.Inject
import javax.inject.Provider

class HistoryAdapter @Inject constructor(
    private val itemPresenterProvider: Provider<HistoryItemPresenter>
) : DataBoundListAdapter<GeneratedSentence, ItemHistoryBinding>(GeneratedSentence.diffCallback) {

    override fun createBinding(parent: ViewGroup): ItemHistoryBinding {
        val binding = DataBindingUtil.inflate<ItemHistoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history,
            parent,
            false
        )
        binding.presenter = itemPresenterProvider.get()
        return binding
    }

    override fun bind(binding: ItemHistoryBinding, item: GeneratedSentence) {
        binding.presenter?.let { it.bind(item) }
    }
}