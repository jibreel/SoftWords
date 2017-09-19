package jibreelpowell.com.softwords.generator

import jibreelpowell.com.softwords.generator.GrammaticalNumber.*
import jibreelpowell.com.softwords.generator.GrammaticalPerson.*

class Verb(
        i: String,
        we: String,
        you: String,
        it: String,
        they: String
): Word() {

    val firstPersonSingular = i.toLowerCase()
    val firstPersonPlural = we.toLowerCase()
    val secondPerson = you.toLowerCase()
    val thirdPersonSingular = it.toLowerCase()
    val thirdPersonPlural = they.toLowerCase()

    var number: GrammaticalNumber = SINGULAR
    var person: GrammaticalPerson = FIRST

    constructor(base: String) :
        this(i = base, we = base, you = base, it = base + 's', they = base)

    override fun asString() =
        when (person) {
            SECOND -> secondPerson
            FIRST ->
                when (number) {
                    SINGULAR -> firstPersonSingular
                    PLURAL -> firstPersonPlural
                }
            THIRD ->
                when (number) {
                    SINGULAR -> thirdPersonSingular
                    PLURAL -> thirdPersonPlural
                }
        }


    companion object Library{

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

        fun random(): Verb = words.random()
    }
}

