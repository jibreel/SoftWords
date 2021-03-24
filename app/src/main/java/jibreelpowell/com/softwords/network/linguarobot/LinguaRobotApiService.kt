package jibreelpowell.com.softwords.network.linguarobot

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface LinguaRobotApiService {

    @GET("/language/v1/entries/en/{entry}")
    fun getEntry(@Path("entry") entry: String): Single<LinguaRobotResponse>

}