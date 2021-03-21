package jibreelpowell.com.softwords.network.words

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface WordsApiService {

    @GET("/words/?random=true&partOfSpeech=verb")
    fun getRandomVerb(): Single<WordsResponse>

    @GET("/words/?random=true&partOfSpeech=noun")
    fun getRandomNoun(): Single<WordsResponse>

    @GET("/words/?random=true&partOfSpeech=verb")
    fun getRandomPreposition(): Single<WordsResponse>
}