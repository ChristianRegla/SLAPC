package com.example.slapc

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class RegisterFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        val usernameEditText = view.findViewById<EditText>(R.id.username)
        val emailEditText = view.findViewById<EditText>(R.id.email)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val addressEditText = view.findViewById<EditText>(R.id.address)
        val deliveryTimeEditText = view.findViewById<EditText>(R.id.delivery_time)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val deliveryTime = deliveryTimeEditText.text.toString().trim()

            if(username.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || deliveryTime.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                registrarUsuario(username, email, password, address, deliveryTime)
            }
        }
    }

    private fun registrarUsuario(
        nombre: String,
        correo: String,
        contraseña: String,
        direccionEntrega: String,
        horarioEntrega: String
    ) {
        if(usuarios.any { it.correo == correo }) {
            Toast.makeText(requireContext(), "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
            return
        } else {
            val nuevoUsuario = Usuario(
                nombre = nombre,
                correo = correo,
                contraseña = contraseña,
                direccionEntrega = direccionEntrega,
                horarioEntrega = horarioEntrega,
                esAdmin = false
            )
            usuarios.add(nuevoUsuario)
            Toast.makeText(requireContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_login)
        }
    }
}