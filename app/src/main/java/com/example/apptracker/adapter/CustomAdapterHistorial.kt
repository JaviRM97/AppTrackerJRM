package com.example.apptracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apptracker.model.Historial
import kotlinx.android.synthetic.main.row_historial.view.*
import org.jetbrains.anko.image


class CustomAdapterHistorial(val context: Context, val layout: Int, val dataList: List<Historial>): RecyclerView.Adapter<CustomAdapterHistorial.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Historial){
            itemView.tvNombre.text = dataItem.nombre
            itemView.tvJuego.text = dataItem.juego
            //itemView.tvTracker.text = dataItem.tracker
            itemView.tag = dataItem
        }
    }
}
