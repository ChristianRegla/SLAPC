package com.example.slapc

object RepositorioComponentes {
    private val catalogoComponentes = mutableListOf<Componente>()

    // Productos para calar
    init {
        catalogoComponentes.addAll(
            listOf(
                Componente("Intel Core i7-14700", "intel_i7", 7599.00, CategoriaComponente.CPU, "Procesador Intel Core i7-14700 / LGA1700 / 2.10GHz, 20-Core / 33MB Smart Cache / 14va. Generación / Intel UHD GRaphics 770 / Raptor Lake / BX8071514700"),
                Componente("Unidad de Estado Sólido M.2 Samsung 980 / 1TB", "ssd_samsung", 2499.00, CategoriaComponente.ALMACENAMIENTO, "Unidad de Estado Sólido M.2 Samsung 980 / 1TB / NVMe Interfaz de Estado Sólido con Tecnología V-NAND / MZ-V8V1T0B/AM"),
                Componente("Monitor Gamer LG UltraGear 24 FHD / 24GL600F-B", "monitor", 4899.00, CategoriaComponente.MONITOR, "Monitor Gamer LG UltraGear 24\" FHD / 1ms - 144Hz / HDMI x2 / 24GL600F-B"),
                Componente("Mouse Gamer Logitech G502 Lightspeed Wireless", "mouse_logitech", 1999.00, CategoriaComponente.MOUSE, "Mouse Gamer Logitech G502 Lightspeed Wireless / 16000 DPI / 910-005566"),
                Componente("Logitech Teclado Gamer PRO X TKL", "teclado_mecanico", 3199.00, CategoriaComponente.TECLADO, "Logitech Teclado Gamer PRO X TKL / Mecánico / Switch GX Brown / Inalámbrico / Negro / (Inglés) / 920-012127")
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