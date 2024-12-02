package com.example.slapc.ui.carrito.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.ItemEnCarrito
import com.example.slapc.R

class ItemEnCarritoAdaptador(private val listaItems: List<ItemEnCarrito>) :
RecyclerView.Adapter<ItemEnCarritoVista>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemEnCarritoVista {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemEnCarritoVista(layoutInflater.inflate(R.layout.cardview_item_carrito, parent, false))
    }

    override fun getItemCount(): Int = listaItems.size

    override fun onBindViewHolder(holder: ItemEnCarritoVista, position: Int) {
        val item = listaItems[position]
        holder.renderizarDatos(item)
    }
}