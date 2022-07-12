package com.vacarisses.protecciocivil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.voluntari_cardview.*
import kotlinx.android.synthetic.main.voluntari_cardview.view.*
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [VoluntarisFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VoluntarisFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val database = FirebaseFirestore.getInstance()
    var voluntaris = ArrayList<voluntari>()
    lateinit var myAdapter: adapterVoluntari


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_voluntaris, container, false)


        val recyclerView:RecyclerView=view.findViewById(R.id.voluntariReciclador)
        recyclerView.layoutManager=LinearLayoutManager(activity, RecyclerView.VERTICAL, false)




        myAdapter = adapterVoluntari(voluntaris)

        recyclerView.adapter=myAdapter

        loadVoluntariData()


        return view
    }

     fun loadVoluntariData() {

        database.collection("voluntaris").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null){

                    Log.e("Firestore Error:  ", error.message.toString())
                    return
                }

                for (dc : DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        voluntaris.add(dc.document.toObject(voluntari::class.java))

                    }
                }
                myAdapter.notifyDataSetChanged()
                Log.d("Documento: ","$voluntaris")

            }
        })

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VoluntarisFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VoluntarisFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

