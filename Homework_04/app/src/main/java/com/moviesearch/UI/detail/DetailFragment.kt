package com.moviesearch.UI.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.moviesearch.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val NAME = "name"
private const val DESC = "description"
private const val PICT = "pictures"
private const val POSTER = "poster"

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : androidx.fragment.app.Fragment() {
    private var name: String? = null
    private var description: String? = null
    //private var pictures: String? = null
    private var poster: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getActivity()?.actionBar?.hide()
        arguments?.let {
            name = it.getString(NAME)
            description = it.getString(DESC)
            //pictures = it.getString(PICT)
            poster = it.getString(POSTER)

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_, container, false)
 //       val view = inflater.inflate(R.layout.fragment_detail, container, false)
 //       view.findViewById<ImageView>(R.id.imageDetail).setImageResource(resources.getIdentifier(pictures,"drawable", view.context.packageName))
//        view.findViewById<TextView>(R.id.textNameDetail).text = name.toString()
//        view.findViewById<TextView>(R.id.text_short_description_detail).text = description.toString()

        view.findViewById<ImageView>(R.id.main_backdrop).setImageResource(resources.
                getIdentifier(poster, "drawable", view.context.packageName))
        view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar).title = name.toString()


        // Inflate the layout for this fragment
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Bundle) =
            DetailFragment().apply {arguments = param1}
        }
    }
