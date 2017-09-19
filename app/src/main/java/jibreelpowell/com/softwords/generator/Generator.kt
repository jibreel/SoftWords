package jibreelpowell.com.softwords.generator

import jibreelpowell.com.softwords.generator.GrammaticalPerson.*
import java.util.*

enum class Pattern {
    NounVerb {
        override fun sentence(): String {
            val isSingular = Random().nextBoolean()
            val number: GrammaticalNumber = GrammaticalNumber.get(isSingular)
            val noun = Noun.random()
            noun.number = number
            val verb = Verb.random()
            verb.person = THIRD
            verb.number = number
            return sentence(noun, verb)
        }
    },

    ArticleNounVerb {
        override fun sentence(): String {
            val isSingular = Random().nextBoolean()
            val number = GrammaticalNumber.get(isSingular)
            val articleType = ArticleType.get(Random().nextBoolean())
            val noun = Noun.random()
            noun.number = number
            val article = noun.article(articleType)
            val verb = Verb.random()
            verb.number = number
            verb.person = THIRD
            return sentence(article, noun, verb)
        }

    };

    abstract fun sentence(): String

    fun sentence(vararg words: Word): String {
        var sentence = ""
        for ((index, word) in words.withIndex()) {
            sentence += word.asString()
            if (words.lastIndex != index) {
                sentence += " "
            }
        }
        return sentence
    }

    companion object Factory{
        fun random(): Pattern = Pattern.values().random()
    }
}
