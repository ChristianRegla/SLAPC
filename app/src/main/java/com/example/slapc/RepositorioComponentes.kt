package com.example.slapc

object RepositorioComponentes {
    private val catalogoComponentes = mutableListOf<Componente>()

    // Productos para calar
    init {
        catalogoComponentes.addAll(
            listOf(
                Componente("Procesador Intel i7", "intel_i7", 320.0, CategoriaComponente.CPU, "8 núcleos, 3.6GHz"),
                Componente("SSD Samsung 1TB", "ssd_samsung", 120.0, CategoriaComponente.ALMACENAMIENTO, "1TB NVMe"),
                Componente("Monitor LG 24''", "monitor", 200.0, CategoriaComponente.MONITOR, "Full HD, IPS"),
                Componente("Mouse Logitech", "mouse_logitech", 1000.0, CategoriaComponente.MOUSE, "Óptico, ergonómico"),
                Componente("Teclado Mecánico", "teclado_mecanico", 1200.0, CategoriaComponente.TECLADO, "RGB, switch azul")
            )
        )
    }

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

    fun obtenerComponentes(): List<Componente> {
        return catalogoComponentes
    }
}