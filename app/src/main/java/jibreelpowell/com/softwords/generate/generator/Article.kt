package jibreelpowell.com.softwords.generate.generator

import jibreelpowell.com.softwords.generate.generator.Article.Articles.*
import jibreelpowell.com.softwords.generate.generator.ArticleType.DEFINITE
import jibreelpowell.com.softwords.generate.generator.ArticleType.INDEFINITE
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.PLURAL
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.SINGULAR

data class Article(val article: String, val type: ArticleType, val number: GrammaticalNumber): Word() {

    override fun toString(): String = article
    override val partOfSpeech: PartOfSpeech
        get() {
            return PartOfSpeech.ARTICLE
        }

    private enum class Articles(val article: Article) {
        THE_SINGULAR(Article("the", DEFINITE, SINGULAR)),
        THE_PLURAL(Article("the", DEFINITE, PLURAL)),
        A(Article("a", INDEFINITE, SINGULAR)),
        AN(Article("an", INDEFINITE, SINGULAR)),
        EMPTY(Article("", INDEFINITE, SINGULAR))
    }

    companion object {
        fun forNoun(noun: Noun, type: ArticleType): Article {
            val number = noun.number
            return when {
                type == INDEFINITE && number == SINGULAR -> if (noun.startsWithVowel) A.article else AN.article
                type == INDEFINITE && number == PLURAL -> EMPTY.article
                type == DEFINITE && number == SINGULAR -> THE_SINGULAR.article
                type == DEFINITE && number == PLURAL -> THE_PLURAL.article
                else -> throw IllegalStateException("Impossible combination of ArticleType and GrammaticalNumber")
            }
        }
    }
}