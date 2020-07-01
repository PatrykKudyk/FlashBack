package com.partos.flashback.fragments.flashcard


import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.R
import com.partos.flashback.models.MyFlashcard
import com.partos.flashback.recycler.flashcard.AssignFlashcardRecyclerViewAdapter
import com.partos.flashback.recycler.MarginItemDecoration


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
class AssignFlashcardsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView

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
        rootView = inflater.inflate(R.layout.fragment_assign_flashcards, container, false);
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
            AssignFlashcardsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        recyclerView = rootView.findViewById(R.id.assign_flashcards_recycler_view)

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                12
            )
        )
        val flashcardList = ArrayList<MyFlashcard>()
        flashcardList.add(MyFlashcard(0, 0, 0, "cześć", "hi", 10, false, true))
        flashcardList.add(MyFlashcard(0, 0, 0, "ty", "you", 10, false, true))
        flashcardList.add(MyFlashcard(0, 0, 0, "ja", "I", 0, false, false))
        flashcardList.add(MyFlashcard(0, 0, 0, "stół", "table", 0, false, false))
        flashcardList.add(MyFlashcard(0, 0, 0, "głośnik", "speaker", 0, false, false))


        recyclerView.adapter =
            AssignFlashcardRecyclerViewAdapter(
                flashcardList
            )

    }
}