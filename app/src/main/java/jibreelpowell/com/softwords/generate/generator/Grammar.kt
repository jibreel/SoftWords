package jibreelpowell.com.softwords.generate.generator

import kotlin.random.Random


enum class GrammaticalNumber {
    SINGULAR,
    PLURAL;

    companion object {
        fun random() =
                when (Random.nextBoolean()) {
                    true -> SINGULAR
                    false -> PLURAL
                }
    }

}

enum class GrammaticalPerson {
    FIRST,
    SECOND,
    THIRD
}

enum class ArticleType {
    DEFINITE,
    INDEFINITE;

    companion object {
        fun random(): ArticleType {
            return when (Random.nextBoolean()) {
                true -> DEFINITE
                false -> INDEFINITE
            }
        }
    }
}

abstract class Word {
    abstract override fun toString(): String
    abstract val partOfSpeech: PartOfSpeech

    enum class PartOfSpeech {
        ARTICLE,
        NOUN,
        VERB,
        PREPOSITION
    }
}
