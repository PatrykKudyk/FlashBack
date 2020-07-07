package com.partos.flashback.fragments.flashcard

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.R
import com.partos.flashback.recycler.flashcard.AddFlashcardRecyclerViewAdapter


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
class AddFlashcardAutomaticallyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Long? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var addButton: Button
    private lateinit var flashcardEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getLong(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView =
            inflater.inflate(R.layout.fragment_add_flashcard_automatically, container, false);
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
            AddFlashcardAutomaticallyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        addButton = rootView.findViewById(R.id.add_flashcard_automatically_button_add)
        flashcardEditText =
            rootView.findViewById(R.id.add_flashcard_automatically_english_edit_text)

        addButton.setOnClickListener {
            if (flashcardEditText.text.toString() != "") {
                fragmentManager
                    ?.popBackStack(
                        fragmentManager!!.getBackStackEntryAt((fragmentManager!!.backStackEntryCount - 2)).id,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_flashcard_not_null_2),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}