package jibreelpowell.com.softwords.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jibreelpowell.com.softwords.databinding.FragmentHistoryBinding
import jibreelpowell.com.softwords.mainactivity.MainActivity
import javax.inject.Inject

class HistoryFragment : Fragment() {

    @Inject lateinit var presenter: HistoryPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generateComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.presenter = presenter
        return binding.root
    }

}