package com.example.mp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class List : AppCompatActivity() {

    private lateinit var voltarButton: Button
    private lateinit var resultContainer: LinearLayout
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_todos)

        voltarButton = findViewById(R.id.voltarButton)
        resultContainer = findViewById(R.id.resultContainer)

        voltarButton.setOnClickListener { finish() }

        carregarTodosOsDocumentos()
    }

    private fun carregarTodosOsDocumentos() {
        db.collection("umidadeTemperatura")
            .orderBy("dataHora")
            .get()
            .addOnSuccessListener { querySnapshot ->
                resultContainer.removeAllViews() // Limpa os resultados anteriores
                querySnapshot.documents.forEach { document ->
                    // Obter os dados do Firestore
                    val dataHora = document.getTimestamp("dataHora")?.toDate()?.let {
                        val dateFormat = java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss", java.util.Locale.getDefault())
                        dateFormat.format(it)
                    } ?: "Data indisponível"

                    val temperatura = document.getString("temperatura") ?: "N/A"
                    val umidade = document.getString("umidade") ?: "N/A"

                    // Criar e adicionar as visualizações ao container
                    adicionarResultado(dataHora, temperatura, umidade)
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace() // Lidar com o erro
            }
    }

    private fun adicionarResultado(dataHora: String, temperatura: String, umidade: String) {
        // Criar TextViews para os dados
        val dataHoraView = TextView(this).apply {
            text = "Data/Hora: $dataHora"
            textSize = 16f
        }
        val temperaturaView = TextView(this).apply {
            text = "Temperatura: $temperatura"
            textSize = 16f
        }
        val umidadeView = TextView(this).apply {
            text = "Umidade: $umidade"
            textSize = 16f
        }

        // Adicionar os TextViews ao container
        resultContainer.addView(dataHoraView)
        resultContainer.addView(temperaturaView)
        resultContainer.addView(umidadeView)

        // Adicionar uma linha de separação
        val separador = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2 // Altura da linha
            ).apply {
                setMargins(0, 16, 0, 16)
            }
            setBackgroundColor(getColor(android.R.color.darker_gray))
        }
        resultContainer.addView(separador)
    }
}
