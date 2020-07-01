package com.partos.flashback.fragments.menu


import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.partos.flashback.R
import com.partos.flashback.fragments.flashcard.AssignFlashcardsFragment
import com.partos.flashback.fragments.packages.AssignPackagesFragment
import com.partos.flashback.fragments.packages.MyPackagesFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoggedMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var myPackagesButton: Button
    private lateinit var newWordsButton: Button
    private lateinit var creditsButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_logged_menu, container, false);
        initFragment()
        return rootView
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoggedMenuFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        myPackagesButton = rootView.findViewById(R.id.logged_menu_button_packages)
        newWordsButton = rootView.findViewById(R.id.logged_menu_button_new_words)
        creditsButton = rootView.findViewById(R.id.logged_menu_button_credits)
        logoutButton = rootView.findViewById(R.id.logged_menu_button_logout)

        myPackagesButton.setOnClickListener {
            val myPackageFragment =
                MyPackagesFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, myPackageFragment)
                ?.addToBackStack(MyPackagesFragment.toString())
                ?.commit()
        }

        newWordsButton.setOnClickListener {
            val assignFlashcardsFragment = AssignFlashcardsFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, assignFlashcardsFragment)
                ?.addToBackStack(AssignFlashcardsFragment.toString())
                ?.commit()
        }

        creditsButton.setOnClickListener {
            val creditsFragment =
                CreditsFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom,
                    R.anim.enter_top_to_bottom, R.anim.exit_bottom_to_top
                )
                ?.replace(R.id.main_frame_layout, creditsFragment)
                ?.addToBackStack(CreditsFragment.toString())
                ?.commit()
        }

        logoutButton.setOnClickListener {
            val menuFragment =
                MainMenuFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, menuFragment)
                ?.commit()
        }
    }
}