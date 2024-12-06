package com.example.slapc.ui.pedidos
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R

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
            val componentesConCantidad = pedido.detallesItems.mapNotNull { componente ->
                try {
                    val (nombre, cantidad) = componente.split(" x ")
                    componentePedido(nombre, cantidad.toInt())
                } catch (e: Exception) {
                    Log.e("DetallesPedidoActivity", "Error al procesar componente: $componente", e)
                    null
                }
            }

            recyclerViewProductos.adapter = ComponentePedidoAdapter(componentesConCantidad)

            textDireccion.text = "Entrega en: ${pedido.fechaEntrega} a las ${pedido.horaEntrega}"
            textTotalPagar.text = "Total a pagar: $${pedido.total}"
        }


        buttonSalir.setOnClickListener {
            finish()
        }
    }

}
