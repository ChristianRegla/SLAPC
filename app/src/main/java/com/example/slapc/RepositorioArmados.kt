package com.example.slapc

object RepositorioArmados {
    private val catalogoArmados = mutableListOf<Armado>()

    fun agregarArmado(armado: Armado) {
        catalogoArmados.add(armado)
    }

    fun obtenerArmado(id: Int): Armado? {
        return catalogoArmados.find { it.id == id }
    }

    fun actualizarArmado(id: Int, armado: Armado) {
        val match = catalogoArmados.find { it.id == id }

        if (match != null) {
            match.nombre = armado.nombre
            match.descuento = armado.descuento
            match.categoria = armado.categoria
            match.descripcion = armado.descripcion
            match.componentes = armado.componentes
        }
    }

    fun quitarArmado(id: Int) {
        catalogoArmados.removeIf { it.id == id }
    }

    fun buscarArmadosPorNombre(nombre: String): List<Armado> {
        return catalogoArmados.filter { it.nombre.contains(nombre, ignoreCase = true) }
    }
}
