package com.partos.flashback.fragments.packages


import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.MyApp
import com.partos.flashback.R
import com.partos.flashback.db.DataBaseHelper
import com.partos.flashback.models.MyPackage
import com.partos.flashback.recycler.packages.AssignPackageRecyclerViewAdapter
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
class AssignPackagesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var flashcardId: Long? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var addPackageButton: LinearLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flashcardId = it.getLong(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_assign_packages, container, false);
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
        fun newInstance(flashcardId: Long) =
            AssignPackagesFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, flashcardId)
                }
            }
    }

    private fun initFragment() {
        addPackageButton = rootView.findViewById(R.id.assign_package_linear_layout_add_new)

        val db = DataBaseHelper(rootView.context)
        val packagesList = db.getPackagesList(MyApp.userId)

        recyclerView = rootView.findViewById(R.id.assign_packages_recycler_view)

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                12
            )
        )


        recyclerView.adapter =
            AssignPackageRecyclerViewAdapter(
                packagesList,
                db.getFlashcard(flashcardId as Long)
            )

        addPackageButton.setOnClickListener {
            val addPackageFragment = AddPackageFragment.newInstance(MyApp.userId)
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right,
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left
                )
                ?.replace(R.id.main_frame_layout, addPackageFragment)
                ?.addToBackStack(AddPackageFragment.toString())
                ?.commit()
        }

    }
}