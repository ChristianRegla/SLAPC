package com.example.slapc.ui.pedidos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R


class ComponentePedidoAdapter(
    private val componentes: List<componentePedido>
) : RecyclerView.Adapter<ComponentePedidoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_pedido, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val componente = componentes[position]
        holder.tvNombre.text = componente.nombre
        holder.tvCantidad.text = "Cantidad: ${componente.cantidad}"
    }

    override fun getItemCount(): Int = componentes.size
}
