package jibreelpowell.com.softwords.history.item

import android.view.View
import androidx.databinding.ObservableField
import androidx.navigation.findNavController
import jibreelpowell.com.softwords.history.HistoryFragmentDirections
import jibreelpowell.com.softwords.storage.GeneratedSentence
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class HistoryItemPresenter @Inject constructor() {

    private var id: Long = ID_NOT_SET
    val sentence = ObservableField<String>()
    val timestamp = ObservableField<String>()

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M/d/YYYY h:mm a")

    fun bind(generatedSentence: GeneratedSentence) {
        this.id = generatedSentence.id
        this.sentence.set(generatedSentence.sentence)
        this.timestamp.set(generatedSentence.timestamp.format(formatter))
    }

    fun onClick(view: View) {
        view.findNavController()
            .navigate(HistoryFragmentDirections.actionOpenHistoryDetail(id))
    }

    companion object {
        const val ID_NOT_SET = -1L
    }
}