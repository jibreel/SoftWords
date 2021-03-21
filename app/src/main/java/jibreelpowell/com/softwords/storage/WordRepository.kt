package jibreelpowell.com.softwords.storage

import io.reactivex.rxjava3.core.Single
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
}