package com.example.slapc.ui.carrito.adaptador

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.ui.carrito.ItemEnCarrito
import com.example.slapc.R

class ItemEnCarritoVista(val view: View) : RecyclerView.ViewHolder(view) {
    private val imgImagen: ImageView = view.findViewById(R.id.imgItmCarImagen)
    private val txtNombre: TextView = view.findViewById(R.id.txtItmCarNombre)
    private val edtCantidad: EditText = view.findViewById(R.id.edtItmCarCantidad)
    private val txtSubtotal: TextView = view.findViewById(R.id.txtItmCarSubtotal)
    private val tgbGarantia: ToggleButton = view.findViewById(R.id.tgbItmCarGarantia)
    private val ibtnEliminar: ImageButton = view.findViewById(R.id.ibtnItmCarEliminar)

    fun renderizarDatos(modeloItem: ItemEnCarrito) {
        imgImagen.setImageResource(modeloItem.obtenerImagen())
        txtNombre.text = modeloItem.obtenerNombre()
        edtCantidad.setText("${modeloItem.cantidad}")
        txtSubtotal.text = "${modeloItem.subtotal}"
        tgbGarantia.isActivated = modeloItem.incluirGarantia
    }
}