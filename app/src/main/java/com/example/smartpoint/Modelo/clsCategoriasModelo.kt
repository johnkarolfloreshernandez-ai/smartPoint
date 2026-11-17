package com.example.smartpoint.Modelo

class clsCategoriasModelo {
    suspend fun obtenerCategorias(): List<dataCategoria> {
        return RetrofitCliente.apiService.obtenerCategorias()
    }
}