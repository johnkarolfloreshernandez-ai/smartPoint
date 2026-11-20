package com.example.smartpoint.Modelo

import com.example.smartpoint.Red.RetrofitCliente

class clsCategoriasModelo {
    suspend fun obtenerCategorias(): List<dataCategoria> {
        return RetrofitCliente.apiService.obtenerCategorias()
    }
}