package com.example.trilhafacil


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val txtTitulo = findViewById<TextView>(R.id.textView2)
        val editNomeTrilha = findViewById<EditText>(R.id.text_input)
        val btnPlanejar = findViewById<Button>(R.id.button5)


        btnPlanejar.setOnClickListener{
            val nomeTrilha = editNomeTrilha.text.toString().trim()


            if(nomeTrilha.isEmpty()) {
                Toast.makeText(this, "Por Favor, digite o nome de uma trilha!", Toast.LENGTH_SHORT).show()
            }else {
                txtTitulo.text = "Proxima Aventura: $nomeTrilha"


                Toast.makeText(this, "Trilha $nomeTrilha planejada com sucesso!" , Toast.LENGTH_SHORT).show()


                editNomeTrilha.text.clear()
            }
        }
    }
}

