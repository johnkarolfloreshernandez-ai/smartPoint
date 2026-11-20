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

    fun eliminarProducto(idCarrito: Int, usuarioId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = modelo.eliminarItemCarrito(idCarrito)
                if (res.exito) {
                    vista.mostrarMensaje(res.mensaje)
                    cargarCarrito(usuarioId)
                } else {
                    vista.mostrarError(res.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al eliminar producto: " + e.localizedMessage)
            }
        }
    }

    fun procesarCompra(usuarioId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = modelo.finalizarCompra(usuarioId)
                if (res.exito) {
                    vista.mostrarCompraExitosa(res.mensaje)
                    cargarCarrito(usuarioId)
                } else {
                    vista.mostrarError(res.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al finalizar compra: " + e.localizedMessage)
            }
        }
    }
}