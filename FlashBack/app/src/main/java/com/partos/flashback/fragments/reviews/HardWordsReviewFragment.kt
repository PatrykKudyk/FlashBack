package com.partos.flashback.fragments.reviews

import android.content.Context
import android.media.AudioAttributes
import android.media.Image
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.partos.flashback.MyApp
import com.partos.flashback.R
import com.partos.flashback.db.DataBaseHelper
import com.partos.flashback.models.MyFlashcard
import kotlinx.android.synthetic.main.fragment_classic_review.*
import kotlin.random.Random


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
class HardWordsReviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var packageId: Long? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var questionCardView: CardView
    private lateinit var answerCardView: CardView
    private lateinit var questionTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var checkButton: Button
    private lateinit var skipButton: Button
    private lateinit var quitButton: Button
    private lateinit var imageView: ImageView
    private lateinit var normalLinearLayout: LinearLayout
    private lateinit var checkLinearLayout: LinearLayout
    private lateinit var nextButton: Button
    private lateinit var quitButton2: Button
    private lateinit var soundPool: SoundPool
    private lateinit var correctAnswerTextView: TextView

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
        rootView = inflater.inflate(R.layout.fragment_hard_words_review, container, false);
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
            HardWordsReviewFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, packageId)
                }
            }
    }

    private fun initFragment() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        val soundCorrect = soundPool.load(rootView.context, R.raw.correct, 1)
        val soundIncorrect = soundPool.load(rootView.context, R.raw.incorrect, 1)

        var correct = 0
        var skipped = 0

        questionCardView = rootView.findViewById(R.id.hard_review_card_view_question)
        answerCardView = rootView.findViewById(R.id.hard_review_card_view_answer)
        questionTextView = rootView.findViewById(R.id.hard_review_text_view_question)
        answerEditText = rootView.findViewById(R.id.hard_review_edit_text_answer)
        checkButton = rootView.findViewById(R.id.hard_review_button_check)
        skipButton = rootView.findViewById(R.id.hard_review_button_skip)
        quitButton = rootView.findViewById(R.id.hard_review_button_quit)
        imageView = rootView.findViewById(R.id.hard_review_image_view)
        normalLinearLayout = rootView.findViewById(R.id.hard_review_linear_layout_normal)
        checkLinearLayout = rootView.findViewById(R.id.hard_review_linear_layout_check)
        nextButton = rootView.findViewById(R.id.hard_review_button_next)
        quitButton2 = rootView.findViewById(R.id.hard_review_button_exit)
        correctAnswerTextView = rootView.findViewById(R.id.hard_review_text_view_correct_answer)

        val db = DataBaseHelper(rootView.context)
        val flashcardList = db.getFlashcardsList(packageId as Long, MyApp.userId)
        var flashcards = ArrayList<MyFlashcard>()

        for (flashcard in flashcardList) {
            if (flashcard.knowledgeLevel <= 4 && flashcard.isKnown == 1 && flashcard.isNew == 0) {
                flashcards.add(flashcard)
            }
        }
        flashcards.shuffle()
        var random = Random.nextInt(0, 1000)
        var position = 0
        if (random <= 500) {
            questionTextView.setText(flashcards[0].english)
        } else {
            questionTextView.setText(flashcards[0].polish)
        }

        checkButton.setOnClickListener {
            val answer = answerEditText.text.toString().trim().replace(" ", "").toLowerCase()
            if (random <= 500) {
                if (answer == flashcards[position].polish.replace(" ", "").toLowerCase()) {
                    setCorrect(soundCorrect)
                    correct++
                    flashcards[position].knowledgeLevel += 2
                    if (flashcards[position].knowledgeLevel > 10) {
                        flashcards[position].knowledgeLevel = 10
                    }
                    db.updateFlashcard(flashcards[position])
                } else {
                    flashcards[position].knowledgeLevel -= 2
                    if (flashcards[position].knowledgeLevel < 0) {
                        flashcards[position].knowledgeLevel = 0
                    }
                    db.updateFlashcard(flashcards[position])
                    setIncorrect(soundIncorrect)
                    correctAnswerTextView.setText(flashcards[position].polish)
                }
            } else {
                if (answer == flashcards[position].english.replace(" ", "").toLowerCase()) {
                    setCorrect(soundCorrect)
                    correct++
                    flashcards[position].knowledgeLevel += 2
                    if (flashcards[position].knowledgeLevel > 10) {
                        flashcards[position].knowledgeLevel = 10
                    }
                    db.updateFlashcard(flashcards[position])
                } else {
                    flashcards[position].knowledgeLevel -= 2
                    if (flashcards[position].knowledgeLevel < 0) {
                        flashcards[position].knowledgeLevel = 0
                    }
                    db.updateFlashcard(flashcards[position])
                    setIncorrect(soundIncorrect)
                    correctAnswerTextView.setText(flashcards[position].english)
                }
            }
        }

        nextButton.setOnClickListener {
            if (position < flashcards.size - 1) {
                random = Random.nextInt(0, 1000)
                position++
                if (random <= 500) {
                    questionTextView.setText(flashcards[position].english)
                } else {
                    questionTextView.setText(flashcards[position].polish)
                }
                setEmpty()
            } else {
                soundPool.release()
                val reviewSummaryFragment =
                    ReviewSummaryFragment.newInstance(correct, skipped, flashcards.size)
                fragmentManager
                    ?.beginTransaction()
                    ?.setCustomAnimations(
                        R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                        R.anim.enter_left_to_right, R.anim.exit_right_to_left
                    )
                    ?.replace(R.id.main_frame_layout, reviewSummaryFragment)
                    ?.addToBackStack(ReviewSummaryFragment.toString())
                    ?.commit()
            }
        }

        quitButton.setOnClickListener {
            soundPool.release()
            fragmentManager
                ?.popBackStack()
        }

        quitButton2.setOnClickListener {
            soundPool.release()
            fragmentManager
                ?.popBackStack()
        }

        skipButton.setOnClickListener {
            skipped++
            if (position < flashcards.size - 1) {
                random = Random.nextInt(0, 1000)
                position++
                if (random <= 500) {
                    questionTextView.setText(flashcards[position].english)
                } else {
                    questionTextView.setText(flashcards[position].polish)
                }
                setEmpty()
            } else {
                soundPool.release()
                val reviewSummaryFragment =
                    ReviewSummaryFragment.newInstance(correct, skipped, flashcards.size)
                fragmentManager
                    ?.beginTransaction()
                    ?.setCustomAnimations(
                        R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                        R.anim.enter_left_to_right, R.anim.exit_right_to_left
                    )
                    ?.replace(R.id.main_frame_layout, reviewSummaryFragment)
                    ?.addToBackStack(ReviewSummaryFragment.toString())
                    ?.commit()
            }
        }
    }

    private fun setCorrect(sound: Int) {
        hideKeyboard()
        imageView.setImageResource(R.drawable.ic_correct)
        imageView.setBackgroundResource(R.drawable.button_background_delete_yes)
        checkLinearLayout.visibility = View.VISIBLE
        normalLinearLayout.visibility = View.GONE
        soundPool.play(sound, 1F, 1F, 0, 0, 1F)
    }

    private fun setIncorrect(sound: Int) {
        hideKeyboard()
        imageView.setImageResource(R.drawable.ic_incorrect)
        imageView.setBackgroundResource(R.drawable.button_background_delete_no)
        correctAnswerTextView.visibility = View.VISIBLE
        checkLinearLayout.visibility = View.VISIBLE
        normalLinearLayout.visibility = View.GONE
        soundPool.play(sound, 1F, 1F, 0, 0, 1F)
    }

    private fun setEmpty() {
        hideKeyboard()
        imageView.setImageDrawable(null)
        imageView.background = null
        correctAnswerTextView.visibility = View.INVISIBLE
        answerEditText.setText("")
        checkLinearLayout.visibility = View.GONE
        normalLinearLayout.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val inputManager =
                rootView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}