package com.partos.flashback.fragments.flashcard


import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.MyApp
import com.partos.flashback.R
import com.partos.flashback.db.DataBaseHelper
import com.partos.flashback.fragments.account.SettingsFragment
import com.partos.flashback.fragments.reviews.ClassicReviewFragment
import com.partos.flashback.fragments.reviews.HardWordsReviewFragment
import com.partos.flashback.fragments.reviews.LearnNewWordsFragment
import com.partos.flashback.models.MyFlashcard
import com.partos.flashback.recycler.flashcard.FlashcardRecyclerViewAdapter
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
class MyFlashcardsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var packageId: Long? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var addFlashcardButton: LinearLayout
    private lateinit var classicReviewButton: Button
    private lateinit var hardWordsButton: Button
    private lateinit var newWordsButton: Button
    private lateinit var settingsButton: ImageView

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
        rootView = inflater.inflate(R.layout.fragment_my_flashcards, container, false);
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
            MyFlashcardsFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, packageId)
                }
            }
    }

    private fun initFragment() {
        addFlashcardButton = rootView.findViewById(R.id.my_flashcards_linear_layout_add_new)
        classicReviewButton = rootView.findViewById(R.id.my_flashcards_button_classic_review)
        hardWordsButton = rootView.findViewById(R.id.my_flashcards_button_hard_words)
        newWordsButton = rootView.findViewById(R.id.my_flashcards_button_learn_new)
        settingsButton = rootView.findViewById(R.id.my_flashcards_button_settings)

        val db = DataBaseHelper(rootView.context)
        val flashcardList = db.getFlashcardsList(packageId as Long, MyApp.userId)

        recyclerView = rootView.findViewById(R.id.my_flashcards_recycler_view)

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                12
            )
        )

        recyclerView.adapter =
            FlashcardRecyclerViewAdapter(
                flashcardList
            )

        addFlashcardButton.setOnClickListener {
            val choseAddFragment = ChoseAddFragment.newInstance(packageId as Long)
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, choseAddFragment)
                ?.addToBackStack(ChoseAddFragment.toString())
                ?.commit()
        }

        classicReviewButton.setOnClickListener {
            val flashcards = ArrayList<MyFlashcard>()
            for (flashcard in flashcardList) {
                if (flashcard.isKnown == 1) {
                    flashcards.add(flashcard)
                }
            }
            if (flashcards.size != 0) {
                val classicReviewFragment = ClassicReviewFragment.newInstance(packageId as Long)
                fragmentManager
                    ?.beginTransaction()
                    ?.setCustomAnimations(
                        R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                        R.anim.enter_left_to_right, R.anim.exit_right_to_left
                    )
                    ?.replace(R.id.main_frame_layout, classicReviewFragment)
                    ?.addToBackStack(ClassicReviewFragment.toString())
                    ?.commit()
            } else {
                Toast.makeText(
                    rootView.context,
                    R.string.toast_too_few_flashcards,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        hardWordsButton.setOnClickListener {
            val flashcards = ArrayList<MyFlashcard>()
            for (flashcard in flashcardList) {
                if (flashcard.knowledgeLevel <= 4 && flashcard.isKnown == 1 && flashcard.isNew == 0) {
                    flashcards.add(flashcard)
                }
            }
            if (flashcards.size != 0) {
                val hardWordsReviewFragment = HardWordsReviewFragment.newInstance(packageId as Long)
                fragmentManager
                    ?.beginTransaction()
                    ?.setCustomAnimations(
                        R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                        R.anim.enter_left_to_right, R.anim.exit_right_to_left
                    )
                    ?.replace(R.id.main_frame_layout, hardWordsReviewFragment)
                    ?.addToBackStack(HardWordsReviewFragment.toString())
                    ?.commit()
            } else {
                Toast.makeText(
                    rootView.context,
                    R.string.toast_too_few_hard_flashcards,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        newWordsButton.setOnClickListener {
            val flashcards = ArrayList<MyFlashcard>()
            for (flashcard in flashcardList) {
                if (flashcard.isKnown == 0) {
                    flashcards.add(flashcard)
                }
            }
            if (flashcards.size != 0) {
                val learnNewWordsFragment = LearnNewWordsFragment.newInstance(packageId as Long)
                fragmentManager
                    ?.beginTransaction()
                    ?.setCustomAnimations(
                        R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                        R.anim.enter_left_to_right, R.anim.exit_right_to_left
                    )
                    ?.replace(R.id.main_frame_layout, learnNewWordsFragment)
                    ?.addToBackStack(LearnNewWordsFragment.toString())
                    ?.commit()
            } else {
                Toast.makeText(
                    rootView.context,
                    R.string.toast_no_words_to_learn,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        settingsButton.setOnClickListener {
            val settingsFragment = SettingsFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, settingsFragment)
                ?.addToBackStack(SettingsFragment.toString())
                ?.commit()
        }
    }
}