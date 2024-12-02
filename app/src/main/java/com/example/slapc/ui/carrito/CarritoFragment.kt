package com.example.slapc.ui.carrito

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slapc.CategoriaComponente
import com.example.slapc.Componente
import com.example.slapc.RepositorioComponentes
import com.example.slapc.databinding.FragmentCarritoBinding
import com.example.slapc.ui.carrito.adaptador.ItemEnCarritoAdaptador

class CarritoFragment : Fragment() {

    private var _binding: FragmentCarritoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarritoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        RepositorioComponentes.agregarComponente(Componente(
            "supermouse",
            "mouse.jpg",
            99.99,
            CategoriaComponente.MOUSE,
            "Es un gran mouse"
        ))

        val itemsCarrito = listOf<ItemEnCarrito>(
            ComponenteEnCarrito(0, 5),
            ArmadoEnCarrito(0, 3)
        )

        val recyclerView = binding.rclCarritoItems
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = ItemEnCarritoAdaptador(itemsCarrito)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}