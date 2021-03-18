package jibreelpowell.com.softwords.generate.generator

import androidx.room.Entity
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.PLURAL
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.SINGULAR
import jibreelpowell.com.softwords.generate.generator.GrammaticalPerson.*

@Entity(tableName = "verbs")
data class Verb(
        val firstPersonSingular: String,
        val firstPersonPlural: String,
        val secondPerson: String,
        val thirdPersonSingular: String,
        val thirdPersonPlural: String
): Word() {

    var number: GrammaticalNumber = SINGULAR
    var person: GrammaticalPerson = FIRST

    constructor(base: String) :
        this(
            firstPersonSingular = base,
            firstPersonPlural = base,
            secondPerson = base,
            thirdPersonSingular = base + 's',
            thirdPersonPlural = base
        )

    override fun toString() =
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
}

