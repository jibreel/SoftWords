package jibreelpowell.com.softwords.generate.generator

import jibreelpowell.com.softwords.generate.generator.GrammaticalPerson.*
import java.util.*

enum class Pattern {
    NounVerb {
        override fun sentence(): String {
            val number: GrammaticalNumber = GrammaticalNumber.get(Random().nextBoolean())
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
            val number = GrammaticalNumber.get(Random().nextBoolean())
            val articleType = ArticleType.get(Random().nextBoolean())
            val noun = Noun.random()
            noun.number = number
            val article = noun.article(articleType)
            val verb = Verb.random()
            verb.number = number
            verb.person = THIRD
            return sentence(article, noun, verb)
        }

    },

    NounPrepositionNoun {
        override fun sentence(): String {
            val number1 = GrammaticalNumber.get(Random().nextBoolean())
            val articleType1 = ArticleType.get(Random().nextBoolean())
            val noun1 = Noun.random()
            noun1.number = number1
            val article1 = noun1.article(articleType1)
            val number2 = GrammaticalNumber.get(Random().nextBoolean())
            val articleType2 = ArticleType.get(Random().nextBoolean())
            val noun2 = Noun.random()
            noun2.number = number2
            val article2 = noun2.article(articleType2)
            val preposition = Preposition.random()
            return sentence(article1, noun1, preposition, article2, noun2)
        }
    },

    NounPrepositionVerbNoun {
        override fun sentence(): String {
            val number1 = GrammaticalNumber.get(Random().nextBoolean())
            val articleType1 = ArticleType.get(Random().nextBoolean())
            val noun1 = Noun.random()
            noun1.number = number1
            val article1 = noun1.article(articleType1)
            val number2 = GrammaticalNumber.get(Random().nextBoolean())
            val articleType2 = ArticleType.get(Random().nextBoolean())
            val noun2 = Noun.random()
            noun2.number = number2
            val article2 = noun2.article(articleType2)
            val verb = Verb.random()
            verb.number = number1
            verb.person = THIRD
            return sentence(article1, noun1, verb, article2, noun2)
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
