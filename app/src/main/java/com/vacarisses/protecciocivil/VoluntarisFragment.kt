package com.vacarisses.protecciocivil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
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

        database.collection("voluntaris").get()
            .addOnSuccessListener { result ->
                val voluntaris = arrayListOf<Voluntari>()
                for (document in result) {
                    Log.d("VoluntarisFragment", "${document.id} => ${document.data}")
                    voluntaris.add(document.toObject(Voluntari::class.java))
                }
                myAdapter.updateElements(voluntaris)
            }
            .addOnFailureListener { error ->
                Log.e("Firestore Error:  ", error.message.toString())
            }
    }
}
