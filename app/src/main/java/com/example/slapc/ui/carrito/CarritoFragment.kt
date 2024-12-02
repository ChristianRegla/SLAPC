package com.example.slapc.ui.carrito

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slapc.ItemEnCarrito
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

        val itemsCarrito = listOf(
            ItemEnCarrito(0, "Componente", 3, 300.0, false),
            ItemEnCarrito(0, "Componente", 3, 300.0, false)
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