package com.example.slapc.ui.pedidos

import com.example.slapc.ui.pedidos.componentePedido

object RepositorioComponentesPedidos {
    private val catalogoComponentesPed = mutableListOf<componentePedido>()

    fun agregarComponente(comp: componentePedido) {
        catalogoComponentesPed.add(comp)
    }

    fun obtenerComponente(id: Int): componentePedido? {
        return catalogoComponentesPed.find { c -> c.id == id }
    }

    fun buscarComponente(nombre: String): componentePedido? {
        return catalogoComponentesPed.find { c -> c.nombre == nombre }
    }

    fun actualizarComponente(id: Int, comp: componentePedido) {
        val match = catalogoComponentesPed.find { c -> c.id == id }

        if (match != null) {
            match.nombre = comp.nombre
            match.refImagen = comp.refImagen
            match.categoria = comp.categoria
            match.precio = comp.precio
            match.detallesTecnicos = comp.detallesTecnicos
        }
    }

    fun quitarComponente(id: Int) {
        catalogoComponentesPed.removeIf { c -> c.id == id }
    }

    fun obtenerComponentes(): List<componentePedido> {
        return catalogoComponentesPed
    }
}