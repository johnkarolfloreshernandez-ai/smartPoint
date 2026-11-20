package com.example.smartpoint.Modelo

import com.example.smartpoint.Red.RetrofitCliente

class clsUsuarioModelo {

    suspend fun iniciarSesion(usuario: String, password: String): UsuarioResponse {
        val request = LoginRequest(usuario = usuario, password = password)
        return RetrofitCliente.apiService.login(request)
    }

    suspend fun registrarUsuario(usuario: String, correo: String, telefono: String, password: String): UsuarioResponse {
        val request = RegistroUsuarioRequest(usuario = usuario, correo = correo, password = password, telefono = telefono)
        return RetrofitCliente.apiService.registrarUsuario(request)
    }

    suspend fun obtenerPerfil(usuarioId: Int): UsuarioResponse {
        return RetrofitCliente.apiService.obtenerPerfil(usuarioId)
    }

    suspend fun obtenerComprasUsuario(usuarioId: Int): List<dataVentaHistorial> {
        return RetrofitCliente.apiService.obtenerHistorialVentas(usuarioId)
    }
}