package com.example.slapc.ui.carrito

abstract class ItemEnCarrito(protected val idReferencia: Int, val tipoElemento: String) {
    var cantidad: Int = 0
    var subtotal: Double = 0.0
    var incluirGarantia: Boolean = false

    fun cambiarCantidad(nuevaCantidad: Int) {
        cantidad = nuevaCantidad
        subtotal = calcularSubtotal()
    }

    fun alternarGarantia() {
        incluirGarantia = !incluirGarantia
    }

    abstract fun calcularSubtotal(): Double
    abstract fun obtenerReferenciaImagen(): String
    abstract fun obtenerNombre(): String
}