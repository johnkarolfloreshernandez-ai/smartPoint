package com.example.smartpoint.Modelo

class clsCatalogoProductosModelo {

        suspend fun obtenerProductos(idCategoria: Int, buscar: String = ""): List<dataCatalogoProductos> {
            return RetrofitCliente.apiService.obtenerProductos(idCategoria, buscar)
        }
}