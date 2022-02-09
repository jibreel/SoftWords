package jibreelpowell.com.softwords.history.item

import androidx.databinding.ObservableField
import jibreelpowell.com.softwords.storage.GeneratedSentence
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class HistoryItemPresenter {

    val sentence = ObservableField<String>()
    val timestamp = ObservableField<String>()

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M/d/YYYY h:mm a")

    fun bind(generatedSentence: GeneratedSentence) {
        this.sentence.set(generatedSentence.sentence)
        this.timestamp.set(generatedSentence.timestamp.format(formatter))
    }
}