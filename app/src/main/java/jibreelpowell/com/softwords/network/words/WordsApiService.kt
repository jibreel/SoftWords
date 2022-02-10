package jibreelpowell.com.softwords.network.words

import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface WordsApiService {

    @GET("/words/?random=true&partOfSpeech=verb")
    suspend fun getRandomVerb(): WordsResponse

    @GET("/words/?random=true&partOfSpeech=noun")
    suspend fun getRandomNoun(): WordsResponse

    @GET("/words/?random=true&partOfSpeech=verb")
    suspend fun getRandomPreposition(): WordsResponse
}