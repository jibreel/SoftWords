package jibreelpowell.com.softwords.history

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.storage.AppDatabase
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.utils.TrampolineSchedulerProvider
import jibreelpowell.com.softwords.utils.getValue
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness


@RunWith(AndroidJUnit4::class)
class HistoryViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: HistoryViewModel
    lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        val schedulerProvider = TrampolineSchedulerProvider()
        appDatabase.sentencesDao().insertAll(
            GeneratedSentence.newInstance("Sentence One"),
            GeneratedSentence.newInstance("Sentence Two")
        )
            .scheduleCompletableInBackground(schedulerProvider)
            .subscribeBy(
                onError = { Log.e("HistoryViewModelTest#Setup", "Error populating database", it) },
                onComplete = { Log.d("HistoryViewModelTest#Setup", "Successfully populated database")}
            )
        viewModel = HistoryViewModel(appDatabase.sentencesDao(), schedulerProvider)
    }

    @After
    fun tearDown() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }

    @Test
    fun testDeleteSentence() {
        val startSize = getValue(viewModel.allSentences).size
        viewModel.delete(0)
        val endValue = getValue(viewModel.allSentences).size
        assertEquals(startSize - 1, endValue)
    }

    @Test
    fun testDeleteWhenEmpty() {
        viewModel.delete(0)
        viewModel.delete(0)
        val emptySize = getValue(viewModel.allSentences).size
        viewModel.delete(0)
        assertEquals(emptySize, getValue(viewModel.allSentences).size)
    }
}