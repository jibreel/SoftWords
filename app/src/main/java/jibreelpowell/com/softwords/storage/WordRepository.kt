package jibreelpowell.com.softwords.storage

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import jibreelpowell.com.softwords.generate.generator.Noun
import jibreelpowell.com.softwords.generate.generator.Preposition
import jibreelpowell.com.softwords.generate.generator.Verb
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotApiService
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotConverter
import jibreelpowell.com.softwords.network.words.WordsApiService
import jibreelpowell.com.softwords.network.words.WordsResponse
import jibreelpowell.com.softwords.utils.NoViableWordException
import jibreelpowell.com.softwords.utils.SchedulerProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject

class WordRepository : KoinComponent {

    private val nounDao: NounDao by inject()
    private val verbDao: VerbDao by inject()
    private val prepositionDao: PrepositionDao by inject()
    private val schedulerProvider: SchedulerProvider by inject()
    private val wordsApiService: WordsApiService by inject()
    private val linguaRobotApiService: LinguaRobotApiService by inject()

    fun getRandomNouns(number: Int): Single<List<Noun>> = nounDao.loadRandom(number)

    fun getRandomVerbs(number: Int): Single<List<Verb>> = verbDao.loadRandom(number)

    fun getRandomPreposition(number: Int): Single<List<Preposition>> = prepositionDao.loadRandom(number)

    private fun fetchNewNoun(): Single<Noun> {
        return wordsApiService.getRandomNoun()
            .map { wordsResponse ->
                searchForSimpleWord(wordsResponse) ?: throw NoViableWordException("Cannot process complex nouns")
            }
            .flatMap {
                linguaRobotApiService.getEntry(it)
            }.map {
                LinguaRobotConverter.convertResponseToNoun(it)
            }
    }

    private fun fetchNewVerb(): Single<Verb> {
        return wordsApiService.getRandomVerb()
            .map { wordsResponse ->
                searchForSimpleWord(wordsResponse) ?: throw NoViableWordException("Cannot process complex verbs")
            }.flatMap {
                linguaRobotApiService.getEntry(it)
            }.map {
                LinguaRobotConverter.convertResponseToVerb(it)
            }
    }

    private fun fetchNewPreposition(): Single<Preposition> {
        return wordsApiService.getRandomPreposition().map { Preposition(it.word) }
    }

    fun addNewNounToStorage(): Completable {
        return fetchNewNoun()
            .concatMapCompletable {
                nounDao.insertAll(arrayListOf(it))
            }
            .subscribeOn(schedulerProvider.io)
    }

    fun addNewVerbToStorage(): Completable {
        return fetchNewVerb()
            .concatMapCompletable {
                verbDao.insertAll(arrayListOf(it))
            }
            .subscribeOn(schedulerProvider.io)
    }

    fun addNewPrepositionToStorage(): Completable {
        return fetchNewPreposition()
            .concatMapCompletable {
                prepositionDao.insertAll(arrayListOf(it))
            }
            .subscribeOn(schedulerProvider.io)

    }

    private fun searchForSimpleWord(wordsResponse: WordsResponse): String? {
        return if (wordsResponse.word.contains(' ', true)) {
            val nouns = wordsResponse.results.filter { it.partOfSpeech == "noun" }
            val alternatives = arrayListOf<String>()
            alternatives.addAll(nouns.flatMap { it.synonyms ?: listOf() })
            alternatives.addAll(nouns.flatMap { it.typeOf ?: listOf() })
            alternatives.filterNot { it.contains(' ', true) }.firstOrNull()
        } else {
            wordsResponse.word
        }
    }
}