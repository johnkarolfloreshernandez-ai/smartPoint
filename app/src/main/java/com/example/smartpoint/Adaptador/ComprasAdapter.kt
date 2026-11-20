package com.example.smartpoint.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpoint.Modelo.dataVentaHistorial
import com.example.smartpoint.R
import java.text.NumberFormat
import java.util.Locale

class ComprasAdapter(
    private var listaCompras: List<dataVentaHistorial>
) : RecyclerView.Adapter<ComprasAdapter.CompraViewHolder>() {

    class CompraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtFolio: TextView = itemView.findViewById(R.id.txtFolioVenta)
        val txtFecha: TextView = itemView.findViewById(R.id.txtFechaVenta)
        val txtProductos: TextView = itemView.findViewById(R.id.txtProductosVenta)
        val txtTotal: TextView = itemView.findViewById(R.id.txtTotalVenta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_compra_perfil, parent, false)
        return CompraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val compra = listaCompras[position]
        val formatoMoneda = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

        holder.txtFolio.text = "Compra #${compra.idVenta} • ${compra.estado.uppercase()}"
        holder.txtFecha.text = compra.fechaVenta
        holder.txtProductos.text = compra.resumenProductos
        holder.txtTotal.text = "Total: MXN$${formatoMoneda.format(compra.total)}"
    }

    override fun getItemCount(): Int = listaCompras.size

    fun actualizarLista(nuevaLista: List<dataVentaHistorial>) {
        listaCompras = nuevaLista
        notifyDataSetChanged()
    }
}