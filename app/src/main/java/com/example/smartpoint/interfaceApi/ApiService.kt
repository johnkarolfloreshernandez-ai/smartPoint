package com.example.smartpoint.interfaceApi

import com.example.smartpoint.Modelo.CarritoRequest
import com.example.smartpoint.Modelo.RespuestaGeneral
import com.example.smartpoint.Modelo.dataCatalogoProductos
import com.example.smartpoint.Modelo.dataCategoria
import com.example.smartpoint.Modelo.dataImagenProducto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("categorias.php")
    suspend fun obtenerCategorias(): List<dataCategoria>

    @GET("obtener_productos.php")
    suspend fun obtenerProductos(@Query("categoria_id") categoriaId: Int, @Query("buscar") buscar: String = ""): List<dataCatalogoProductos>

    @GET("detalle_producto.php")
    suspend fun obtenerGaleriaProducto(@Query("producto_id") productoId: Int): List<dataImagenProducto>

    @POST("carrito.php")
    suspend fun agregarAlCarrito(@Body request: CarritoRequest): RespuestaGeneral
}