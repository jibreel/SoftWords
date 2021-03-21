package jibreelpowell.com.softwords.storage

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import jibreelpowell.com.softwords.generate.generator.Noun
import jibreelpowell.com.softwords.generate.generator.Preposition
import jibreelpowell.com.softwords.generate.generator.Verb
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotApiService
import jibreelpowell.com.softwords.network.words.WordsApiService
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val nounDao: NounDao,
    private val verbDao: VerbDao,
    private val prepositionDao: PrepositionDao,
    private val wordsApiService: WordsApiService,
    private val linguaRobotApiService: LinguaRobotApiService
) {

    fun getRandomNouns(number: Int): Single<List<Noun>> = nounDao.loadRandom(number)

    fun getRandomVerbs(number: Int): Single<List<Verb>> = verbDao.loadRandom(number)

    fun getRandomPreposition(number: Int): Single<List<Preposition>> = prepositionDao.loadRandom(number)

    private fun fetchNewNoun(): Single<Noun> {
        return wordsApiService.getRandomNoun()
            .map {
                val word = it.word
                if (word.endsWith('s', true)) {
                    val sliced = word.slice(0 until word.lastIndex)
                    Noun(sliced, word)
                } else {
                    Noun(word)
                }
            }
    }

    private fun fetchNewVerb(): Single<Verb> {
        return wordsApiService.getRandomVerb()
            .map {
                val word = it.word
                if (word.contains(" ")) {
                    throw IllegalArgumentException("Cannot process compound verbs")
                }
                Verb(word)
            }
    }

    private fun fetchNewPreposition(): Single<Preposition> {
        return wordsApiService.getRandomPreposition().map { Preposition(it.word) }
    }

    fun addNewNounToStorage(): Completable {
        return fetchNewNoun()
            .subscribeOn(Schedulers.io())
            .concatMapCompletable {
                nounDao.insertAll(arrayListOf(it))
            }
    }

    fun addNewVerbToStorage(): Completable {
        return fetchNewVerb()
            .subscribeOn(Schedulers.io())
            .concatMapCompletable {
                verbDao.insertAll(arrayListOf(it))
            }
    }

    fun addNewPrepositionToStorage(): Completable {
        return fetchNewPreposition()
            .subscribeOn(Schedulers.io())
            .concatMapCompletable {
                prepositionDao.insertAll(arrayListOf(it))
            }
    }
}