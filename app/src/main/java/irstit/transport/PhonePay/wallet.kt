package irstit.transport.PhonePay


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import irstit.transport.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var navigation_Recycler: RecyclerView? = null
private var listme: MutableList<TransctionModel> = arrayListOf()
var re : TransctionRecycler? = null

/**
 * A simple [Fragment] subclass.
 *
 */
class wallet : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wallet, container, false)
        navigation_Recycler = view.findViewById(R.id.TransctionRecycler)

        val ob0 =  TransctionModel()
        ob0.checkImage ="image1"
        ob0.price ="500"
        ob0.transctionNumber ="1"

        val ob1 = TransctionModel()
        ob1.checkImage ="image2"
        ob1.price ="10000"
        ob1.transctionNumber ="2"

        listme.add(ob0)
        listme.add(ob1)



        // Inflate the layout for this fragment
        return view
    }


}
