package com.lcardoso.helpietest.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lcardoso.helpietest.data.HelpieRepository
import com.lcardoso.helpietest.data.model.PostResponse
import com.lcardoso.helpietest.data.model.StateResponse

class PostsViewModel(private val repository: HelpieRepository) : ViewModel() {

    val postResponse: LiveData<StateResponse<List<PostResponse>>> get() = _postResponse
    private val _postResponse = MutableLiveData<StateResponse<List<PostResponse>>>()

    fun getPosts(userId: Int) {

        _postResponse.value = StateResponse.StateLoading()
        repository.run {
            getPosts(userId).subscribe(
                { posts -> _postResponse.value = StateResponse.StateSuccess(posts) },
                { e -> _postResponse.value = StateResponse.StateError(e) })
        }
    }

    class PostsViewModelFactory(
        private val repository: HelpieRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsViewModel(repository) as T
        }
    }
}