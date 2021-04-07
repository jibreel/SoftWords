package jibreelpowell.com.softwords.history.detail

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import jibreelpowell.com.softwords.R
import jibreelpowell.com.softwords.databinding.FragmentHistoryDetailBinding
import jibreelpowell.com.softwords.mainactivity.MainActivity
import javax.inject.Inject

class HistoryDetailFragment: Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    val args: HistoryDetailFragmentArgs by navArgs()

    private val viewModel: HistoryDetailViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generateComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHistoryDetailBinding.inflate(inflater, container, false)

        viewModel.loadResult.observe(viewLifecycleOwner) {
            when {
                it.isSuccess -> {
                    viewModel.updateUiForSentence(it.getOrThrow())
                }
                else -> {
                    AlertDialog.Builder(context)
                        .setMessage(getString(R.string.error_loading_sentence_message))
                        .setPositiveButton(R.string.retry) { dialogInterface: DialogInterface, _: Int ->
                            viewModel.load(args.sentenceId)
                            dialogInterface.dismiss()
                        }
                        .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, _: Int ->
                            dialogInterface.cancel()
                        }
                }
            }
        }

        viewModel.deleteResult.observe(viewLifecycleOwner) {
            when {
                it.isSuccess -> {
                    Snackbar.make(
                        binding.root,
                        R.string.snackbar_deleted_sentence,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    findNavController().navigateUp()
                }
                else -> {
                    Snackbar.make(
                        binding.root,
                        R.string.snackbar_delete_sentence_failed,
                        Snackbar.LENGTH_SHORT
                    ).setAction(R.string.retry) { viewModel.delete() }
                        .show()
                }
            }
        }

        viewModel.load(args.sentenceId)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}