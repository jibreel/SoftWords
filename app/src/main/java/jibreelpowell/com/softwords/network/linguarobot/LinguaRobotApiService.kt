package jibreelpowell.com.softwords.network.linguarobot

import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface LinguaRobotApiService {

    @GET("/language/v1/entries/en/{entry}")
    suspend fun getEntry(@Path("entry") entry: String): LinguaRobotResponse

}