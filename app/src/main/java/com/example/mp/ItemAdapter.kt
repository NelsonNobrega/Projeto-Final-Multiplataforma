package com.example.mp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.List

class ItemAdapter(private val items: List<UmidadeTemperatura>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dataHoraTextView: TextView = view.findViewById(R.id.dataHoraTextView)
        val temperaturaTextView: TextView = view.findViewById(R.id.temperaturaTextView)
        val umidadeTextView: TextView = view.findViewById(R.id.umidadeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_umidade_temperatura, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.dataHoraTextView.text = item.dataHora
        holder.temperaturaTextView.text = "Temperatura: ${item.temperatura}°C"
        holder.umidadeTextView.text = "Umidade: ${item.umidade}%"
    }

    override fun getItemCount(): Int = items.size
}
