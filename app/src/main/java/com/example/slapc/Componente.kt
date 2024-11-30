package com.example.slapc

class Componente (
    nombre: String,
    refImagen: String,
    precio: Double,
    categoria: CategoriaComponente,
    detallesTecnicos: String
) {
    var id: Int = obtenerId()
    var nombre: String = nombre
    var refImagen: String = refImagen
    var precio: Double = precio
    var categoria: CategoriaComponente = categoria
    var detallesTecnicos: String = detallesTecnicos

    companion object {
        private var contadorComponentes: Int = 0

        private fun obtenerId(): Int {
            val id = contadorComponentes
            contadorComponentes++
            return id
        }

        fun obtenerCategoriaConNombre(categoria: String): CategoriaComponente? {
            return when (categoria) {
                "CPU" -> CategoriaComponente.CPU;
                "Disco duro" -> CategoriaComponente.DISCO_DURO;
                "Monitor" -> CategoriaComponente.MONITOR;
                "Mouse" -> CategoriaComponente.MOUSE;
                "Teclado" -> CategoriaComponente.TECLADO;
                "RAM" -> CategoriaComponente.RAM;
                else -> null
            }
        }

        fun obtenerNombreDeCategoria(categoria: CategoriaComponente): String {
            return when (categoria) {
                CategoriaComponente.CPU -> "CPU";
                CategoriaComponente.DISCO_DURO -> "Disco duro";
                CategoriaComponente.MONITOR -> "Monitor";
                CategoriaComponente.MOUSE -> "Mouse";
                CategoriaComponente.TECLADO -> "Teclado";
                CategoriaComponente.RAM -> "RAM"
            }
        }
    }

};