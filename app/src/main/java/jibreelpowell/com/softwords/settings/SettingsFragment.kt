package jibreelpowell.com.softwords.settings

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
import jibreelpowell.com.softwords.databinding.FragmentSettingsBinding
import jibreelpowell.com.softwords.generate.generator.Word
import jibreelpowell.com.softwords.mainactivity.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import javax.inject.Inject

class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.storageResult.observe(viewLifecycleOwner) {
            when {
                it.isSuccess -> {
                    val pos = it.getOrNull()?.name?.toLowerCase(Locale.getDefault()) ?: "word"
                    Snackbar.make(
                        binding.root,
                        getString(R.string.added_new_word, pos),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                it.isFailure -> {
                    val lastRequested = viewModel.lastRequestedAddition
                    val snack = Snackbar.make(
                        binding.root,
                        R.string.error_adding_word,
                        Snackbar.LENGTH_LONG,
                    )
                    if (lastRequested != null && lastRequested != Word.PartOfSpeech.ARTICLE) {
                        snack.setAction(
                            R.string.retry
                        ) {
                            when (lastRequested) {
                                Word.PartOfSpeech.NOUN -> viewModel.addNewNoun()
                                Word.PartOfSpeech.VERB -> viewModel.addNewVerb()
                                Word.PartOfSpeech.PREPOSITION -> viewModel.addNewPreposition()
                                else -> throw IllegalStateException()
                            }
                        }
                    }
                    snack.show()
                }
            }
        }

        return binding.root
    }

}