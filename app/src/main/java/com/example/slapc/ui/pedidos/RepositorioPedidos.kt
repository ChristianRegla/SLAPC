package com.example.slapc.ui.pedidos

import com.example.slapc.CategoriaComponente
import com.example.slapc.Componente

object RepositorioPedidos {
    private val listaPedidos = mutableListOf<Pedido>()
    init {
        // Ejemplo de pedido inicial
        val componente1 = Componente(
            nombre = "Tornillo de acero",
            refImagen = "https://example.com/tornillo_acero.png",
            precio = 10.5,
            categoria = CategoriaComponente.ALMACENAMIENTO,
            detallesTecnicos = "Tornillo de acero inoxidable, medida 8x50mm"
        )

        val componente2 = Componente(
            nombre = "Llave inglesa",
            refImagen = "https://example.com/llave_inglesa.png",
            precio = 15.99,
            categoria = CategoriaComponente.FUENTE_DE_PODER,
            detallesTecnicos = "Llave inglesa ajustable de 6 pulgadas"
        )

        // Crear el pedido inicial
        val pedidoEjemplo = Pedido(
            id = "1001",
            fechaCompra = "2024-12-04",
            fechaEntrega = "2024-12-10",
            horaEntrega = "14:00",
            componentes = listOf(componente1.nombre, componente2.nombre),
            total = componente1.precio + componente2.precio,
            garantias = listOf("Garantía estándar", "Garantía extendida")
        )

        // Agregar el pedido inicial a la lista
        listaPedidos.add(pedidoEjemplo)
    }


    // Agregar un nuevo pedido
    fun agregarPedido(pedido: Pedido) {
        listaPedidos.add(pedido)
    }

    // Obtener todos los pedidos
    fun obtenerPedidos(): List<Pedido> {
        return listaPedidos
    }

    // Buscar un pedido por ID
    fun buscarPedidoPorId(id: String): Pedido? {
        return listaPedidos.find { it.id == id }
    }

}