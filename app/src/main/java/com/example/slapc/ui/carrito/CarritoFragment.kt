package com.example.slapc.ui.carrito

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slapc.R
import com.example.slapc.databinding.FragmentCarritoBinding
import com.example.slapc.ui.carrito.adaptador.ItemEnCarritoAdaptador
import com.google.android.material.button.MaterialButton

class CarritoFragment : Fragment() {

    private var _binding: FragmentCarritoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var txtSubtotal: TextView
    private lateinit var txtGarantias: TextView
    private lateinit var txtIVA: TextView
    private lateinit var txtTotal: TextView
    private lateinit var btnComprar: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarritoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obtención de componentes gráficos
        txtSubtotal = binding.txtCarritoSubtotal
        txtGarantias = binding.txtCarritoGarantias
        txtIVA = binding.txtCarritoIVA
        txtTotal = binding.txtCarritoTotal
        btnComprar = binding.btnCarritoComprar as MaterialButton

        // Muestra inicial de items de carrito.
        val recyclerView = binding.rclCarritoItems
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        val adapter = ItemEnCarritoAdaptador(Carrito.obtenerItems(), {itemNum, callbackRedibujo -> mostrarDialogoDeEdicionDeCantidad(itemNum, callbackRedibujo)})
        recyclerView.adapter = adapter

        // Muestra inicial de valores acumulados.
        actualizarAcumulados()

        // Observer para cambios en items de carrito por eliminación.
        Carrito.agregarCallbackDeEliminacion { adapter.actualizarListaItems() }

        // Observer para recalculación de valores acumulados del carrito.
        Carrito.agregarCallbackDeRecalculos { actualizarAcumulados() }

        btnComprar.setOnClickListener { comprarCarrito() }

        return root
    }

    private fun actualizarAcumulados() {
        txtSubtotal.text = "Subtotal: $${String.format("%.2f", Carrito.subtotal)}"
        txtGarantias.text = "Garantías: $${String.format("%.2f", Carrito.costoGarantias)}"
        txtIVA.text = "IVA: $${String.format("%.2f", Carrito.iva)}"
        txtTotal.text = "Total: $${String.format("%.2f", Carrito.total)}"
    }

    private fun comprarCarrito() {
        if(!Carrito.estaVacio()) {
            Carrito.comprar()
            Toast.makeText(context, "Pedido generado con el carrito", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Agregue items al carrito para poder comprarlos", Toast.LENGTH_SHORT).show()
        }
    }

    fun mostrarDialogoDeEdicionDeCantidad(itemNum: Int, callbackRedibujo: (nuevoModelo: ItemEnCarrito) -> Unit) {
        // Obtener objetos de creación de dialogo
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater

        // Declarar vistas de dialogo
        val dialogView = inflater.inflate(R.layout.dialog_editar_cantidad_carrito, null)
        val edtCantidad: EditText = dialogView.findViewById(R.id.edtCarritoCantidadEditada)

        // Construir dialogo
        val dialog = builder.setView(dialogView)
            .setPositiveButton(R.string.aceptar, null)
            .setNegativeButton(R.string.cancelar, null)
            .create()

        // Configurar listeners de botones evitando comportamiento por defecto.
        dialog.setOnShowListener { dialogInterface -> run {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            // Configurar botón Aceptar con validación de la cantidad.
            positiveButton.setOnClickListener {
                if(edtCantidad.text.isEmpty() || edtCantidad.text.isBlank()) {
                    Toast.makeText(context, "Ingrese una cantidad para actualizar", Toast.LENGTH_SHORT).show()
                }
                else {
                    val cantidad = edtCantidad.text.toString().toInt()

                    if (cantidad == 0) {
                        Toast.makeText(context, "Ingrese una cantidad mayor a 0 para actualizar", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val nuevoModelo = Carrito.cambiarCantidadDeItem(itemNum, edtCantidad.text.toString().toInt())
                        callbackRedibujo(nuevoModelo)
                        dialog.dismiss()
                    }
                }
            }

            negativeButton.setOnClickListener {
                dialog.cancel()
            }
        }}

        // Mostrar dialogo
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Carrito.quitarCallbackDeEliminacion()
        Carrito.quitarCallbackDeRecalculos()
    }
}