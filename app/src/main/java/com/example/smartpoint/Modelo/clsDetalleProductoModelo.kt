package com.example.smartpoint.Modelo

import com.example.smartpoint.Red.RetrofitCliente

class clsDetalleProductoModelo {
    suspend fun obtenerGaleria(productoId: Int): List<dataImagenProducto> {
        return RetrofitCliente.apiService.obtenerGaleriaProducto(productoId)
    }

    suspend fun agregarAlCarrito(usuarioId: Int, productoId: Int, cantidad: Int = 1): RespuestaGeneral {
        val request = CarritoRequest(
            usuarioId = usuarioId,
            productoId = productoId,
            cantidad = cantidad
        )
        return RetrofitCliente.apiService.agregarAlCarrito(request)
    }
}