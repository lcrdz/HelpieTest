package com.lcardoso.helpietest.data

import com.lcardoso.helpietest.data.model.PhotoResponse
import com.lcardoso.helpietest.data.model.PostResponse
import com.lcardoso.helpietest.data.model.UserResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HelpieRepository {

    private val api = HelpieApi.service

    fun getUsers(): Observable<List<UserResponse>> {
        return api.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPosts(userId: Int): Observable<List<PostResponse>> {
        return api.getPosts(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPhotos(albumId: Int): Observable<List<PhotoResponse>>? {
        return api.getPhotos(albumId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}