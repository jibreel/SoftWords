package jibreelpowell.com.softwords.generator

import jibreelpowell.com.softwords.generator.GrammaticalNumber.PLURAL
import jibreelpowell.com.softwords.generator.GrammaticalNumber.SINGULAR
import java.util.*

enum class Pattern {
    NounVerb{
        override fun sentence(): String {
            val isSingular = Random().nextBoolean()
            val number: GrammaticalNumber = when (isSingular) {
                true -> SINGULAR
                false -> PLURAL
            }
            return sentence(NounLibrary().random(number), VerbLibrary().random(number, GrammaticalPerson.THIRD))
        }
    };

    abstract fun sentence(): String

    fun sentence(vararg words: String): String {
        var sentence = ""
        for ((index, word) in words.withIndex()) {
            sentence += word
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
