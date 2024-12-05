package com.example.slapc.ui.pedidos
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.Componente
import com.example.slapc.R
import com.example.slapc.ui.pedidos.RepositorioPedidos
import com.example.slapc.ui.catalogo.ProductoAdapter
import com.example.slapc.RepositorioComponentes

class DetallesPedidoActivity : AppCompatActivity() {

    private lateinit var recyclerViewProductos: RecyclerView
    private lateinit var textDireccion: TextView
    private lateinit var textTotalPagar: TextView
    private lateinit var buttonSalir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_pedido)

        recyclerViewProductos = findViewById(R.id.recycler_view_componentes)
        textDireccion = findViewById(R.id.text_direccion)
        textTotalPagar = findViewById(R.id.text_total_pagar)
        buttonSalir = findViewById(R.id.button_salir)

        recyclerViewProductos.layoutManager = LinearLayoutManager(this)

        val pedidoId = intent.getStringExtra("pedido_id")
        val pedido = RepositorioPedidos.buscarPedidoPorId(pedidoId ?: "")

        if (pedido != null) {
            val componentes = pedido.componentes.mapNotNull { idString ->
                val id = idString.toIntOrNull() ?: return@mapNotNull null
                RepositorioComponentes.obtenerComponente(id)
            }

            Log.d("DetallesPedidoActivity", "Componentes cargados: ${componentes.size}")
            recyclerViewProductos.adapter = ProductoAdapter(componentes)

            textDireccion.text = "Entrega en: ${pedido.fechaEntrega} a las ${pedido.horaEntrega}"
            textTotalPagar.text = "Total a pagar: $${pedido.total}"
        }

        buttonSalir.setOnClickListener {
            finish()
        }
    }
}