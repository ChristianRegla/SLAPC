package com.example.slapc.ui.pedidos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R

class PedidoAdapter(
    private val pedidos: List<Pedido>,
    private val onClick: (Pedido) -> Unit
) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    class PedidoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numeroPedido: TextView = view.findViewById(R.id.text_numero_pedido)
        val fechaEntrega: TextView = view.findViewById(R.id.text_fecha_entrega)
        val precioTotal: TextView = view.findViewById(R.id.text_precio)
        val botonDetalles: Button = view.findViewById(R.id.button_ver_detalles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.numeroPedido.text = "No. de Pedido: ${pedido.id}"
        holder.fechaEntrega.text = "Fecha de Entrega: ${pedido.fechaEntrega} a las ${pedido.horaEntrega}"
        holder.precioTotal.text = "Total: $${pedido.total}"
        holder.botonDetalles.setOnClickListener { onClick(pedido) }
    }

    override fun getItemCount(): Int = pedidos.size
}
