package jibreelpowell.com.softwords.history.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import jibreelpowell.com.softwords.databinding.FragmentHistoryDetailBinding
import jibreelpowell.com.softwords.mainactivity.MainActivity
import javax.inject.Inject

class HistoryDetailFragment: Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

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

        viewModel.closeCallBack = {
            findNavController().popBackStack()
        }

        binding.viewModel = viewModel

        return binding.root
    }
}