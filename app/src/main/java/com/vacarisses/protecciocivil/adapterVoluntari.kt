package com.vacarisses.protecciocivil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterVoluntari(var list: ArrayList<voluntari>): RecyclerView.Adapter<adapterVoluntari.viewHolder>(){

    class viewHolder(view:View): RecyclerView.ViewHolder(view){

        fun bindItems(data: voluntari){
            val indicatiu:TextView=itemView.findViewById(R.id.voluntaritextview)
            val carrec:TextView=itemView.findViewById(R.id.carrecvoluntari)
            val nom:TextView=itemView.findViewById(R.id.nomvoluntaritextview)
            val mail:TextView=itemView.findViewById(R.id.mailvoluntari)
            val telefon:TextView=itemView.findViewById(R.id.telefonvoluntari)
            val estat:TextView=itemView.findViewById(R.id.estatTextview)

            indicatiu.text=data.indicatiu
            carrec.text=data.carrec
            nom.text=data.nom
            mail.text=data.mail
            telefon.text=data.telefon
            estat.text=data.estat

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.voluntari_cardview,parent, false)

        return viewHolder(v)
    }

    override fun onBindViewHolder(holder: adapterVoluntari.viewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}