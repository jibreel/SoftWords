package jibreelpowell.com.softwords.generate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jibreelpowell.com.softwords.databinding.FragmentGenerateBinding
import jibreelpowell.com.softwords.mainactivity.MainActivity
import javax.inject.Inject

class GenerateFragment : Fragment() {

    @Inject
    lateinit var viewModel: GenerateViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generateComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentGenerateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }
}