package com.lcardoso.helpietest.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lcardoso.helpietest.data.HelpieRepository
import com.lcardoso.helpietest.data.model.PhotoResponse
import com.lcardoso.helpietest.data.model.StateResponse

class PhotosViewModel(private val repository: HelpieRepository) : ViewModel() {

    val photosResponse: LiveData<StateResponse<List<PhotoResponse>>> get() = _photosResponse
    private val _photosResponse = MutableLiveData<StateResponse<List<PhotoResponse>>>()

    fun getPhotos(albumId: Int) {

        _photosResponse.value = StateResponse.StateLoading()
        repository.run {
            getPhotos(albumId)?.subscribe(
                { photos -> _photosResponse.value = StateResponse.StateSuccess(photos) },
                { e -> _photosResponse.value = StateResponse.StateError(e) })
        }
    }

    class PhotosViewModelFactory(private val repository: HelpieRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PhotosViewModel(repository) as T
        }
    }
}