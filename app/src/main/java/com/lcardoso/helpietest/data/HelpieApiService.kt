package com.lcardoso.helpietest.data

import com.lcardoso.helpietest.data.model.PhotoResponse
import com.lcardoso.helpietest.data.model.PostResponse
import com.lcardoso.helpietest.data.model.UserResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface HelpieApiService {

    @GET("users")
    fun getUsers(): Observable<List<UserResponse>>

    @GET("posts")
    fun getPosts(@Query("userId") userId: Int): Observable<List<PostResponse>>

    @GET("photos")
    fun getPhotos(@Query("albumId") albumId: Int): Observable<List<PhotoResponse>>
}