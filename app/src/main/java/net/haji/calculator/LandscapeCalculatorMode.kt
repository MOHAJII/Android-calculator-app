package net.haji.calculator

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.exp

class LandscapeCalculatorMode : AppCompatActivity() {
    private lateinit var calculatingZone : TextView;


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_paysage_calculator_mode)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Our work
        val radianBtn = findViewById<Button>(R.id.radianBtnLs);
        val racineBtn = findViewById<Button>(R.id.racineBtnLs);
        val piBtn = findViewById<Button>(R.id.piBtnLs);
        val percentageBtn = findViewById<Button>(R.id.percentageBtnLs);
        val powerBtn = findViewById<Button>(R.id.powerBtnLs);
        val factorialBtn = findViewById<Button>(R.id.factorialBtnLs);
        val sinBtn = findViewById<Button>(R.id.sinBtnLs);
        val cosBtn = findViewById<Button>(R.id.cosBtnLs);
        val tanBtn = findViewById<Button>(R.id.tanBtnLs);
        val exponentialBtn = findViewById<Button>(R.id.exponentialBtnLs);
        val lnBtn = findViewById<Button>(R.id.lnBtnLs);
        val logBtn = findViewById<Button>(R.id.logBtnLs);
        val deleteAllBtn = findViewById<Button>(R.id.deletAllBtnLs)
        val openParenthesisBtn = findViewById<Button>(R.id.openParenthesisBtnLs)
        val closeParenthesisBtn = findViewById<Button>(R.id.closeParenthesisBtnLs)
        val divisionBtn = findViewById<Button>(R.id.divisionBtnLs)
        val num7Btn = findViewById<Button>(R.id.num7BtnLs)
        val num8Btn = findViewById<Button>(R.id.num8BtnLs)
        val num9Btn = findViewById<Button>(R.id.num9BtnLs)
        val multiplicationBtn = findViewById<Button>(R.id.multiplicationBtnLs)
        val num4Btn = findViewById<Button>(R.id.num4BtnLs)
        val num5Btn = findViewById<Button>(R.id.num5BtnLs)
        val num6Btn = findViewById<Button>(R.id.num6BtnLs)
        val subtractionBtn = findViewById<Button>(R.id.subtractionBtnLs)
        val num1Btn = findViewById<Button>(R.id.num1BtnLs)
        val num2Btn = findViewById<Button>(R.id.num2BtnLs)
        val num3Btn = findViewById<Button>(R.id.num3BtnLs)
        val additionBtn = findViewById<Button>(R.id.additionBtnLs)
        val num0Btn = findViewById<Button>(R.id.num0BtnLs)
        val commaBtn = findViewById<Button>(R.id.commaBtnLs)
        val deleteBtn = findViewById<Button>(R.id.deleteBtnLs)
        val equalBtn = findViewById<Button>(R.id.equalBtnLs)

        var mode = "RAD"



        calculatingZone = findViewById(R.id.calculatingZoneLs)
        calculatingZone.text = intent.getStringExtra("currentPortraitCalculatingZone")

        deleteAllBtn.setOnClickListener {
            calculatingZone.setText("")
        }

        openParenthesisBtn.setOnClickListener {
            calculatingZone.append("(")
        }

        closeParenthesisBtn.setOnClickListener {
            calculatingZone.append(")")
        }

        divisionBtn.setOnClickListener {
            calculatingZone.append("/")
        }

        num7Btn.setOnClickListener {
            calculatingZone.append("7")
        }

        num8Btn.setOnClickListener {
            calculatingZone.append("8")
        }

        num9Btn.setOnClickListener {
            calculatingZone.append("9")
        }

        multiplicationBtn.setOnClickListener {
            calculatingZone.append("*")
        }

        num4Btn.setOnClickListener {
            calculatingZone.append("4")
        }

        num5Btn.setOnClickListener {
            calculatingZone.append("5")
        }

        num6Btn.setOnClickListener {
            calculatingZone.append("6")
        }

        subtractionBtn.setOnClickListener {
            calculatingZone.append("-")
        }

        num1Btn.setOnClickListener {
            calculatingZone.append("1")
        }

        num2Btn.setOnClickListener {
            calculatingZone.append("2")
        }

        num3Btn.setOnClickListener {
            calculatingZone.append("3")
        }

        additionBtn.setOnClickListener {
            calculatingZone.append("+")
        }

        num0Btn.setOnClickListener {
            calculatingZone.append("0")
        }

        commaBtn.setOnClickListener {
            calculatingZone.append(".")
        }

        radianBtn.setOnClickListener {
            if (radianBtn.text == "RAD")
                radianBtn.text = "DEG"
            else
                radianBtn.text = "RAD"

            mode = radianBtn.text.toString()
        }

        racineBtn.setOnClickListener {
            calculatingZone.append("√")
        }

        piBtn.setOnClickListener {
            calculatingZone.append("π")
        }

        percentageBtn.setOnClickListener {
            calculatingZone.append("%")
        }

        powerBtn.setOnClickListener {
            calculatingZone.append("^")
        }

        factorialBtn.setOnClickListener {
            calculatingZone.append("!")
        }

        sinBtn.setOnClickListener {
            calculatingZone.append("sin(")
        }

        cosBtn.setOnClickListener {
            calculatingZone.append("cos(")
        }

        tanBtn.setOnClickListener {
            calculatingZone.append("tan(")
        }

        exponentialBtn.setOnClickListener {
            calculatingZone.append("e")
        }

        lnBtn.setOnClickListener {
            calculatingZone.append("ln(")
        }

        logBtn.setOnClickListener {
            calculatingZone.append("log(")
        }

        deleteBtn.setOnClickListener {
            val currentText = calculatingZone.text.toString()
            if (currentText.isNotEmpty()) {
                // Remove the last character
                calculatingZone.setText(currentText.dropLast(1))
            }
        }

        equalBtn.setOnClickListener {
            var expression = calculatingZone.text.toString()

            if (expression.isNotEmpty()) {
                try {
                    // Replace "√" with "sqrt(" for proper evaluation
                    expression = expression.replace("√", "sqrt")

                    // Handle RAD vs DEG mode
                    if (mode == "DEG") {
                        expression = convertDegreesToRadians(expression)
                    }

                    val result = ExpressionBuilder(expression).build().evaluate()

                    // Check if result has decimals
                    val formattedResult = if (result % 1 == 0.0) {
                        result.toLong().toString()  // Whole number, display as integer
                    } else {
                        result.toString()  // Decimal, display as float
                    }

                    calculatingZone.setText(formattedResult)
                } catch (e: Exception) {
                    calculatingZone.setText("Error")

                    // Clear the error message after a short delay
                    Handler(Looper.getMainLooper()).postDelayed({
                        calculatingZone.setText("")
                    }, 1000)
                }
            }
        }



    }

    fun convertDegreesToRadians(expression: String): String {
        val regex = """(sin|cos|tan)\(([-]?\d+(\.\d+)?)\)""".toRegex()

        return regex.replace(expression) { matchResult ->
            val function = matchResult.groupValues[1] // sin, cos, tan
            val angleInDegrees = matchResult.groupValues[2].toDouble()
            val angleInRadians = Math.toRadians(angleInDegrees)

            "$function($angleInRadians)"
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            intent.putExtra("currentLandscapeCalculatingZone", calculatingZone.text.toString())
            finish()
        }
    }
}