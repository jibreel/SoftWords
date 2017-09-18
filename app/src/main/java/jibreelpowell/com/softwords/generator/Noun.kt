package jibreelpowell.com.softwords.generator

import jibreelpowell.com.softwords.generator.GrammaticalNumber.*

class Noun(singular: String, plural: String) {
    val singular: String = singular.toLowerCase()
    val plural: String = plural.toLowerCase()

    constructor(singular: String) : this(singular, singular + 's')


}

class NounLibrary {

    val words: List<Noun> = listOf(
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

    fun random() = words.random()

    fun random(number: GrammaticalNumber) =
            when (number) {
                SINGULAR -> random().singular
                PLURAL -> random().plural
            }


 }

