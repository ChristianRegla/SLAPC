package com.example.slapc

object RepositorioComponentes {
    private val catalogoComponentes = mutableListOf<Componente>()

    fun agregarComponente(comp: Componente) {
        catalogoComponentes.add(comp)
    }

    fun obtenerComponente(id: Int): Componente? {
        return catalogoComponentes.find { c -> c.id == id }
    }

    fun buscarComponente(nombre: String): Componente? {
        return catalogoComponentes.find { c -> c.nombre == nombre }
    }

    fun actualizarComponente(id: Int, comp: Componente) {
        val match = catalogoComponentes.find { c -> c.id == id }

        if (match != null) {
            match.nombre = comp.nombre
            match.refImagen = comp.refImagen
            match.categoria = comp.categoria
            match.precio = comp.precio
            match.detallesTecnicos = comp.detallesTecnicos
        }
    }

    fun quitarComponente(id: Int) {
        catalogoComponentes.removeIf { c -> c.id == id }
    }
}