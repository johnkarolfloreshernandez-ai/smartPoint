package com.example.smartpoint.Presentador

import com.example.smartpoint.Modelo.UsuarioResponse
import com.example.smartpoint.Modelo.clsUsuarioModelo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsUsuarioPresentador {

    private val modelo = clsUsuarioModelo()

    fun iniciarSesion(
        usuario: String,
        password: String,
        onExito: (UsuarioResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (usuario.isBlank() || password.isBlank()) {
            onError("Completa usuario y contraseña")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = modelo.iniciarSesion(usuario, password)
                if (res.exito) {
                    onExito(res)
                } else {
                    onError(res.mensaje.ifEmpty { "Usuario o contraseña incorrectos" })
                }
            } catch (e: Exception) {
                onError("Error de conexión: " + e.localizedMessage)
            }
        }
    }

    fun registrarUsuario(
        usuario: String,
        correo: String,
        password: String,
        confirmarPassword: String,
        onExito: (UsuarioResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (usuario.isBlank() || correo.isBlank() || password.isBlank() || confirmarPassword.isBlank()) {
            onError("Todos los campos son obligatorios")
            return
        }
        if (password != confirmarPassword) {
            onError("Las contraseñas no coinciden")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = modelo.registrarUsuario(usuario, correo, password)
                if (res.exito) {
                    onExito(res)
                } else {
                    onError(res.mensaje.ifEmpty { "No se pudo crear la cuenta" })
                }
            } catch (e: Exception) {
                onError("Error de conexión: " + e.localizedMessage)
            }
        }
    }

    fun cargarPerfil(
        usuarioId: Int,
        onExito: (UsuarioResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = modelo.obtenerPerfil(usuarioId)
                if (res.exito) {
                    onExito(res)
                } else {
                    onError(res.mensaje)
                }
            } catch (e: Exception) {
                onError("Error al cargar perfil: " + e.localizedMessage)
            }
        }
    }
}