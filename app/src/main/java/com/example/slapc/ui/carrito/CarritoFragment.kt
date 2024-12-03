package com.example.slapc.ui.carrito

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slapc.CategoriaComponente
import com.example.slapc.Componente
import com.example.slapc.R
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

        // Codigo de testing
        RepositorioComponentes.agregarComponente(Componente(
            "supermouse",
            "mouse.jpg",
            99.99,
            CategoriaComponente.MOUSE,
            "Es un gran mouse"
        ))
        Carrito.agregarItem(ComponenteEnCarrito(0, 5))
        Carrito.agregarItem(ArmadoEnCarrito(0, 3))

        val recyclerView = binding.rclCarritoItems
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = ItemEnCarritoAdaptador(Carrito.obtenerItems(), { itemNum, callbackRedibujo -> mostrarDialogoDeEdicionDeCantidad(itemNum, callbackRedibujo)})

        return root
    }

    fun mostrarDialogoDeEdicionDeCantidad(itemNum: Int, callbackRedibujo: (nuevoModelo: ItemEnCarrito) -> Unit) {
        // Obtener objetos de creación de dialogo
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater

        // Declarar vistas de dialogo
        val dialogView = inflater.inflate(R.layout.dialog_editar_cantidad_carrito, null)
        val edtCantidad: EditText = dialogView.findViewById(R.id.edtCarritoCantidadEditada)

        // Construir dialogo
        builder.setView(dialogView)
        .setPositiveButton(R.string.aceptar, DialogInterface.OnClickListener {
            dialog, id -> run {
                val nuevoModelo = Carrito.cambiarCantidadDeItem(itemNum, edtCantidad.text.toString().toInt())
                callbackRedibujo(nuevoModelo)
                dialog.dismiss()
            }
        })
        .setNegativeButton(R.string.cancelar, DialogInterface.OnClickListener {
            dialog, id -> dialog.cancel()
        })

        // Mostrar dialogo
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}