package jibreelpowell.com.softwords.generate.generator

import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.PLURAL
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.SINGULAR

data class Noun(
    val singular: String,
    val plural: String
): Word() {

    var number: GrammaticalNumber = SINGULAR

    constructor(singular: String) : this(singular, singular + 's')

    override fun asString() =
            when (number) {
                SINGULAR -> singular
                PLURAL -> plural
            }

    fun article(articleType: ArticleType): Article = Article.forNoun(this, articleType)

    val startsWithVowel: Boolean
        get() {
            return when (number) {
                SINGULAR -> singular[0].isVowel()
                PLURAL -> plural[0].isVowel()
            }
        }

    companion object NounLibrary {

        val nouns: List<Noun> = listOf(
                Noun("leaf", "leaves"),
                Noun("water"),
                Noun("rock"),
                Noun("card"),
                Noun("fish", "fish"),
                Noun("person", "people"),
                Noun("bug"),
                Noun("galaxy", "galaxies"),
                Noun("napkin"),
                Noun("word")
        )

        fun random() = nouns.random()
    }
}

