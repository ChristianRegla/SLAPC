package com.example.slapc.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.slapc.R

class LoginFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView

    private val users = mapOf(
        "admin" to "admin123",
        "user1" to "userpass",
        "user2" to "password2"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)
        registerTextView = view.findViewById(R.id.registerTextView)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateLogin(username, password)) {
                if (username == "admin") {
                    // Si es admin pues se va directo al menú de los admin
                    findNavController().navigate(R.id.nav_admin_menu)
                } else {
                    // Si no es admin y las credenciales son correctas pues se intuye que es usuario
                    // y se dirije a la pantalla de los usuarios
                    findNavController().navigate(R.id.nav_gallery)
                }
            } else {
                Toast.makeText(requireContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        registerTextView.setOnClickListener {
            findNavController().navigate(R.id.nav_gallery)
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        val users = sharedPreferences.getStringSet("users", mutableSetOf()) ?: mutableSetOf()
        return users.any { it.split(":")[0] == username && it.split(":")[2] == password }
    }

}