package com.example.smartpoint.interfaceApi

import com.example.smartpoint.Modelo.CarritoRequest
import com.example.smartpoint.Modelo.LoginRequest
import com.example.smartpoint.Modelo.RegistroUsuarioRequest
import com.example.smartpoint.Modelo.RespuestaGeneral
import com.example.smartpoint.Modelo.UsuarioResponse
import com.example.smartpoint.Modelo.VentaRequest
import com.example.smartpoint.Modelo.dataCarritoItem
import com.example.smartpoint.Modelo.dataCatalogoProductos
import com.example.smartpoint.Modelo.dataCategoria
import com.example.smartpoint.Modelo.dataImagenProducto
import com.example.smartpoint.Modelo.dataVentaHistorial
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @GET("carrito.php")
    suspend fun obtenerCarrito(@Query("usuario_id") usuarioId: Int): List<dataCarritoItem>

    @POST("carrito.php")
    suspend fun agregarAlCarrito(@Body request: CarritoRequest): RespuestaGeneral

    @DELETE("carrito.php") suspend fun eliminarDelCarrito(@Query("id") idCarrito: Int
    ): RespuestaGeneral

    @POST("ventas.php")
    suspend fun finalizarCompra(@Body request: VentaRequest): RespuestaGeneral

    @GET("ventas.php")
    suspend fun obtenerHistorialVentas(@Query("usuario_id") usuarioId: Int): List<dataVentaHistorial>

    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): UsuarioResponse

    @POST("registro.php")
    suspend fun registrarUsuario(@Body request: RegistroUsuarioRequest): UsuarioResponse

    @GET("perfil.php")
    suspend fun obtenerPerfil(@Query("usuario_id") usuarioId: Int): UsuarioResponse

}