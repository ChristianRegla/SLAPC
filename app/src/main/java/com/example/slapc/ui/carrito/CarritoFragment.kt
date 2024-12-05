package com.example.slapc.ui.carrito

import android.content.DialogInterface
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slapc.Armado
import com.example.slapc.CategoriaComponente
import com.example.slapc.Componente
import com.example.slapc.Pedido
import com.example.slapc.R
import com.example.slapc.RepositorioArmados
import com.example.slapc.RepositorioComponentes
import com.example.slapc.RepositorioPedidos
import com.example.slapc.databinding.FragmentCarritoBinding
import com.example.slapc.ui.carrito.adaptador.ItemEnCarritoAdaptador
import java.util.Calendar

class CarritoFragment : Fragment() {

    private var _binding: FragmentCarritoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var txtSubtotal: TextView
    private lateinit var txtGarantias: TextView
    private lateinit var txtIVA: TextView
    private lateinit var txtTotal: TextView
    private lateinit var btnComprar: Button

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
        btnComprar = binding.btnCarritoComprar

        // Codigo de testing
        RepositorioComponentes.agregarComponente(Componente(
            "supermouse",
            "facebook",
            99.99,
            CategoriaComponente.MOUSE,
            "Es un gran mouse"
        ))
        RepositorioArmados.agregarArmado(Armado(
            0,
            "armadoTest",
            20.0,
            "Gaming",
            "Un armado de pruebas",
            listOf(RepositorioComponentes.obtenerComponente(0)!!),
            0.0
        ))

        Carrito.agregarItem(ComponenteEnCarrito(0, 5))
        Carrito.agregarItem(ArmadoEnCarrito(0, 3))

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
        // Obtener datos preexistentes
        val nombresItems = Carrito.copiarNombresComponentes()
        val garantias = Carrito.copiarGarantias()
        val total = Carrito.total

        // Obtener datos de tiempo actuales
        val calendario = android.icu.util.Calendar.getInstance(TimeZone.getTimeZone("GMT-06:00"))
        val dia = dosCifrasDe(calendario.get(Calendar.DAY_OF_MONTH))
        val mes = dosCifrasDe(calendario.get(Calendar.MONTH) + 1)
        val anio = calendario.get(Calendar.YEAR)

        // Obtener datos de tiempo de entrega
        calendario.add(Calendar.DATE, 5)
        val diaEntrega = dosCifrasDe(calendario.get(Calendar.DAY_OF_MONTH))
        val mesEntrega = dosCifrasDe(calendario.get(Calendar.MONTH) + 1)
        val anioEntrega = dosCifrasDe(calendario.get(Calendar.YEAR))
        val horas = dosCifrasDe(calendario.get(Calendar.HOUR_OF_DAY))
        val minutos = dosCifrasDe(calendario.get(Calendar.MINUTE))
        val segundos = dosCifrasDe(calendario.get(Calendar.SECOND))

        // Crear strings informativas
        val identificador = "$anio$mes$dia$horas$minutos$segundos"
        val fechaCompra = "$dia/$mes/$anio"
        val horaEntrega = "$horas:$minutos"
        val fechaEntrega = "$diaEntrega/$mesEntrega/$anioEntrega"

        // Crear pedido
        RepositorioPedidos.agregarPedido(Pedido(
            identificador,
            fechaCompra,
            fechaEntrega,
            horaEntrega,
            nombresItems,
            total,
            garantias
        ))

        // Vaciar listado del carrito
        Carrito.reiniciar()

        // TODO: Cambiar navegación a la ventana de pedidos
        findNavController().navigate(R.id.nav_catalogo)
    }

    private fun dosCifrasDe(numero: Int): String {
        return String.format("%02d", numero)
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
        Carrito.quitarCallbackDeEliminacion()
        Carrito.quitarCallbackDeRecalculos()
    }
}