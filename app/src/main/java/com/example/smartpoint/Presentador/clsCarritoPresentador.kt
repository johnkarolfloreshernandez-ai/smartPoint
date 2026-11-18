package com.example.smartpoint.Presentador

import com.example.smartpoint.Modelo.clsCarritoModelo
import com.example.smartpoint.Vista.CarritoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCarritoPresentador(private val vista: CarritoActivity) {

    private val modelo = clsCarritoModelo()

    fun cargarCarrito(usuarioId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val items = modelo.obtenerCarrito(usuarioId)
                vista.mostrarProductosCarrito(items)

                val total = items.sumOf { it.precio * it.cantidad }
                vista.mostrarTotales(subtotal = total, totalFinal = total)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar carrito: " + e.localizedMessage)
            }
        }
    }
}