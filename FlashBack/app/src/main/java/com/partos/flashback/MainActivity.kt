package com.partos.flashback

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.partos.flashback.fragments.packages.AddPackageFragment
import com.partos.flashback.fragments.packages.MyPackagesFragment
import com.partos.flashback.fragments.account.LogInFragment
import com.partos.flashback.fragments.account.RegisterFragment
import com.partos.flashback.fragments.flashcard.*
import com.partos.flashback.fragments.menu.CreditsFragment
import com.partos.flashback.fragments.menu.LoggedMenuFragment
import com.partos.flashback.fragments.menu.MainMenuFragment
import com.partos.flashback.fragments.packages.AssignPackagesFragment
import com.partos.flashback.fragments.reviews.*

class MainActivity : AppCompatActivity(),
    MainMenuFragment.OnFragmentInteractionListener,
    CreditsFragment.OnFragmentInteractionListener,
    LogInFragment.OnFragmentInteractionListener,
    RegisterFragment.OnFragmentInteractionListener,
    LoggedMenuFragment.OnFragmentInteractionListener,
    MyPackagesFragment.OnFragmentInteractionListener,
    AddPackageFragment.OnFragmentInteractionListener,
    MyFlashcardsFragment.OnFragmentInteractionListener,
    AddFlashcardManuallyFragment.OnFragmentInteractionListener,
    ClassicReviewFragment.OnFragmentInteractionListener,
    ReviewSummaryFragment.OnFragmentInteractionListener,
    HardWordsReviewFragment.OnFragmentInteractionListener,
    LearnNewWordsFragment.OnFragmentInteractionListener,
    LearnedSummaryFragment.OnFragmentInteractionListener,
    AssignFlashcardsFragment.OnFragmentInteractionListener,
    AssignPackagesFragment.OnFragmentInteractionListener,
    ChoseAddFragment.OnFragmentInteractionListener,
    AddFlashcardAutomaticallyFragment.OnFragmentInteractionListener {

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