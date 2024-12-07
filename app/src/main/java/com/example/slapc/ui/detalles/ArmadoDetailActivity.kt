package com.example.slapc.ui.detalles

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R
import com.example.slapc.RepositorioArmados
import com.example.slapc.ui.carrito.ArmadoEnCarrito
import com.example.slapc.ui.carrito.Carrito
import com.example.slapc.ui.carrito.adaptador.ItemArmadoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.round

class ArmadoDetailActivity : AppCompatActivity() {
    private lateinit var tvCategoriaArmado: TextView
    private lateinit var tvNombreArmado: TextView
    private lateinit var tvPrecioArmado: TextView
    private lateinit var tvDescuentoArmado: TextView
    private lateinit var tvPrecioFinalArmado: TextView
    private lateinit var tvDescripcionArmado: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemArmadoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_armado_detail)

        val id = intent.getIntExtra("id", 0)
        val armado = RepositorioArmados.obtenerArmado(id) ?:
        throw IllegalArgumentException("No existe el armado con id $id")

        val nombre = armado.nombre
        val descuento = armado.descuento
        val categoria = armado.categoria
        val descripcion = armado.descripcion
        val componentes = armado.componentes
        val precio = round(armado.obtenerPrecio())

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ItemArmadoAdapter(componentes)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = adapter

        val fabAddToCart: FloatingActionButton = findViewById(R.id.fabAddToCartArmado)
        fabAddToCart.setOnClickListener { validarSesionParaCompra(id) }

        tvCategoriaArmado = findViewById(R.id.tvCategoriaArmadoDetails)
        tvNombreArmado = findViewById(R.id.tvNombreArmadoDetails)
        tvPrecioArmado = findViewById(R.id.tvPrecioArmadoDetails)
        tvDescuentoArmado = findViewById(R.id.tvDescuentoArmadoDetails)
        tvPrecioFinalArmado = findViewById(R.id.tvPrecioFinalDetails)
        tvDescripcionArmado = findViewById(R.id.tvDescripcionArmadoDetails)

        tvCategoriaArmado.text = categoria
        tvNombreArmado.text = nombre
        tvPrecioArmado.text = "Precio normal: " + String.format("%.2f", armado.precio)
        tvDescuentoArmado.text = "Descuento: " + descuento.toInt().toString() + "%"
        tvPrecioFinalArmado.text = "Precio final: " + String.format("%.2f", (precio - ((descuento / 100) * precio)))
        tvDescripcionArmado.text = descripcion
    }

    private fun validarSesionParaCompra(id: Int) {
        val sharedPreferences = getSharedPreferences("slapc.prefs", Context.MODE_PRIVATE)
        val sesionIniciada = sharedPreferences.getBoolean("sesion_iniciada", false)

        if (sesionIniciada) {
            Carrito.agregarItem(ArmadoEnCarrito(id, 1))
        }

        val intent = Intent()
        intent.putExtra("agregado_al_carrito", sesionIniciada)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}