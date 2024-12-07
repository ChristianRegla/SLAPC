package com.example.slapc

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.ui.detalles.ArmadoDetailActivity
import kotlin.math.round

class ArmadoAdapter(private val armados: List<Armado>,
                     private val activityWithResultLauncher: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<ArmadoAdapter.ArmadoViewHolder>() {

    inner class ArmadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreArmado: TextView = itemView.findViewById(R.id.tvNombreArmado)
        val tvDescuentoArmado: TextView = itemView.findViewById(R.id.tvDescuentoArmado)
        val tvPrecioArmado: TextView = itemView.findViewById(R.id.tvPrecioArmado)
        val tvProductosArmado: TextView = itemView.findViewById(R.id.tvProductosArmado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArmadoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_armado, parent, false)
        return ArmadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArmadoViewHolder, position: Int) {
        val armado = armados[position]
        armado.precio = round(armado.obtenerPrecio())

        holder.tvNombreArmado.text = armado.nombre
        holder.tvDescuentoArmado.text = "$${armado.precio}"
        holder.tvDescuentoArmado.paintFlags = holder.tvDescuentoArmado.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.tvPrecioArmado.text = "$${armado.precio - ((armado.descuento / 100) * armado.precio)}"
        holder.tvProductosArmado.text = "${armado.componentes.joinToString { "\n${it.nombre}" }}"

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ArmadoDetailActivity::class.java)
            intent.putExtra("id", armado.id)
            activityWithResultLauncher.launch(intent)
        }
    }

    override fun getItemCount(): Int = armados.size
}