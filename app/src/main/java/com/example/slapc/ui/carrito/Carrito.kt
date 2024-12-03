package com.example.slapc.ui.carrito

import android.util.Log

object Carrito {
    private val items = mutableListOf<ItemEnCarrito>()
    var subtotal: Double = 0.0
    var costoGarantias: Double = 0.0
    var total: Double = 0.0
    private val garantias = mutableListOf<String>()

    fun agregarItem(item: ItemEnCarrito) {
        items.add(item)
    }

    fun obtenerItems(): List<ItemEnCarrito> {
        return items
    }

    fun cambiarCantidadDeItem(itemNum: Int, nuevaCantidad: Int): ItemEnCarrito {
        items[itemNum].cambiarCantidad(nuevaCantidad)
        return items[itemNum]
    }

    fun alternarGarantiaDeItem(itemNum: Int) {
        items[itemNum].alternarGarantia()
    }
}