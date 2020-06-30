package com.partos.flashback.fragments.reviews


import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.partos.flashback.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "correct"
private const val ARG_PARAM2 = "skipped"
private const val ARG_PARAM3 = "all"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewSummaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var correct: Int? = null
    private var skipped: Int? = null
    private var all: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var backButton: Button
    private lateinit var correctTextView: TextView
    private lateinit var skippedTextView: TextView
    private lateinit var soundPool: SoundPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            correct = it.getInt(ARG_PARAM1)
            skipped = it.getInt(ARG_PARAM2)
            all = it.getInt(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_review_summary, container, false);
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
        fun newInstance(correct: Int, skipped: Int, all: Int) =
            ReviewSummaryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, correct)
                    putInt(ARG_PARAM2, skipped)
                    putInt(ARG_PARAM3, all)
                }
            }
    }

    private fun initFragment() {
        backButton = rootView.findViewById(R.id.summary_button_back)
        correctTextView = rootView.findViewById(R.id.summary_text_view_correct)
        skippedTextView = rootView.findViewById(R.id.summary_text_view_skipped)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()
        val soundEnd = soundPool.load(rootView.context, R.raw.done, 1)
        Handler().postDelayed({
            soundPool.play(soundEnd, 1F, 1F, 0, 0, 1F)
        }, 400)
        correctTextView.setText(correct.toString() + "/" + (all as Int - skipped as Int).toString())
        skippedTextView.setText(skipped.toString())

        backButton.setOnClickListener {
            soundPool.release()
            fragmentManager
                ?.popBackStack(
                    fragmentManager!!.getBackStackEntryAt((fragmentManager!!.backStackEntryCount - 2)).id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
        }
    }
}