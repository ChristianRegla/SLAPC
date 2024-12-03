package com.example.slapc.ui.carrito

import com.example.slapc.R

abstract class ItemEnCarrito(protected val idReferencia: Int, val tipoElemento: String) {
    var cantidad: Int = 0
    var subtotal: Double = 0.0
    var incluirGarantia: Boolean = false

    fun cambiarCantidad(nuevaCantidad: Int) {
        cantidad = nuevaCantidad
        subtotal = calcularSubtotal()
    }

    abstract fun calcularSubtotal(): Double
    abstract fun obtenerImagen(): Int
    abstract fun obtenerNombre(): String
}