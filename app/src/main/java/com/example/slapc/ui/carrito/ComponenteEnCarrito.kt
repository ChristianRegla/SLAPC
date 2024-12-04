package com.example.slapc.ui.carrito

import com.example.slapc.Componente
import com.example.slapc.R
import com.example.slapc.RepositorioComponentes

class ComponenteEnCarrito(idReferencia: Int, cantidad: Int) : ItemEnCarrito(idReferencia, "Componente") {
    private val componente: Componente

    init {
        componente = RepositorioComponentes.obtenerComponente(idReferencia)!!
        this.cantidad = cantidad
        subtotal = calcularSubtotal()
        incluirGarantia = false
    }

    override fun calcularSubtotal(): Double {
        return componente.precio * cantidad
    }

    // TODO: Cambiar stub por busqueda de imagen
    override fun obtenerReferenciaImagen(): String {
        return componente.refImagen
    }

    override fun obtenerNombre(): String {
        return componente.nombre
    }
}