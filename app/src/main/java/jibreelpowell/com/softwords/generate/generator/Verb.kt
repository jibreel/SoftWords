package jibreelpowell.com.softwords.generate.generator

import androidx.room.Entity
import androidx.room.PrimaryKey
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.PLURAL
import jibreelpowell.com.softwords.generate.generator.GrammaticalNumber.SINGULAR
import jibreelpowell.com.softwords.generate.generator.GrammaticalPerson.*

@Entity(tableName = "verbs")
data class Verb(
    @PrimaryKey val firstPersonSingular: String,
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

    override val partOfSpeech: PartOfSpeech
        get() {
            return PartOfSpeech.VERB
        }

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

    fun withNumber(number: GrammaticalNumber): Verb {
        this.number = number
        return this
    }

    fun withPerson(person: GrammaticalPerson): Verb {
        this.person = person
        return this
    }
}

