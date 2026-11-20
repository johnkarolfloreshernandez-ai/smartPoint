package com.example.smartpoint.Modelo

import com.example.smartpoint.Red.RetrofitCliente

class clsCarritoModelo {
    suspend fun obtenerCarrito(usuarioId: Int): List<dataCarritoItem> {
        return RetrofitCliente.apiService.obtenerCarrito(usuarioId)
    }

    suspend fun eliminarItemCarrito(idCarrito: Int): RespuestaGeneral {
        return RetrofitCliente.apiService.eliminarDelCarrito(idCarrito)
    }

    suspend fun finalizarCompra(usuarioId: Int): RespuestaGeneral {
        return RetrofitCliente.apiService.finalizarCompra(VentaRequest(usuarioId))
    }
}