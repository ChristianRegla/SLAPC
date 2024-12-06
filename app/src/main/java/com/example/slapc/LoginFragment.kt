package com.example.slapc

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var facebookImageView: ImageView
    private lateinit var youtubeImageView: ImageView

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
        facebookImageView = view.findViewById(R.id.facebookImageView)
        youtubeImageView = view.findViewById(R.id.youtubeImageView)


        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            iniciarSesion(username, password)
        }

        registerTextView.setOnClickListener {
            findNavController().navigate(R.id.nav_registro)
        }

        facebookImageView.setOnClickListener {
            val facebookIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100008376672634"))
            startActivity(facebookIntent)
        }

        youtubeImageView.setOnClickListener {
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@Slappy8"))
            startActivity(youtubeIntent)
        }
    }

    override fun onResume() {
        super.onResume()

        limpiarCampos()
    }

    private fun limpiarCampos() {
        usernameEditText.text.clear()
        passwordEditText.text.clear()
    }

    private fun iniciarSesion(nombreUsuario: String, contraseña: String) {
        val usuario = usuarios.find { it.nombre == nombreUsuario && it.contraseña == contraseña }
        if (usuario != null) {
            (activity as MainActivity).actualizarNavHeader(usuario.nombre, usuario.correo)
            if (usuario.esAdmin) {
                findNavController().navigate(R.id.nav_admin_menu)
            } else {
                (activity as MainActivity).actualizarMenuParaCliente()
                findNavController().navigate(R.id.nav_catalogo)
            }
        }
        else {
            Toast.makeText(requireContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }
}