package jibreelpowell.com.softwords.network.linguarobot

import jibreelpowell.com.softwords.generate.generator.Noun
import jibreelpowell.com.softwords.generate.generator.Verb

object LinguaRobotConverter {

    fun convertResponseToNoun(response: LinguaRobotResponse): Noun {
        val nouns = response.entries.flatMap { it.lexemes }.filter { it.partOfSpeech == NOUN }
        if (nouns.isEmpty()) {
            throw IllegalStateException("Word has no senses that are nouns")
        }
        val lexeme = nouns[0]
        val forms = lexeme.forms.filterNot { it.labels?.contains(OBSOLETE) ?: false }
        return if (forms.isNotEmpty()) {
            val singular = forms.firstOrNull { form ->
                form.grammar.any { it.number?.contains(SINGULAR) ?: false}
            }?.form ?: lexeme.lemma
            val plural =
                forms.firstOrNull { form ->
                    form.grammar.any { it.number?.contains(PLURAL) ?: false}
                }?.form ?: lexeme.lemma
            Noun(singular, plural)
        } else {
            Noun(lexeme.lemma, lexeme.lemma)
        }
    }

    fun convertResponseToVerb(response: LinguaRobotResponse): Verb {
        val verbs = response.entries.flatMap { it.lexemes }.filter { it.partOfSpeech == VERB }
        if (verbs.isEmpty()) {
            throw IllegalStateException("Word has no senses that are verbs")
        }
        val lexeme = verbs[0]
        val forms = lexeme.forms
            .filterNot { it.labels?.contains(OBSOLETE) ?: false }
            .filter { form -> form.grammar.any {
                it.tense?.contains(PRESENT) ?: false &&
                        it.mood?.contains(INDICATIVE) ?: false
            }
            }
        return if (forms.isNotEmpty()) {
            val firstPersonSingular = forms.firstOrNull { form -> form.grammar.any {
                (it.person?.contains(FIRST_PERSON) ?: false) && (it.number?.contains(SINGULAR) ?: false)
            }}?.form ?: lexeme.lemma
            val firstPersonPlural = forms.firstOrNull { form -> form.grammar.any {
                (it.person?.contains(FIRST_PERSON) ?: false) && (it.number?.contains(PLURAL) ?: false)
            }}?.form ?: lexeme.lemma
            val secondPerson = forms.firstOrNull { form -> form.grammar.any {
                it.person?.contains(SECOND_PERSON) ?: false
            }}?.form ?: lexeme.lemma
            val thirdPersonSingular = forms.firstOrNull { form -> form.grammar.any {
                (it.person?.contains(THIRD_PERSON) ?: false) && (it.number?.contains(SINGULAR) ?: false)
            }}?.form ?: lexeme.lemma
            val thirdPersonPlural = forms.firstOrNull { form -> form.grammar.any {
                (it.person?.contains(THIRD_PERSON) ?: false) && (it.number?.contains(PLURAL) ?: false)
            }}?.form ?: lexeme.lemma
            Verb(
                firstPersonSingular = firstPersonSingular,
                firstPersonPlural = firstPersonPlural,
                secondPerson = secondPerson,
                thirdPersonSingular = thirdPersonSingular,
                thirdPersonPlural = thirdPersonPlural
            )
        } else {
            Verb(lexeme.lemma, lexeme.lemma, lexeme.lemma, lexeme.lemma, lexeme.lemma)
        }
    }

        const val NOUN = "noun"
        const val VERB = "verb"
        const val OBSOLETE = "obsolete"
        const val SINGULAR = "singular"
        const val PLURAL = "plural"
        const val PRESENT = "present"
        const val INDICATIVE = "indicative"
        const val FIRST_PERSON = "first-person"
        const val SECOND_PERSON = "second-person"
        const val THIRD_PERSON = "third-person"
}