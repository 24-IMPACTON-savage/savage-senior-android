package team.retum.savage_android.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import team.retum.savage_android.model.base.BaseResponse
import team.retum.savage_android.model.request.SignUpRequest

interface UserApi {
    @POST("/user/senior")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): BaseResponse<Int>

    @GET("/user")
    suspend fun getUser(): BaseResponse<Boolean>

    @POST("/user/visa")
    suspend fun authenticationVisa(
        @Body passport: String
    ): BaseResponse<Boolean>
}
