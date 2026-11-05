package com.imartinez.segonlayout

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Guardem si ja hem mostrat un resultat per començar de nou en prémer un número o operador
        var calculAcabat: Boolean = false

        // Agafem els botons de l'XML amb findViewById per donar-los una acció
        // Fem servir val perquè sempre apuntem als mateixos botons, encara que en canviem el text o l'estat
        val boto1 = findViewById<Button>(R.id.boto1)
        val boto2 = findViewById<Button>(R.id.boto2)
        val boto3 = findViewById<Button>(R.id.boto3)
        val boto4 = findViewById<Button>(R.id.boto4)
        val boto5 = findViewById<Button>(R.id.boto5)
        val boto6 = findViewById<Button>(R.id.boto6)
        val boto7 = findViewById<Button>(R.id.boto7)
        val boto8 = findViewById<Button>(R.id.boto8)
        val boto9 = findViewById<Button>(R.id.boto9)
        val boto0 = findViewById<Button>(R.id.boto0)
        val botoSuma = findViewById<Button>(R.id.botoSuma)
        val botoResta = findViewById<Button>(R.id.botoResta)
        val botoMultiplicar = findViewById<Button>(R.id.botoMultiplicar)
        val botoDividir = findViewById<Button>(R.id.botoDividir)
        val botoIgual = findViewById<Button>(R.id.botoIgual)
        val botoC = findViewById<Button>(R.id.botoC)

        // Agafem les dues pantalles, una per a l'operació i l'altra per al resultat
        val screen = findViewById<TextView>(R.id.screen)
        val screenResultat = findViewById<TextView>(R.id.screenResultat)

        // Quan premem igual, calculem l'operació que hi ha a la pantalla
        botoIgual.setOnClickListener {
            // Passem el text de la pantalla a String
            val text = screen.text.toString()

            // Si la pantalla està buida o només té espais, avisem i no calculem
            if (text.isBlank()) {
                Snackbar.make(botoIgual, R.string.emptyscreen_error, LENGTH_SHORT).show()
            } else {
                try {
                    val expression = ExpressionBuilder(text).build()

                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    if (result == longResult.toDouble()) {
                        screenResultat.text = longResult.toString()
                    } else {
                        screenResultat.text = result.toString()
                    }

                    // Ja hem mostrat el resultat, així que marquem el càlcul com a acabat
                    calculAcabat = true

                } catch (error: IllegalArgumentException) {
                    // Si l'operació no és vàlida, avisem, buidem les pantalles i comencem de nou
                    Snackbar.make(botoIgual, R.string.invalidexpression_error, LENGTH_SHORT).show()
                    screen.text = ""
                    screenResultat.text = ""
                    calculAcabat = false
                } catch (error: ArithmeticException) {
                    // Si hi ha un error de càlcul, com dividir per zero, avisem i ho deixem tot a punt per començar de nou
                    Snackbar.make(botoIgual, R.string.arithmetic_error, LENGTH_SHORT).show()
                    screen.text = ""
                    screenResultat.text = ""
                    calculAcabat = false
                }
            }
        }
        // Amb el botó C esborrem l'operació i el resultat i comencem de nou
        botoC.setOnClickListener {
            screen.text = ""
            screenResultat.text = ""
            calculAcabat = false
        }

        // Posem en una llista els botons que afegeixen números i operadors
        // Deixem C i igual fora de la llista perquè fan coses diferents
        val llistaBotons: List<Button> = listOf(
            boto1,
            boto2,
            boto3,
            boto4,
            boto5,
            boto6,
            boto7,
            boto8,
            boto9,
            boto0,
            botoSuma,
            botoResta,
            botoMultiplicar,
            botoDividir
        )
        // Recorrem la llista i posem un listener a cada botó
        llistaBotons.forEach { boto ->
            boto.setOnClickListener {
                // Si ja tenim un resultat, esborrem l'operació anterior abans d'afegir el número o operador
                if (calculAcabat) {
                    screen.text = ""
                    calculAcabat = false
                }
                // Afegim el text del botó al final del que ja hi ha a la pantalla
                screen.append(boto.text)
                // Esborrem el resultat anterior perquè hem canviat l'operació
                screenResultat.text = ""
            }
        }
    }
}
