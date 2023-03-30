package com.prateek.mycbseguide.repository

import com.prateek.mycbseguide.api.MyCbseGuideApi
import com.prateek.mycbseguide.models.ApiResponse
import com.prateek.mycbseguide.models.ApiResponseWrapper
import com.prateek.mycbseguide.models.Categories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CategoriesRepository @Inject constructor(private val myCbseGuideApi: MyCbseGuideApi) {

    private val _categoriesFlow = MutableStateFlow<ApiResponseWrapper<List<Categories>>>(ApiResponseWrapper.Init())
    val categoriesFlow : StateFlow<ApiResponseWrapper<List<Categories>>>
        get() = _categoriesFlow

    private val _connectionStatusFlow = MutableStateFlow<Boolean>(false)
    val connectionStatusFlow : StateFlow<Boolean>
        get() = _connectionStatusFlow


    suspend fun getAllCategories(){
            _connectionStatusFlow.emit(true)
            _categoriesFlow.emit(ApiResponseWrapper.Loading())
            try{
                val response = myCbseGuideApi.getCategories()
                handleResponse(response)
            }catch (e : SocketTimeoutException){
                _categoriesFlow.emit(ApiResponseWrapper.Timeout())
            }catch (e: UnknownHostException){
              _categoriesFlow.emit(ApiResponseWrapper.Offline())
            } catch (e: java.lang.Exception){
                e.printStackTrace()
                _categoriesFlow.emit(ApiResponseWrapper.UnknownError())
            }
    }

    private suspend fun handleResponse(response: Response<ApiResponse>) {
        if(response.isSuccessful && response.body()!=null && response.code() == 200){
            _categoriesFlow.emit(ApiResponseWrapper.Success(response.body()!!.categories))
        }
        else if(response.isSuccessful && response.body() == null){
            _categoriesFlow.emit(ApiResponseWrapper.KnownError("Empty Response"))
        }
        else{
            _categoriesFlow.emit(ApiResponseWrapper.UnknownError())
        }

    }

}