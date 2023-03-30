package com.prateek.mycbseguide.api

import com.prateek.mycbseguide.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface MyCbseGuideApi {

    @GET("category/all/")
    suspend fun getCategories() : Response<ApiResponse>
}