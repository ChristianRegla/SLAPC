package com.example.slapc.ui.pedidos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R

class PedidosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)

        // Inicializar RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_pedidos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Obtener la lista de pedidos desde el repositorio
        val pedidos = RepositorioPedidos.obtenerPedidos()

        // Configurar el adaptador
        adapter = PedidoAdapter(pedidos) { pedido ->
            // Acción al hacer clic en "Ver detalles del pedido"
            val intent = Intent(requireContext(), DetallesPedidoActivity::class.java)
            intent.putExtra("pedido_id", pedido.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        return view
    }
}
