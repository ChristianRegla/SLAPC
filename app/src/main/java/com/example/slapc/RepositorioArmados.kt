package com.example.slapc

object RepositorioArmados {
    private val catalogoArmados = mutableListOf<Armado>()

    fun agregarArmado(armado: Armado): Boolean {
        if (catalogoArmados.any { it.id == armado.id }) {
            return false
        }
        catalogoArmados.add(armado)
        return true
    }

    fun obtenerArmado(id: Int): Armado? {
        return catalogoArmados.find { it.id == id }
    }

    fun actualizarArmado(id: Int, armado: Armado): Boolean {
        val index = catalogoArmados.indexOfFirst { it.id == id }

        return if (index != -1) {
            catalogoArmados[index] = armado
            true
        } else {
            false
        }
    }

    fun quitarArmado(id: Int): Boolean {
        return catalogoArmados.removeIf { it.id == id }
    }

    fun buscarArmadosPorNombre(nombre: String): List<Armado> {
        return catalogoArmados.filter { it.nombre.contains(nombre, ignoreCase = true) }
    }

    fun obtenerTodosArmados(): List<Armado> {
        return catalogoArmados
    }
}
