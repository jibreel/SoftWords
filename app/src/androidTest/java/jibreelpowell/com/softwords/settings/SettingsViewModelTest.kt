package jibreelpowell.com.softwords.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import jibreelpowell.com.softwords.generate.generator.Word
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotApiService
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotResponse
import jibreelpowell.com.softwords.network.words.WordsApiService
import jibreelpowell.com.softwords.network.words.WordsResponse
import jibreelpowell.com.softwords.storage.AppDatabase
import jibreelpowell.com.softwords.storage.WordRepository
import jibreelpowell.com.softwords.utils.SampleJson
import jibreelpowell.com.softwords.utils.TrampolineSchedulerProvider
import jibreelpowell.com.softwords.utils.getValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.quality.Strictness

@RunWith(AndroidJUnit4::class)
class SettingsViewModelTest {

    lateinit var viewModel: SettingsViewModel

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val wordsApiService = mock<WordsApiService> {
        on { getRandomNoun() } doReturn Single.just(Gson().fromJson(SampleJson.WORDS_TABLE, WordsResponse::class.java))
        on { getRandomVerb() } doReturn Single.just(Gson().fromJson(SampleJson.WORDS_TABLE, WordsResponse::class.java))
        on { getRandomPreposition() } doReturn Single.just(Gson().fromJson(SampleJson.WORDS_RESPONSE_VERSUS, WordsResponse::class.java))
    }

    private val linguaRobotApiService = mock<LinguaRobotApiService> {
        on { getEntry("table") } doReturn Single.just(Gson().fromJson(SampleJson.LINGUA_ROBOT_TABLE, LinguaRobotResponse::class.java))
    }

    lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        val schedulerProvider = TrampolineSchedulerProvider()
        val wordRepository = WordRepository(
            appDatabase.nounDao(),
            appDatabase.verbDao(),
            appDatabase.prepositionDao(),
            schedulerProvider,
            wordsApiService,
            linguaRobotApiService
        )
        viewModel = SettingsViewModel(wordRepository, schedulerProvider)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun addNewNoun() {
        viewModel.addNewNoun()
        assertEquals(Word.PartOfSpeech.NOUN, getValue(viewModel.storageResult).getOrNull())
    }

    @Test
    fun addNewVerb() {
        viewModel.addNewVerb()
        assertEquals(Word.PartOfSpeech.VERB, getValue(viewModel.storageResult).getOrNull())
    }

    @Test
    fun addNewPreposition() {
        viewModel.addNewPreposition()
        assertEquals(Word.PartOfSpeech.PREPOSITION, getValue(viewModel.storageResult).getOrNull())
    }
}