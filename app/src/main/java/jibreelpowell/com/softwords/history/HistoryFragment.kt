package jibreelpowell.com.softwords.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import jibreelpowell.com.softwords.R
import jibreelpowell.com.softwords.databinding.FragmentHistoryBinding
import jibreelpowell.com.softwords.mainactivity.MainActivity
import jibreelpowell.com.softwords.utils.recyclerview.SwipeToDeleteItemTouchHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val adapter: HistoryAdapter by inject()

    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHistoryBinding.inflate(inflater, container, false)

        viewModel.allSentences.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.viewModel = viewModel
        binding.historyRecyclerView.adapter = adapter

        context?.let {
            val itemTouchHelper = SwipeToDeleteItemTouchHelper(it, viewModel::delete)
            itemTouchHelper.attachToRecyclerView(binding.historyRecyclerView)
        }

        viewModel.deletionResult.observe(viewLifecycleOwner) { result ->
            when {
                result.isSuccess -> Snackbar.make(
                    binding.root,
                    R.string.snackbar_deleted_sentence,
                    Snackbar.LENGTH_SHORT
                ).show()
                result.isFailure -> Snackbar.make(
                    binding.root,
                    R.string.snackbar_delete_sentence_failed,
                    Snackbar.LENGTH_SHORT
                )
            }
        }

        return binding.root
    }

}