package jibreelpowell.com.softwords.generate.generator

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.PLURAL
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.SINGULAR

@Entity(tableName = "nouns")
data class Noun(
    @PrimaryKey val singular: String,
    val plural: String
) : Word() {

    @Ignore
    var number: GrammaticalNumber = SINGULAR

    constructor(singular: String) : this(singular, singular + 's')

    override val partOfSpeech: PartOfSpeech
        get() {
            return PartOfSpeech.NOUN
        }

    val startsWithVowel: Boolean
        get() {
            return when (number) {
                SINGULAR -> singular[0].isVowel()
                PLURAL -> plural[0].isVowel()
            }
        }

    override fun toString() =
        when (number) {
            SINGULAR -> singular
            PLURAL -> plural
        }

    fun article(articleType: ArticleType? = null): Article =
        Article.forNoun(this, articleType ?: ArticleType.random())

    fun withNumber(grammaticalNumber: GrammaticalNumber): Noun {
        number = grammaticalNumber
        return this
    }


}

