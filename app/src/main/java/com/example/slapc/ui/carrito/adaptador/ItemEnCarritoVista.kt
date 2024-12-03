package com.example.slapc.ui.carrito.adaptador

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.ui.carrito.ItemEnCarrito
import com.example.slapc.R
import com.example.slapc.ui.carrito.Carrito
import com.example.slapc.ui.carrito.CarritoFragment

class ItemEnCarritoVista(
    view: View,
    val onCantidadEditada: (
        itemNum: Int,
        callbackRedibujo: (nuevoModelo: ItemEnCarrito) -> Unit
    ) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val imgImagen: ImageView = view.findViewById(R.id.imgItmCarImagen)
    private val txtNombre: TextView = view.findViewById(R.id.txtItmCarNombre)
    private val txtCantidad: TextView = view.findViewById(R.id.txtItmCarCantidad)
    private val ibtnEditarCantidad: ImageButton = view.findViewById(R.id.ibtnItmCarEditarCantidad)
    private val txtSubtotal: TextView = view.findViewById(R.id.txtItmCarSubtotal)
    private val tgbGarantia: ToggleButton = view.findViewById(R.id.tgbItmCarGarantia)
    private val ibtnEliminar: ImageButton = view.findViewById(R.id.ibtnItmCarEliminar)

    fun renderizarDatos(modeloItem: ItemEnCarrito, itemNum: Int) {
        imgImagen.setImageResource(modeloItem.obtenerImagen())
        txtNombre.text = modeloItem.obtenerNombre()
        txtCantidad.text = "Cant: ${modeloItem.cantidad}"
        txtSubtotal.text = "$${String.format("%.2f", modeloItem.subtotal)}"
        tgbGarantia.isChecked = modeloItem.incluirGarantia

        ibtnEditarCantidad.setOnClickListener {
            onCantidadEditada(itemNum, { nuevoModelo -> actualizarDatosDeCantidad(nuevoModelo)})
        }

        tgbGarantia.setOnClickListener { Carrito.alternarGarantiaDeItem(itemNum) }

        ibtnEliminar.setOnClickListener { Carrito.eliminarItem(itemNum) }
    }

    fun actualizarDatosDeCantidad(nuevoModelo: ItemEnCarrito) {
        txtCantidad.text = "Cant: ${nuevoModelo.cantidad}"
        txtSubtotal.text = "$${String.format("%.2f", nuevoModelo.subtotal)}"
    }
}