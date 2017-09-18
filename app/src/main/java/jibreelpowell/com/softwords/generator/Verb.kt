package jibreelpowell.com.softwords.generator

import jibreelpowell.com.softwords.generator.GrammaticalNumber.*
import jibreelpowell.com.softwords.generator.GrammaticalPerson.*

class Verb(
        i: String,
        we: String,
        you: String,
        it: String,
        they: String
) {

    val firstPersonSingular = i.toLowerCase()
    val firstPersonPlural = we.toLowerCase()
    val secondPerson = you.toLowerCase()
    val thirdPersonSingular = it.toLowerCase()
    val thirdPersonPlural = they.toLowerCase()

    constructor(base: String) :
        this(i = base, we = base, you = base, it = base + 's', they = base)
}

class VerbLibrary {

    val words: List<Verb> = listOf(
            Verb("fall"),
            Verb("swim"),
            Verb(i = "am", we = "are", you = "are", it = "is", they = "are"),
            Verb(i = "have", we = "have", you = "have", it = "has", they = "have"),
            Verb("run"),
            Verb("meander"),
            Verb("beget"),
            Verb("fortell"),
            Verb("live"),
            Verb("listen"),
            Verb("write")
    )

    fun random(): Verb = words[(0..(words.size - 1)).random()]

    fun random(number: GrammaticalNumber, person: GrammaticalPerson): String =
            when (person) {
                SECOND -> random().secondPerson
                FIRST ->
                    when (number) {
                        SINGULAR -> random().firstPersonSingular
                        PLURAL -> random().firstPersonPlural
                    }
                THIRD ->
                    when (number) {
                        SINGULAR -> random().thirdPersonSingular
                        PLURAL -> random().thirdPersonPlural
                    }
            }




}