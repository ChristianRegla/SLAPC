package com.example.slapc

object RepositorioArmados {
    private val catalogoArmados = mutableListOf<Armado>()

    init {
        catalogoArmados.addAll(
            listOf(
                Armado(1, "PC Gamer Ultra", 15.0, "Gaming", "Configuración ideal para juegos en calidad ultra.", listOf(
                    Componente("Intel Core i7-14700", "intel_i7", 7599.00, CategoriaComponente.CPU, "Procesador Intel Core i7-14700 / LGA1700 / 2.10GHz, 20-Core / 33MB Smart Cache / 14va. Generación / Intel UHD Graphics 770 / Raptor Lake / BX8071514700"),
                    Componente("Unidad de Estado Sólido M.2 Samsung 980 / 1TB", "ssd_samsung", 2499.00, CategoriaComponente.ALMACENAMIENTO, "Unidad de Estado Sólido M.2 Samsung 980 / 1TB / NVMe Interfaz de Estado Sólido con Tecnología V-NAND / MZ-V8V1T0B/AM"),
                    Componente("Kit Memoria RAM Kingston FURY Beast RGB Black DDR5", "ram_fury", 2699.00, CategoriaComponente.RAM, "Kit Memoria RAM Kingston FURY Beast RGB Black DDR5 / 5200MHz / 32GB (2 x 16GB) / Non-ECC / CL40 / XMP / KF552C40BBAK2-32"),
                    Componente("Fuente De Poder Cooler Master MWE V3 / 650W", "fuente_poder_cooler_master", 1299.00, CategoriaComponente.FUENTE_DE_PODER, "Fuente De Poder Cooler Master MWE V3 / 650W / 80+Bronze / MPE-6501-ACAAW-3BUS"),
                    Componente("Gabinete Acteck Doom Pro Crystal GI730", "gabinete_acteck", 1069.00, CategoriaComponente.GABINETE, "Gabinete Acteck Doom Pro Crystal GI730 / Mini-Tower / Fuente de Poder 600W / 3x120mm / Blanco / AC-939232"),
                    Componente("TARJETA MADRE NZXT N7 B650E", "tarjeta_madre", 6199.00, CategoriaComponente.MOTHERBOARD, "TARJETA MADRE NZXT N7 B650E / Negro / AM5 / AMD B650E / Placa Base ATX / DDR5 / USB 3.2 / HDMI / N7-B65XT-B1"),
                    Componente("Tarjeta de Video Asus TUF Gaming GeForce RTX 4090 OG OC Edition", "rtx_4090", 38999.00, CategoriaComponente.GPU, "Tarjeta de Video Asus TUF Gaming GeForce RTX™ 4090 OG OC Edition / PCIe 4.0 / 24GB GDDR6X / DLSS 3 / HDMI 2.1 / DisplayPort 1.4a / TUF-RTX4090-O24G-OG-GAMING")
                ), 65000.00),
                Armado(2, "PC Creativa Pro", 10.0, "Edición", "Configuración ideal para creadores de contenido.", listOf(
                    Componente("Intel Core i7-14700", "intel_i7", 7599.00, CategoriaComponente.CPU, "Procesador Intel Core i7-14700 / LGA1700 / 2.10GHz, 20-Core / 33MB Smart Cache / 14va. Generación / Intel UHD Graphics 770 / Raptor Lake / BX8071514700"),
                    Componente("Unidad de Estado Sólido M.2 Samsung 980 / 1TB", "ssd_samsung", 2499.00, CategoriaComponente.ALMACENAMIENTO, "Unidad de Estado Sólido M.2 Samsung 980 / 1TB / NVMe Interfaz de Estado Sólido con Tecnología V-NAND / MZ-V8V1T0B/AM"),
                    Componente("Kit Memoria RAM Kingston FURY Beast RGB Black DDR5", "ram_fury", 2699.00, CategoriaComponente.RAM, "Kit Memoria RAM Kingston FURY Beast RGB Black DDR5 / 5200MHz / 32GB (2 x 16GB) / Non-ECC / CL40 / XMP / KF552C40BBAK2-32"),
                    Componente("Fuente De Poder Cooler Master MWE V3 / 650W", "fuente_poder_cooler_master", 1299.00, CategoriaComponente.FUENTE_DE_PODER, "Fuente De Poder Cooler Master MWE V3 / 650W / 80+Bronze / MPE-6501-ACAAW-3BUS"),
                    Componente("Gabinete Acteck Doom Pro Crystal GI730", "gabinete_acteck", 1069.00, CategoriaComponente.GABINETE, "Gabinete Acteck Doom Pro Crystal GI730 / Mini-Tower / Fuente de Poder 600W / 3x120mm / Blanco / AC-939232"),
                    Componente("TARJETA MADRE NZXT N7 B650E", "tarjeta_madre", 6199.00, CategoriaComponente.MOTHERBOARD, "TARJETA MADRE NZXT N7 B650E / Negro / AM5 / AMD B650E / Placa Base ATX / DDR5 / USB 3.2 / HDMI / N7-B65XT-B1")
                ), 52000.00),
                Armado(3, "PC Oficina Plus", 5.0, "Ofimática", "Configuración eficiente para trabajos de oficina.", listOf(
                    Componente("Intel Core i7-14700", "intel_i7", 7599.00, CategoriaComponente.CPU, "Procesador Intel Core i7-14700 / LGA1700 / 2.10GHz, 20-Core / 33MB Smart Cache / 14va. Generación / Intel UHD Graphics 770 / Raptor Lake / BX8071514700"),
                    Componente("Unidad de Estado Sólido M.2 Samsung 980 / 1TB", "ssd_samsung", 2499.00, CategoriaComponente.ALMACENAMIENTO, "Unidad de Estado Sólido M.2 Samsung 980 / 1TB / NVMe Interfaz de Estado Sólido con Tecnología V-NAND / MZ-V8V1T0B/AM"),
                    Componente("Kit Memoria RAM Kingston FURY Beast RGB Black DDR5", "ram_fury", 2699.00, CategoriaComponente.RAM, "Kit Memoria RAM Kingston FURY Beast RGB Black DDR5 / 5200MHz / 32GB (2 x 16GB) / Non-ECC / CL40 / XMP / KF552C40BBAK2-32"),
                    Componente("Fuente De Poder Cooler Master MWE V3 / 650W", "fuente_poder_cooler_master", 1299.00, CategoriaComponente.FUENTE_DE_PODER, "Fuente De Poder Cooler Master MWE V3 / 650W / 80+Bronze / MPE-6501-ACAAW-3BUS"),
                    Componente("Gabinete Acteck Doom Pro Crystal GI730", "gabinete_acteck", 1069.00, CategoriaComponente.GABINETE, "Gabinete Acteck Doom Pro Crystal GI730 / Mini-Tower / Fuente de Poder 600W / 3x120mm / Blanco / AC-939232"),
                    Componente("TARJETA MADRE NZXT N7 B650E", "tarjeta_madre", 6199.00, CategoriaComponente.MOTHERBOARD, "TARJETA MADRE NZXT N7 B650E / Negro / AM5 / AMD B650E / Placa Base ATX / DDR5 / USB 3.2 / HDMI / N7-B65XT-B1")
                ), 25000.00),
                Armado(4, "PC Edición Avanzada", 20.0, "Edición", "Perfecta para renderizar y edición de video profesional.", listOf(
                    Componente("Intel Core i7-14700", "intel_i7", 7599.00, CategoriaComponente.CPU, "Procesador Intel Core i7-14700 / LGA1700 / 2.10GHz, 20-Core / 33MB Smart Cache / 14va. Generación / Intel UHD Graphics 770 / Raptor Lake / BX8071514700"),
                    Componente("Unidad de Estado Sólido M.2 Samsung 980 / 1TB", "ssd_samsung", 2499.00, CategoriaComponente.ALMACENAMIENTO, "Unidad de Estado Sólido M.2 Samsung 980 / 1TB / NVMe Interfaz de Estado Sólido con Tecnología V-NAND / MZ-V8V1T0B/AM"),
                    Componente("Kit Memoria RAM Kingston FURY Beast RGB Black DDR5", "ram_fury", 2699.00, CategoriaComponente.RAM, "Kit Memoria RAM Kingston FURY Beast RGB Black DDR5 / 5200MHz / 32GB (2 x 16GB) / Non-ECC / CL40 / XMP / KF552C40BBAK2-32"),
                    Componente("Fuente De Poder Cooler Master MWE V3 / 650W", "fuente_poder_cooler_master", 1299.00, CategoriaComponente.FUENTE_DE_PODER, "Fuente De Poder Cooler Master MWE V3 / 650W / 80+Bronze / MPE-6501-ACAAW-3BUS"),
                    Componente("Gabinete Acteck Doom Pro Crystal GI730", "gabinete_acteck", 1069.00, CategoriaComponente.GABINETE, "Gabinete Acteck Doom Pro Crystal GI730 / Mini-Tower / Fuente de Poder 600W / 3x120mm / Blanco / AC-939232"),
                    Componente("TARJETA MADRE NZXT N7 B650E", "tarjeta_madre", 6199.00, CategoriaComponente.MOTHERBOARD, "TARJETA MADRE NZXT N7 B650E / Negro / AM5 / AMD B650E / Placa Base ATX / DDR5 / USB 3.2 / HDMI / N7-B65XT-B1"),
                    Componente("Tarjeta de Video Asus TUF Gaming GeForce RTX 4090 OG OC Edition", "rtx_4090", 38999.00, CategoriaComponente.GPU, "Tarjeta de Video Asus TUF Gaming GeForce RTX™ 4090 OG OC Edition / PCIe 4.0 / 24GB GDDR6X / DLSS 3 / HDMI 2.1 / DisplayPort 1.4a / TUF-RTX4090-O24G-OG-GAMING")
                ), 75000.00),
                Armado(5, "PC Básica Escolar", 8.0, "Estudios", "Ideal para tareas escolares y navegación básica.", listOf(
                    Componente("Intel Core i7-14700", "intel_i7", 7599.00, CategoriaComponente.CPU, "Procesador Intel Core i7-14700 / LGA1700 / 2.10GHz, 20-Core / 33MB Smart Cache / 14va. Generación / Intel UHD Graphics 770 / Raptor Lake / BX8071514700"),
                    Componente("Unidad de Estado Sólido M.2 Samsung 980 / 1TB", "ssd_samsung", 2499.00, CategoriaComponente.ALMACENAMIENTO, "Unidad de Estado Sólido M.2 Samsung 980 / 1TB / NVMe Interfaz de Estado Sólido con Tecnología V-NAND / MZ-V8V1T0B/AM"),
                    Componente("Kit Memoria RAM Kingston FURY Beast RGB Black DDR5", "ram_fury", 2699.00, CategoriaComponente.RAM, "Kit Memoria RAM Kingston FURY Beast RGB Black DDR5 / 5200MHz / 32GB (2 x 16GB) / Non-ECC / CL40 / XMP / KF552C40BBAK2-32"),
                    Componente("Fuente De Poder Cooler Master MWE V3 / 650W", "fuente_poder_cooler_master", 1299.00, CategoriaComponente.FUENTE_DE_PODER, "Fuente De Poder Cooler Master MWE V3 / 650W / 80+Bronze / MPE-6501-ACAAW-3BUS"),
                    Componente("Gabinete Acteck Doom Pro Crystal GI730", "gabinete_acteck", 1069.00, CategoriaComponente.GABINETE, "Gabinete Acteck Doom Pro Crystal GI730 / Mini-Tower / Fuente de Poder 600W / 3x120mm / Blanco / AC-939232"),
                    Componente("TARJETA MADRE NZXT N7 B650E", "tarjeta_madre", 6199.00, CategoriaComponente.MOTHERBOARD, "TARJETA MADRE NZXT N7 B650E / Negro / AM5 / AMD B650E / Placa Base ATX / DDR5 / USB 3.2 / HDMI / N7-B65XT-B1")
                ), 20000.00),
            )
        )
    }

    fun agregarArmado(armado: Armado): Boolean {
        if (catalogoArmados.any { it.id == armado.id }) {
            return false
        }
        catalogoArmados.add(armado)
        return true
    }

    fun actualizarArmado(id: Int, armado: Armado): Boolean {
        val index = catalogoArmados.indexOfFirst { it.id == id }

        return if (index != -1) {
            catalogoArmados[index] = armado.copy()
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

    fun obtenerArmado(id: Int): Armado? {
        return catalogoArmados.find({ a -> a.id == id})
    }

    fun obtenerTodosLosArmados(): List<Armado> {
        return catalogoArmados.toList()
    }
}
