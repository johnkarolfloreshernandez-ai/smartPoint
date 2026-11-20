package com.example.smartpoint.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartpoint.Red.RetrofitCliente
import com.example.smartpoint.Modelo.dataCatalogoProductos
import com.example.smartpoint.R
import com.example.smartpoint.Vista.MainActivity

class catalogoProducto(
    private val activity: MainActivity,
    private var listaCatalogoProductos: List<dataCatalogoProductos>
) : RecyclerView.Adapter<catalogoProducto.ProductosViewHolder>() {

    class ProductosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val tvNombreProducto: TextView = itemView.findViewById(R.id.txtNombreProducto)
        val btnVerDetalle: Button = itemView.findViewById(R.id.btnDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val producto = listaCatalogoProductos[position]

        holder.tvNombreProducto.text = producto.nombre

        val urlImagen = RetrofitCliente.BASE_URL + producto.urlImagenPrincipal

        Glide.with(holder.itemView.context)
            .load(urlImagen)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imagenProducto)

        holder.btnVerDetalle.setOnClickListener {
            activity.presenter.seleccionarProducto(producto)
        }
    }

    override fun getItemCount(): Int {
        return listaCatalogoProductos.size
    }

    fun actualizarLista(nuevaLista: List<dataCatalogoProductos>) {
        listaCatalogoProductos = nuevaLista
        notifyDataSetChanged()
    }
}