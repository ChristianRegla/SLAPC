package com.example.slapc.ui.catalogo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R
import com.example.slapc.ui.catalogo.Producto

class ProductoAdapter(private var productos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size

    fun actualizarProductos(nuevosProductos: List<Producto>) {
        this.productos = nuevosProductos
        notifyDataSetChanged()
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tvNombre)
        private val detallesTextView: TextView = itemView.findViewById(R.id.tvDescription)
        private val imagenImageView: ImageView = itemView.findViewById(R.id.imgProducto)

        fun bind(producto: Producto) {
            nombreTextView.text = producto.nombre
            detallesTextView.text = producto.detallesTecnicos
            imagenImageView.setImageResource(producto.reflimagen)
        }
    }
}

private fun ImageView.setImageResource(any: Any) {}
