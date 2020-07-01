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
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.partos.flashback.R
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
class LearnNewWordsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var questionCardView: CardView
    private lateinit var answerCardView: CardView
    private lateinit var questionTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var answerTextView: TextView
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
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_learn_new_words, container, false);
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
            LearnNewWordsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        val flashcardList = ArrayList<MyFlashcard>()
        flashcardList.add(MyFlashcard(0, 0, 0, "cześć", "hi", 10, false, true))
        flashcardList.add(MyFlashcard(0, 0, 0, "ty", "you", 10, false, true))
        flashcardList.add(MyFlashcard(0, 0, 0, "tak", "yes", 3, false, true))
        flashcardList.add(MyFlashcard(0, 0, 0, "nie", "no", 2, false, true))
        flashcardList.add(MyFlashcard(0, 0, 0, "może", "maybe", 5, false, true))
        flashcardList.add(MyFlashcard(0, 0, 0, "ja", "I", 0, false, false))
        flashcardList.add(MyFlashcard(0, 0, 0, "stół", "table", 0, false, false))
        flashcardList.add(MyFlashcard(0, 0, 0, "głośnik", "speaker", 0, false, false))
        flashcardList.add(
            MyFlashcard(
                0,
                0,
                0,
                "sklejasz akcje",
                "you know what I'm sayin'",
                8,
                false,
                true
            )
        )

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(audioAttributes)
            .build()

        val soundCorrect = soundPool.load(rootView.context, R.raw.correct, 1)
        val soundIncorrect = soundPool.load(rootView.context, R.raw.incorrect, 1)

        var correct = 0
        var skipped = 0

        questionCardView = rootView.findViewById(R.id.new_words_review_card_view_question)
        answerCardView = rootView.findViewById(R.id.new_words_review_card_view_answer)
        questionTextView = rootView.findViewById(R.id.new_words_review_text_view_question)
        answerEditText = rootView.findViewById(R.id.new_words_review_edit_text_answer)
        checkButton = rootView.findViewById(R.id.new_words_review_button_check)
        quitButton = rootView.findViewById(R.id.new_words_review_button_quit)
        imageView = rootView.findViewById(R.id.new_words_review_image_view)
        normalLinearLayout = rootView.findViewById(R.id.new_words_review_linear_layout_normal)
        checkLinearLayout = rootView.findViewById(R.id.new_words_review_linear_layout_check)
        nextButton = rootView.findViewById(R.id.new_words_review_button_next)
        quitButton2 = rootView.findViewById(R.id.new_words_review_button_exit)
        correctAnswerTextView = rootView.findViewById(R.id.new_words_review_text_view_correct_answer)
        answerTextView = rootView.findViewById(R.id.new_words_review_edit_text_answer_answer)

        var flashcards = ArrayList<MyFlashcard>()
        var number = 0
        for (flashcard in flashcardList) {
            if (flashcard.isNew && number < 4) {
                flashcards.add(flashcard)
                number++
            }
        }
        flashcards.shuffle()
        var afterNew = ArrayList<MyFlashcard>()
        for (i in 0..3) {
            afterNew.addAll(flashcards)
        }
        afterNew.shuffle()
        flashcards.addAll(afterNew)
        var random = Random.nextInt(0, 1000)
        var position = 0
        questionTextView.setText(flashcards[0].english)
        answerTextView.setText(flashcards[0].polish)


        checkButton.setOnClickListener {
            if (random <= 500) {
                if (answerEditText.text.toString()
                        .toLowerCase() == flashcards[position].polish.toLowerCase()
                ) {
                    setCorrect(soundCorrect)
                } else {
                    setIncorrect(soundIncorrect)
                    correctAnswerTextView.setText(flashcards[position].polish)
                }
            } else {
                if (answerEditText.text.toString()
                        .toLowerCase() == flashcards[position].english.toLowerCase()
                ) {
                    setCorrect(soundCorrect)
                } else {
                    setIncorrect(soundIncorrect)
                    correctAnswerTextView.setText(flashcards[position].english)
                }
            }
        }

        nextButton.setOnClickListener {
           if (position <= number) {
               position++
               questionTextView.setText(flashcards[position].english)
               answerTextView.setText(flashcards[position].polish)
           } else {
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
                   val learnedSummaryFragment =
                       LearnedSummaryFragment.newInstance()
                   fragmentManager
                       ?.beginTransaction()
                       ?.setCustomAnimations(
                           R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                           R.anim.enter_left_to_right, R.anim.exit_right_to_left
                       )
                       ?.replace(R.id.main_frame_layout, learnedSummaryFragment)
                       ?.addToBackStack(LearnedSummaryFragment.toString())
                       ?.commit()
               }
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

    }

    private fun setCorrect(sound: Int) {
        imageView.setImageResource(R.drawable.ic_correct)
        imageView.setBackgroundResource(R.drawable.button_background_delete_yes)
        checkLinearLayout.visibility = View.VISIBLE
        normalLinearLayout.visibility = View.GONE
        soundPool.play(sound, 1F, 1F, 0, 0, 1F)
    }

    private fun setIncorrect(sound: Int) {
        imageView.setImageResource(R.drawable.ic_incorrect)
        imageView.setBackgroundResource(R.drawable.button_background_delete_no)
        correctAnswerTextView.visibility = View.VISIBLE
        checkLinearLayout.visibility = View.VISIBLE
        normalLinearLayout.visibility = View.GONE
        soundPool.play(sound, 1F, 1F, 0, 0, 1F)
    }

    private fun setEmpty() {
        imageView.setImageDrawable(null)
        imageView.background = null
        correctAnswerTextView.visibility = View.INVISIBLE
        answerEditText.setText("")
        checkLinearLayout.visibility = View.GONE
        normalLinearLayout.visibility = View.VISIBLE
    }
}