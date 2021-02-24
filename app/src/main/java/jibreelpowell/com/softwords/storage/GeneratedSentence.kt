package jibreelpowell.com.softwords.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "sentences")
data class GeneratedSentence(
    val timestamp: Long,
    val sentence: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        fun newInstance(sentence: String): GeneratedSentence {
            return GeneratedSentence(ZonedDateTime.now().toEpochSecond(), sentence)
        }
    }
}