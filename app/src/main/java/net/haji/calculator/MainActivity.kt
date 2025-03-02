package net.haji.calculator

import android.annotation.SuppressLint
import android.content.Intent
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

class MainActivity : AppCompatActivity() {
    private lateinit var calculatingZone : TextView;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val deleteAllBtn = findViewById<Button>(R.id.deletAllBtn);
        val openParenthesisBtn = findViewById<Button>(R.id.openParenthesisBtn);
        val closeParenthesisBtn = findViewById<Button>(R.id.closeParenthesisBtn);
        val divisionBtn = findViewById<Button>(R.id.divisionBtn);
        val num7Btn = findViewById<Button>(R.id.num7Btn);
        val num8Btn = findViewById<Button>(R.id.num8Btn);
        val num9Btn = findViewById<Button>(R.id.num9Btn);
        val multiplicationBtn = findViewById<Button>(R.id.multiplicationBtn);
        val num4Btn = findViewById<Button>(R.id.num4Btn);
        val num5Btn = findViewById<Button>(R.id.num5Btn);
        val num6Btn = findViewById<Button>(R.id.num6Btn);
        val subtractionBtn = findViewById<Button>(R.id.subtractionBtn);
        val num1Btn = findViewById<Button>(R.id.num1Btn);
        val num2Btn = findViewById<Button>(R.id.num2Btn);
        val num3Btn = findViewById<Button>(R.id.num3Btn);
        val additionBtn = findViewById<Button>(R.id.additionBtn);
        val num0Btn = findViewById<Button>(R.id.num0Btn);
        val commaBtn = findViewById<Button>(R.id.commaBtn);
        val deleteBtn = findViewById<Button>(R.id.deleteBtn);
        val equalBtn = findViewById<Button>(R.id.equalBtn);

        calculatingZone = findViewById<TextView>(R.id.calculatingZone)
        calculatingZone.text = intent.getStringExtra("currentLandscapeCalculatingZone")

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

        deleteBtn.setOnClickListener {
            val currentText = calculatingZone.text.toString()
            if (currentText.isNotEmpty()) {
                // Remove the last character
                calculatingZone.setText(currentText.dropLast(1))
            }
        }

        equalBtn.setOnClickListener {
            val expression = calculatingZone.text
            try {
                var result = ExpressionBuilder(expression.toString()).build().evaluate()
                calculatingZone.setText(result.toString())
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).postDelayed({
                    calculatingZone.setText("")
                }, 700)
                calculatingZone.setText("NaN")
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Start landscape activity
            val intent = Intent(this, LandscapeCalculatorMode::class.java)
            intent.putExtra("currentPortraitCalculatingZone", calculatingZone.text.toString())
            startActivity(intent)
        }
    }


}