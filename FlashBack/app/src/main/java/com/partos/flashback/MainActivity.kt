package com.partos.flashback

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.partos.flashback.fragments.*

class MainActivity : AppCompatActivity(),
    MainMenuFragment.OnFragmentInteractionListener,
    CreditsFragment.OnFragmentInteractionListener,
    LogInFragment.OnFragmentInteractionListener,
    RegisterFragment.OnFragmentInteractionListener,
    LoggedMenuFragment.OnFragmentInteractionListener,
    MyPackagesFragment.OnFragmentInteractionListener,
    AddPackageFragment.OnFragmentInteractionListener,
    MyFlashcardsFragment.OnFragmentInteractionListener {

    private lateinit var mainMenuFragment: MainMenuFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainMenuFragment = MainMenuFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom,
                R.anim.enter_top_to_bottom, R.anim.exit_bottom_to_top
            )
            .add(R.id.main_frame_layout, mainMenuFragment)
            .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}