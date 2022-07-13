package com.vacarisses.protecciocivil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vacarisses.protecciocivil.databinding.VoluntariCardviewBinding

class AdapterVoluntari(private val list: ArrayList<Voluntari> = arrayListOf()) :
    RecyclerView.Adapter<AdapterVoluntari.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(VoluntariCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateElements(updatedList: ArrayList<Voluntari>) {
        list.apply {
            clear()
            addAll(updatedList)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: VoluntariCardviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(data: Voluntari) = with(binding) {
//            voluntaritextview.text = data.indicatiu
            carrecvoluntari.text = data.Carrec
            nomvoluntaritextview.text = data.Nom
            mailvoluntari.text = data.Mail
            telefonvoluntari.text = data.Movil
//            estatTextview.text = data.estat
        }
    }
}