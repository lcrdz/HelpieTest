package com.lcardoso.helpietest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.lcardoso.helpietest.R
import com.lcardoso.helpietest.ui.photos.PhotosFragment
import com.lcardoso.helpietest.ui.users.UsersFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var usersFragment: UsersFragment
    lateinit var homeFragment: HomeFragment
    lateinit var photoFragment: PhotosFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFragments()
        setupButtons()
    }

    private fun setupFragments() {
        homeFragment = HomeFragment()
        usersFragment = UsersFragment()
        photoFragment = PhotosFragment()
        navigation(homeFragment)
        changeColorButton(HOME_FRAGMENT)
    }

    private fun setupButtons() {
        btnUsers.setOnClickListener {
            navigation(usersFragment)
            changeColorButton(USERS_FRAGMENT)
        }

        btnHome.setOnClickListener {
            navigation(homeFragment)
            changeColorButton(HOME_FRAGMENT)
        }

        btnPhotos.setOnClickListener {
            navigation(photoFragment)
            changeColorButton(PHOTOS_FRAGMENT)
        }
    }

    private fun changeColorButton(activeFragment: Int) = when (activeFragment) {
        HOME_FRAGMENT -> {
            btnHome.setImageResource(R.drawable.ic_home_select)
            btnUsers.setImageResource(R.drawable.ic_person)
            btnPhotos.setImageResource(R.drawable.ic_photo)
        }
        USERS_FRAGMENT -> {
            btnHome.setImageResource(R.drawable.ic_home)
            btnUsers.setImageResource(R.drawable.ic_person_select)
            btnPhotos.setImageResource(R.drawable.ic_photo)
        }
        PHOTOS_FRAGMENT -> {
            btnHome.setImageResource(R.drawable.ic_home)
            btnUsers.setImageResource(R.drawable.ic_person)
            btnPhotos.setImageResource(R.drawable.ic_image_select)
        }
        else -> null
    }

    private fun navigation(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fmLayout, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    companion object {
        const val HOME_FRAGMENT = 0
        const val USERS_FRAGMENT = 1
        const val PHOTOS_FRAGMENT = 2
    }
}
