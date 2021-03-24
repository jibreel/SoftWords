package jibreelpowell.com.softwords.generate.generator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prepositions")
data class Preposition(@PrimaryKey val name: String): Word() {

    override fun toString() = name
    override val partOfSpeech: PartOfSpeech
        get() {
            return PartOfSpeech.PREPOSITION
        }
}