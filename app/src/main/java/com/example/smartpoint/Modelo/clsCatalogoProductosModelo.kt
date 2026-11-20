package com.example.smartpoint.Modelo

import com.example.smartpoint.Red.RetrofitCliente

class clsCatalogoProductosModelo {

        suspend fun obtenerProductos(idCategoria: Int, buscar: String = ""): List<dataCatalogoProductos> {
            return RetrofitCliente.apiService.obtenerProductos(idCategoria, buscar)
        }
}