package com.partos.flashback.fragments.account


import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.partos.flashback.MyApp
import com.partos.flashback.R
import com.partos.flashback.db.DataBaseHelper
import com.partos.flashback.models.Settings


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
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var review10: CardView
    private lateinit var review25: CardView
    private lateinit var review50: CardView
    private lateinit var reviewInf: CardView
    private lateinit var hard5: CardView
    private lateinit var hard15: CardView
    private lateinit var hard25: CardView
    private lateinit var hardInf: CardView
    private lateinit var learn3: CardView
    private lateinit var learn5: CardView
    private lateinit var learn7: CardView
    private lateinit var learn10: CardView


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
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
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
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        attachViews()
        setCards()
        setListeners()
    }

    private fun setListeners() {
        val db = DataBaseHelper(rootView.context)
        val settings = db.getSettings(MyApp.userId)[0]
        review10.setOnClickListener {
            resetCardsReview()
            review10.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.reviewClassicAmount = 10
            db.updateSettings(settings)
        }
        review25.setOnClickListener {
            resetCardsReview()
            review25.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.reviewClassicAmount = 25
            db.updateSettings(settings)
        }
        review50.setOnClickListener {
            resetCardsReview()
            review50.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.reviewClassicAmount = 50
            db.updateSettings(settings)
        }
        reviewInf.setOnClickListener {
            resetCardsReview()
            reviewInf.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.reviewClassicAmount = -1
            db.updateSettings(settings)
        }
        hard5.setOnClickListener {
            resetCardsHard()
            hard5.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.reviewHardAmount = 5
            db.updateSettings(settings)
        }
        hard15.setOnClickListener {
            resetCardsHard()
            hard15.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.reviewHardAmount = 15
            db.updateSettings(settings)
        }
        hard25.setOnClickListener {
            resetCardsHard()
            hard25.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.reviewHardAmount = 25
            db.updateSettings(settings)
        }
        hardInf.setOnClickListener {
            resetCardsHard()
            hardInf.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.reviewHardAmount = -1
            db.updateSettings(settings)
        }
        learn3.setOnClickListener {
            resetCardsLearn()
            learn3.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.learningAmount = 3
            db.updateSettings(settings)
        }
        learn5.setOnClickListener {
            resetCardsLearn()
            learn5.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.learningAmount = 5
            db.updateSettings(settings)
        }
        learn7.setOnClickListener {
            resetCardsLearn()
            learn7.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.learningAmount = 7
            db.updateSettings(settings)
        }
        learn10.setOnClickListener {
            resetCardsLearn()
            learn10.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            settings.learningAmount = 10
            db.updateSettings(settings)
        }
    }

    private fun resetCardsReview() {
        review10.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        review25.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        review50.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        reviewInf.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
    }

    private fun resetCardsHard() {
        hard5.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        hard15.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        hard25.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        hardInf.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
    }

    private fun resetCardsLearn() {
        learn3.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        learn5.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        learn7.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
        learn10.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorBackground))
    }

    private fun setCards() {
        val db = DataBaseHelper(rootView.context)
        val settings = db.getSettings(MyApp.userId)[0]
        when (settings.reviewClassicAmount) {
            10 -> review10.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            25 -> review25.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            50 -> review50.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            -1 -> reviewInf.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
        }
        when (settings.reviewHardAmount) {
            5 -> hard5.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            15 -> hard15.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            25 -> hard25.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            -1 -> hardInf.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
        }
        when (settings.learningAmount) {
            3 -> learn3.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            5 -> learn5.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            7 -> learn7.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
            10 -> learn10.setCardBackgroundColor(rootView.context.getColorStateList(R.color.colorMainMediumLight))
        }
    }

    private fun attachViews() {
        review10 = rootView.findViewById(R.id.settings_review_10)
        review25 = rootView.findViewById(R.id.settings_review_25)
        review50 = rootView.findViewById(R.id.settings_review_50)
        reviewInf = rootView.findViewById(R.id.settings_review_infinite)
        hard5 = rootView.findViewById(R.id.settings_hard_5)
        hard15 = rootView.findViewById(R.id.settings_hard_15)
        hard25 = rootView.findViewById(R.id.settings_hard_25)
        hardInf = rootView.findViewById(R.id.settings_hard_infinite)
        learn3 = rootView.findViewById(R.id.settings_learn_3)
        learn5 = rootView.findViewById(R.id.settings_learn_5)
        learn7 = rootView.findViewById(R.id.settings_learn_7)
        learn10 = rootView.findViewById(R.id.settings_learn_10)
    }
}