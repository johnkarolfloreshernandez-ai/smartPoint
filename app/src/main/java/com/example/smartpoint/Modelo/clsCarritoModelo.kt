package com.example.smartpoint.Modelo

class clsCarritoModelo {
    suspend fun obtenerCarrito(usuarioId: Int): List<dataCarritoItem> {
        return RetrofitCliente.apiService.obtenerCarrito(usuarioId)
    }

}