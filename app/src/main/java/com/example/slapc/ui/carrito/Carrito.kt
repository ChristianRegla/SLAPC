package com.example.slapc.ui.carrito

object Carrito {
    private val items = mutableListOf<ItemEnCarrito>()
    var subtotal: Double = 0.0
    var costoGarantias: Double = 0.0
    var total: Double = 0.0
    var garantias = mutableListOf<String>()
    var iva: Double = 0.0

    private var onItemEliminado: (() -> Unit)? = null
    private var onRecalculoDeAcumulados: (() -> Unit)? = null

    fun agregarItem(item: ItemEnCarrito) {
        items.add(item)
        recalcularAcumulados()
    }

    fun obtenerItems(): List<ItemEnCarrito> {
        return items
    }

    fun cambiarCantidadDeItem(itemNum: Int, nuevaCantidad: Int): ItemEnCarrito {
        items[itemNum].cambiarCantidad(nuevaCantidad)
        recalcularAcumulados()
        return items[itemNum]
    }

    fun alternarGarantiaDeItem(itemNum: Int) {
        items[itemNum].alternarGarantia()
        recalcularAcumulados()
    }

    fun agregarCallbackDeEliminacion(callback: () -> Unit) {
        onItemEliminado = callback
    }

    fun quitarCallbackDeEliminacion() {
        onItemEliminado = null
    }

    fun eliminarItem(itemNum: Int) {
        items.removeAt(itemNum)
        recalcularAcumulados()
        onItemEliminado?.invoke()
    }

    fun agregarCallbackDeRecalculos(callback: () -> Unit) {
        onRecalculoDeAcumulados = callback
    }

    fun quitarCallbackDeRecalculos() {
        onRecalculoDeAcumulados = null
    }

    private fun recalcularAcumulados() {
        subtotal = 0.0
        iva = 0.0
        total = 0.0
        costoGarantias = 0.0
        garantias.clear()

        items.forEach { item -> run {
            subtotal += item.subtotal

            if (item.incluirGarantia) {
                costoGarantias += item.subtotal * 0.1
                garantias.add(item.obtenerNombre())
            }
        }}

        iva = (subtotal + costoGarantias) * 0.16
        total = subtotal + costoGarantias + iva

        onRecalculoDeAcumulados?.invoke()
    }
}