package com.lcardoso.helpietest.di

import com.lcardoso.helpietest.data.HelpieApi
import com.lcardoso.helpietest.data.HelpieRepository
import com.lcardoso.helpietest.ui.photos.PhotosViewModel
import com.lcardoso.helpietest.ui.posts.PostsViewModel
import com.lcardoso.helpietest.ui.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppComponent {
    val appModule = module {

        single { HelpieApi.service }

        single { HelpieRepository(get()) }

        viewModel { UsersViewModel(get()) }
        viewModel { PostsViewModel(get()) }
        viewModel { PhotosViewModel(get()) }

    }
}