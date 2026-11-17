package com.example.smartpoint.interfaceApi

import com.example.smartpoint.Modelo.dataCatalogoProductos
import com.example.smartpoint.Modelo.dataCategoria
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categorias.php")
    suspend fun obtenerCategorias(): List<dataCategoria>

    @GET("obtener_productos.php")
    suspend fun obtenerProductos(
        @Query("categoria_id") categoriaId: Int,
        @Query("buscar") buscar: String = ""
    ): List<dataCatalogoProductos>
}