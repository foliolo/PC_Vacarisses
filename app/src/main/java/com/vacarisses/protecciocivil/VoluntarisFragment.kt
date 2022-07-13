package com.vacarisses.protecciocivil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.*
import com.vacarisses.protecciocivil.databinding.FragmentVoluntarisBinding

class VoluntarisFragment : Fragment() {

    private lateinit var binding: FragmentVoluntarisBinding

    private val database = FirebaseFirestore.getInstance()
    private lateinit var myAdapter: AdapterVoluntari

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        FragmentVoluntarisBinding.inflate(inflater).apply {
            binding = this
            return this.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter = AdapterVoluntari()
        binding.voluntariReciclador.adapter = myAdapter

        loadVoluntariData()
    }

    private fun loadVoluntariData() {

        database.collection("voluntaris").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error != null) {
                    Log.e("Firestore Error:  ", error.message.toString())
                    return
                }

                val voluntaris = arrayListOf<Voluntari>()
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        voluntaris.add(dc.document.toObject(Voluntari::class.java))
                    }
                }

                myAdapter.updateElements(voluntaris)

            }
        })
    }
}
