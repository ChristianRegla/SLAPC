package com.example.slapc

class ItemEnCarrito (
    val idReferencia: Int,
    val tipoElemento: String,
    var cantidad: Int,
    var subtotal: Double,
    var incluirGarantia: Boolean
) {
    fun obtenerNombre(): String {
        return "[nombre]"
    }

    fun obtenerImagen(): Int {
        return R.drawable.full_logo
    }
}