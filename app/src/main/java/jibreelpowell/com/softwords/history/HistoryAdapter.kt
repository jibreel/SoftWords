package jibreelpowell.com.softwords.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import jibreelpowell.com.softwords.R
import jibreelpowell.com.softwords.databinding.ItemHistoryBinding
import jibreelpowell.com.softwords.history.item.HistoryItemPresenter
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.utils.recyclerview.DataBoundListAdapter
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HistoryAdapter : DataBoundListAdapter<GeneratedSentence, ItemHistoryBinding>(GeneratedSentence.diffCallback), KoinComponent {

    override fun createBinding(parent: ViewGroup): ItemHistoryBinding {
        val binding = DataBindingUtil.inflate<ItemHistoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history,
            parent,
            false
        )
        val presenter:  HistoryItemPresenter = get()
        binding.presenter = presenter
        return binding
    }

    override fun bind(binding: ItemHistoryBinding, item: GeneratedSentence) {
        binding.presenter?.let { it.bind(item) }
    }
}