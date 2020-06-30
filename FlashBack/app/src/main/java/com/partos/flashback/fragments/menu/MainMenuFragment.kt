package com.partos.flashback.fragments.menu


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.partos.flashback.R
import com.partos.flashback.fragments.account.LogInFragment


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
class MainMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var image: ImageView
    private lateinit var linearLayout: LinearLayout
    private lateinit var loginButton: Button
    private lateinit var creditsButton: Button

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
        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
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
            MainMenuFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        image = rootView.findViewById(R.id.menu_image_view)
        linearLayout = rootView.findViewById(R.id.main_linear_layout)
        loginButton = rootView.findViewById(R.id.menu_button_log_in)
        creditsButton = rootView.findViewById(R.id.menu_button_credits)

        image.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(rootView.context, R.anim.disapear_front)
            image.startAnimation(animation)
            Handler().postDelayed({
                image.visibility = View.GONE
                linearLayout.visibility = View.VISIBLE
                val anim1 = AnimationUtils.loadAnimation(rootView.context, R.anim.enter_left_to_right)
                val anim2 = AnimationUtils.loadAnimation(rootView.context, R.anim.enter_right_to_left)
                loginButton.startAnimation(anim1)
                creditsButton.startAnimation(anim2)
            }, 500)
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

        loginButton.setOnClickListener {
            val logInFragment =
                LogInFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, logInFragment)
                ?.commit()
        }
    }
}