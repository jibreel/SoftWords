package jibreelpowell.com.softwords.generate.generator

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import jibreelpowell.com.softwords.generate.generator.GrammaticalPerson.THIRD
import jibreelpowell.com.softwords.storage.WordRepository
import javax.inject.Inject

class Generator @Inject constructor(private val repository: WordRepository) {

    fun generateRandomSentence(pattern: Pattern): Single<Sentence> {
        val numNouns = pattern.parts.count { it == Word.PartOfSpeech.NOUN }
        val numVerbs = pattern.parts.count { it == Word.PartOfSpeech.VERB }
        val numPreps = pattern.parts.count { it == Word.PartOfSpeech.PREPOSITION }

        val nounSingle =
            (if (numNouns > 0) {
                repository.getRandomNouns(numNouns)
            } else {
                Single.just(arrayListOf())
            }).subscribeOn(Schedulers.io())

        val verbSingle =
            (if (numVerbs > 0) {
                repository.getRandomVerbs(numVerbs)
            } else {
                Single.just(arrayListOf())
            }).subscribeOn(Schedulers.io())

        val prepSingle =
            (if (numPreps > 0) {
                repository.getRandomPreposition(numPreps)
            } else {
                Single.just(arrayListOf())
            }).subscribeOn(Schedulers.io())

        return Single.zip(nounSingle, verbSingle, prepSingle) { nouns, verbs, preps ->
            val packet = Packet(nouns, verbs, preps)
            packet
        }.map { (nouns, verbs, preps) ->
            return@map when (pattern) {
                Pattern.NounVerb -> {
                    val noun = nouns[0].withNumber(GrammaticalNumber.random())
                    val verb = verbs[0].withNumber(noun.number).withPerson(THIRD)
                    Sentence(noun, verb)
                }
                Pattern.ArticleNounVerb -> {
                    val noun = nouns[0].withNumber(GrammaticalNumber.random())
                    val article = noun.article()
                    val verb = verbs[0].withNumber(noun.number).withPerson(THIRD)
                    Sentence(article, noun, verb)
                }
                Pattern.ArticleNounPrepositionArticleNoun -> {
                    val noun1 = nouns[0].withNumber(GrammaticalNumber.random())
                    val article1 = noun1.article()
                    val prep = preps[0]
                    val noun2 = nouns[1].withNumber(GrammaticalNumber.random())
                    val article2 = noun2.article()
                    Sentence(article1, noun1, prep, article2, noun2)
                }
                Pattern.ArticleNounVerbArticleNoun -> {
                    val noun1 = nouns[0].withNumber(GrammaticalNumber.random())
                    val article1 = noun1.article()
                    val verb = verbs[0].withNumber(noun1.number).withPerson(THIRD)
                    val noun2 = nouns[1].withNumber(GrammaticalNumber.random())
                    val article2 = noun2.article()
                    Sentence(article1, noun1, verb, article2, noun2)
                }
            }
        }
    }

    private data class Packet(
        val nouns: List<Noun>,
        val verbs: List<Verb>,
        val prepositions: List<Preposition>
    )

}