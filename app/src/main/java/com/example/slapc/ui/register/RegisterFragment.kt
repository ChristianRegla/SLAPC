package com.example.slapc.ui.register

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
import com.example.slapc.R

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

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (addUser(username, email, password, address, deliveryTime)) {
                    Toast.makeText(requireContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.nav_login)
                } else {
                    Toast.makeText(requireContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUser(
        username: String,
        email: String,
        password: String,
        address: String,
        deliveryTime: String
    ): Boolean {
        val users = sharedPreferences.getStringSet("users", mutableSetOf()) ?: mutableSetOf()

        // Verifica si el usuario ya existe
        if (users.any { it.startsWith("$username:") }) {
            return false
        }

        // Agrega el nuevo usuario
        users.add("$username:$email:$password:$address:$deliveryTime")
        sharedPreferences.edit().putStringSet("users", users).apply()
        return true
    }
}