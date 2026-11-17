package com.example.smartpoint.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartpoint.Modelo.RetrofitCliente
import com.example.smartpoint.R

class GaleriaProductoAdapter (
    private var listaUrls: List<String>
) : RecyclerView.Adapter<GaleriaProductoAdapter.GaleriaViewHolder>() {

    class GaleriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCarrusel: ImageView = itemView.findViewById(R.id.imgCarruselProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_galeria_producto, parent, false)
        return GaleriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: GaleriaViewHolder, position: Int) {
        val urlCompleta = RetrofitCliente.BASE_URL + listaUrls[position]

        Glide.with(holder.itemView.context)
            .load(urlCompleta)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgCarrusel)
    }

    override fun getItemCount(): Int = listaUrls.size

    fun actualizarImagenes(nuevasUrls: List<String>) {
        listaUrls = nuevasUrls
        notifyDataSetChanged()
    }
}