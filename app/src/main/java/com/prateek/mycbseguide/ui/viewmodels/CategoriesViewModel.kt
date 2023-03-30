package com.prateek.mycbseguide.ui.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prateek.mycbseguide.constants.RequestStatus
import com.prateek.mycbseguide.models.ApiResponseWrapper
import com.prateek.mycbseguide.models.Categories
import com.prateek.mycbseguide.repository.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class CategoriesViewModel @Inject constructor(private val categoriesRepository: CategoriesRepository) : ViewModel() {

    private val _categoriesLiveData : MutableLiveData<List<Categories>> = MutableLiveData()
    val categoriesLiveData : LiveData<List<Categories>>
        get() = _categoriesLiveData

    private val _requestStatus : MutableLiveData<RequestStatus> = MutableLiveData()
    val requestStatus : LiveData<RequestStatus>
        get() = _requestStatus

    fun fetchCategories(){
        viewModelScope.launch {
            categoriesRepository.getAllCategories()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesRepository.categoriesFlow.collect{
                handleDataFromFlow(it)
            }
        }
    }

    private fun handleDataFromFlow(it: ApiResponseWrapper<List<Categories>>) {
        when(it){
            is ApiResponseWrapper.Success -> {
                _requestStatus.postValue(RequestStatus.SUCCESS)
                _categoriesLiveData.postValue(it.data!!)
            }
            is ApiResponseWrapper.Init -> _requestStatus.postValue(RequestStatus.INIT)
            is ApiResponseWrapper.KnownError -> _requestStatus.postValue(RequestStatus.FAILED)
            is ApiResponseWrapper.Loading -> _requestStatus.postValue(RequestStatus.LOADING)
            is ApiResponseWrapper.Timeout -> _requestStatus.postValue(RequestStatus.TIMEOUT)
            is ApiResponseWrapper.UnknownError -> _requestStatus.postValue(RequestStatus.UNKNOWN_ERROR)
            is ApiResponseWrapper.Offline -> _requestStatus.postValue(RequestStatus.OFFLINE)
        }
    }
}