package com.lcardoso.helpietest.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lcardoso.helpietest.data.HelpieRepository
import com.lcardoso.helpietest.data.model.StateResponse
import com.lcardoso.helpietest.data.model.UserResponse

class UsersViewModel(private val repository: HelpieRepository) : ViewModel() {

    val userResponse: LiveData<StateResponse<List<UserResponse>>> get() = _usersResponse
    private val _usersResponse = MutableLiveData<StateResponse<List<UserResponse>>>()

    fun getUsers() {

        _usersResponse.value = StateResponse.StateLoading()
        repository.run {
            getUsers().subscribe(
                { users -> _usersResponse.value = StateResponse.StateSuccess(orderList(users)) },
                { e -> _usersResponse.value = StateResponse.StateError(e) })
        }
    }

    private fun orderList(users: List<UserResponse>) = users.sortedBy { it.name }

    class UsersViewModelFactory(private val repository: HelpieRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UsersViewModel(repository) as T
        }
    }
}
