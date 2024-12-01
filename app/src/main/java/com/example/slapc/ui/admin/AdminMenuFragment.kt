package com.example.slapc.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.slapc.ArmadosActivity
import com.example.slapc.CRUDComponentesActivity
import com.example.slapc.R
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
    }
}