package net.haji.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
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

        val calculatingZone = findViewById<EditText>(R.id.calculatingZone);

        deleteBtn.setOnClickListener(v -> {

        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}