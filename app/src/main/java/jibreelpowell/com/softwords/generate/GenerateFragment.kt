package jibreelpowell.com.softwords.generate

import android.app.AlertDialog
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
import jibreelpowell.com.softwords.databinding.FragmentGenerateBinding
import jibreelpowell.com.softwords.mainactivity.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenerateFragment : Fragment() {

    private val viewModel: GenerateViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentGenerateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.storageResult.observe(viewLifecycleOwner) {
            when {
                it.isSuccess -> {
                    Snackbar
                        .make(binding.root, R.string.generate_store_snackbar_text_success, Snackbar.LENGTH_SHORT)
                        .show()
                }
                it.isFailure -> {
                    Snackbar.make(binding.root, R.string.generate_store_snackbar_text_failure, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry) {
                            viewModel.storeCurrentSentence()
                        }
                        .show()
                }
            }
        }

        viewModel.generateResult.observe(viewLifecycleOwner) {
            when {
                it.isFailure -> {
                    AlertDialog.Builder(context)
                        .setMessage(R.string.generate_sentence_error)
                        .setPositiveButton(R.string.retry) { adb, _ ->
                            adb.dismiss()
                            viewModel.generateNewSentence() }
                        .setNegativeButton(R.string.cancel) { adb, _ ->
                            adb.cancel()
                        }
                        .show()
                }
            }
        }

        viewModel.generateNewSentence()

        return binding.root
    }

}