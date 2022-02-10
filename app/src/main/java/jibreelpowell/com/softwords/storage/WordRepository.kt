package jibreelpowell.com.softwords.storage

import jibreelpowell.com.softwords.generate.generator.Noun
import jibreelpowell.com.softwords.generate.generator.Preposition
import jibreelpowell.com.softwords.generate.generator.Verb
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotApiService
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotConverter
import jibreelpowell.com.softwords.network.words.WordsApiService
import jibreelpowell.com.softwords.network.words.WordsResponse
import jibreelpowell.com.softwords.utils.DispatcherProvider
import jibreelpowell.com.softwords.utils.NoViableWordException
import jibreelpowell.com.softwords.utils.SchedulerProvider
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WordRepository : KoinComponent {

    private val nounDao: NounDao by inject()
    private val verbDao: VerbDao by inject()
    private val prepositionDao: PrepositionDao by inject()
    private val dispatcherProvider: DispatcherProvider by inject()
    private val wordsApiService: WordsApiService by inject()
    private val linguaRobotApiService: LinguaRobotApiService by inject()

    suspend fun getRandomNouns(number: Int): List<Noun> {
        return withContext(dispatcherProvider.io) {
            nounDao.loadRandom(number)
        }
    }

    suspend fun getRandomVerbs(number: Int): List<Verb> {
        return withContext(dispatcherProvider.io) {
            verbDao.loadRandom(number)
        }
    }

    suspend fun getRandomPreposition(number: Int): List<Preposition> {
        return withContext(dispatcherProvider.io) {
            prepositionDao.loadRandom(number)
        }
    }

    private suspend fun fetchNewNoun(): Noun {
        val noun = withContext(dispatcherProvider.io) {
            val wordsResponse = wordsApiService.getRandomNoun()
            val robotResponse = linguaRobotApiService.getEntry(wordsResponse.forceToSimpleWord())
            LinguaRobotConverter.convertResponseToNoun(robotResponse)
        }

        return noun
    }

    private suspend fun fetchNewVerb(): Verb {
        val verb = withContext(dispatcherProvider.io) {
            val wordsResponse = wordsApiService.getRandomVerb()
            val robotResponse = linguaRobotApiService.getEntry(wordsResponse.forceToSimpleWord())
            LinguaRobotConverter.convertResponseToVerb(robotResponse)
        }
        return verb
    }

    private suspend fun fetchNewPreposition(): Preposition {
        val prep = withContext(dispatcherProvider.io){
            val wordsResponse = wordsApiService.getRandomPreposition()
            wordsResponse.word

        }
        return Preposition(prep)
    }

    suspend fun addNewNounToStorage(): Result<Noun> {
        return try {
            val noun = fetchNewNoun()
            withContext(dispatcherProvider.io) {
                nounDao.insertAll(arrayListOf(noun))
            }
            Result.success(noun)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addNewVerbToStorage(): Result<Verb> {
        return try {
            val verb = fetchNewVerb()
            withContext(dispatcherProvider.io) {
                verbDao.insertAll(arrayListOf(verb))
            }
            Result.success(verb)
        } catch (e: Exception) {
            Result.failure(e)
        }


    }

    suspend fun addNewPrepositionToStorage(): Result<Preposition> {
        return try {
            val prep = fetchNewPreposition()
            withContext(dispatcherProvider.io) {
                prepositionDao.insertAll(arrayListOf(prep))
            }
            Result.success(prep)
        } catch (e: Exception) {
            Result.failure(e)
        }

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

    private fun WordsResponse.forceToSimpleWord(): String {
        return searchForSimpleWord(this) ?: throw NoViableWordException("Cannot process complex words")
    }
}