package com.example.smartpoint.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartpoint.Red.RetrofitCliente
import com.example.smartpoint.Modelo.dataCarritoItem
import com.example.smartpoint.R
import java.text.NumberFormat
import java.util.Locale

class CarritoAdapter (
    private var listaCarrito: List<dataCarritoItem>,
    private val onEliminarClick: (dataCarritoItem) -> Unit) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProductoCarrito)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreProductoCarrito)
        val txtCantidad: TextView = itemView.findViewById(R.id.txtCantidadProductoCarrito)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecioProductoCarrito)
        val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminarItemCarrito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = listaCarrito[position]

        val formatoMoneda = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

        holder.txtNombre.text = item.nombre
        holder.txtCantidad.text = "Cantidad: ${item.cantidad}"
        val subtotalItem = item.precio * item.cantidad
        holder.txtPrecio.text = "MXN$${formatoMoneda.format(subtotalItem)}"

        val urlImagen = RetrofitCliente.BASE_URL + item.urlImagenPrincipal

        Glide.with(holder.itemView.context)
            .load(urlImagen)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgProducto)

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(item)
        }
    }

    override fun getItemCount(): Int = listaCarrito.size

    fun actualizarLista(nuevaLista: List<dataCarritoItem>) {
        listaCarrito = nuevaLista
        notifyDataSetChanged()
    }
}