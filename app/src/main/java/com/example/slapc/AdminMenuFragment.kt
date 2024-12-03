package com.example.slapc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class AdminMenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val agregarComponenteButton = view.findViewById<MaterialButton>(R.id.agregarComponenteButton)
        agregarComponenteButton.setOnClickListener {
            val intent = Intent(requireContext(), CRUDComponentesActivity::class.java)
            startActivity(intent)
        }

        val agregarArmadoButton = view.findViewById<MaterialButton>(R.id.agregarArmadoButton)
        agregarArmadoButton.setOnClickListener {
            val intent = Intent(requireContext(), ArmadosActivity::class.java)
            startActivity(intent)
        }

        val btnSalirAdmin = view.findViewById<MaterialButton>(R.id.btnSalirAdmin)
        btnSalirAdmin.setOnClickListener {
            // Se actualiza el nav header a usuario no registrado porque se cerró sesión
            (activity as MainActivity).actualizarNavHeader(null, null)
            // Se vuelve al login
            findNavController().navigate(R.id.nav_login)
        }
    }
}