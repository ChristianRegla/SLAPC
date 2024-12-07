package com.example.slapc.ui.pedidos
import com.example.slapc.CategoriaComponente
import com.example.slapc.ui.carrito.ItemEnCarrito

class componentePedido(
    var nombre: String,
    var cantidad:Int
) {
    var id: Int = obtenerId()

    companion object {
        private var contadorComponentes: Int = 0

        private fun obtenerId(): Int {
            val id = contadorComponentes
            contadorComponentes++
            return id
        }

    }
}
