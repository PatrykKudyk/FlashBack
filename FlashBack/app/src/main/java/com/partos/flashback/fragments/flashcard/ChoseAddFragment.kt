package com.partos.flashback.fragments.flashcard


import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.partos.flashback.R


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
class ChoseAddFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var packageId: Long? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var manuallyButton: Button
    private lateinit var automaticallyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            packageId = it.getLong(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_chose_add, container, false);
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
        fun newInstance(packageId: Long) =
            ChoseAddFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, packageId)
                }
            }
    }

    private fun initFragment() {
        manuallyButton = rootView.findViewById(R.id.add_choice_button_manually)
        automaticallyButton = rootView.findViewById(R.id.add_choice_button_automatically)

        manuallyButton.setOnClickListener {
            val addFlashcardFragment = AddFlashcardManuallyFragment.newInstance(packageId as Long)
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, addFlashcardFragment)
                ?.addToBackStack(AddFlashcardManuallyFragment.toString())
                ?.commit()
        }

        automaticallyButton.setOnClickListener {
            val addFlashcardFragment = AddFlashcardAutomaticallyFragment.newInstance(packageId as Long)
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, addFlashcardFragment)
                ?.addToBackStack(AddFlashcardAutomaticallyFragment.toString())
                ?.commit()
        }
    }
}