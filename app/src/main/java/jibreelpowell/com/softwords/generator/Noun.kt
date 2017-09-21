package jibreelpowell.com.softwords.generator

import jibreelpowell.com.softwords.generator.GrammaticalNumber.*

class Noun(singular: String, plural: String): Word() {
    val singular: String = singular.toLowerCase()
    val plural: String = plural.toLowerCase()

    var number: GrammaticalNumber = SINGULAR

    constructor(singular: String) : this(singular, singular + 's')

    override fun asString() =
            when (number) {
                SINGULAR -> singular
                PLURAL -> plural
            }

    fun article(articleType: ArticleType): Article {
        val article = Article()
        article.type = articleType
        article.number = number
        article.precedes(this)
        return article
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

