package jibreelpowell.com.softwords.storage

import io.reactivex.rxjava3.core.Single
import jibreelpowell.com.softwords.generate.generator.Noun
import jibreelpowell.com.softwords.generate.generator.Preposition
import jibreelpowell.com.softwords.generate.generator.Verb
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val nounDao: NounDao,
    private val verbDao: VerbDao,
    private val prepositionDao: PrepositionDao
) {

    fun getRandomNouns(number: Int): Single<List<Noun>> = nounDao.loadRandom(number)

    fun getRandomVerbs(number: Int): Single<List<Verb>> = verbDao.loadRandom(number)

    fun getRandomPreposition(number: Int): Single<List<Preposition>> = prepositionDao.loadRandom(number)
}