package com.example.slapc.ui.pedidos

import com.example.slapc.ui.carrito.ItemEnCarrito
import com.example.slapc.ui.pedidos.componentePedido

object RepositorioComponentesPedidos {
    private val catalogoComponentesPed = mutableListOf<ItemEnCarrito>()

    fun agregarComponente(comp: ItemEnCarrito) {
        catalogoComponentesPed.add(comp)
    }

    fun obtenerComponente(nombre: String): ItemEnCarrito? {
        return catalogoComponentesPed.find { c -> c.obtenerNombre() == nombre }
    }

}