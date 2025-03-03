package net.haji.calculator

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var calculatingZone : EditText;
    private val PREFS_NAME = "HistoryPrefs"
    private val HISTORY_KEY = "history_list"
    private lateinit var historyList: ArrayList<String>
    private lateinit var historySpinner: Spinner

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        historySpinner = findViewById(R.id.historySpinner)

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

        calculatingZone = findViewById(R.id.calculatingZone)
        calculatingZone.setText(intent.getStringExtra("currentLandscapeCalculatingZone"))

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

        historyList = getHistoryList()

        // Create display list with hint
        val displayList = ArrayList<String>()
        displayList.add("Select from history")
        displayList.addAll(historyList)

        // Set up the spinner with the history list
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, displayList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        historySpinner.adapter = adapter

        // Set spinner item selection listener
        historySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position > 0) { // Skip the hint item
                    // Get the selected history item
                    val selectedItem = historyList[position - 1]
                    val expression = selectedItem.split("=");

                    // Apply the selected item to the input field
                    calculatingZone.setText(expression[0])

                    // Optional: Move cursor to the end of the text
                    calculatingZone.setSelection(expression[0].length)

                    // Optional: Show toast confirmation
                    Toast.makeText(this@MainActivity, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
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
                val result = ExpressionBuilder(expression.toString()).build().evaluate()
                calculatingZone.setText(result.toString())
                val newItem = expression.toString() + " = " + result.toString()
                if (newItem != "Error") {
                    addItemToHistory(newItem)
                    // Update the spinner
                    // Update the spinner
                    displayList.clear()
                    displayList.add("Select from history")
                    displayList.addAll(historyList)
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                calculatingZone.setText("Error")

                // Clear the error message after a short delay
                Handler(Looper.getMainLooper()).postDelayed({
                    calculatingZone.setText("")
                }, 1000)
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

    // Get history list from SharedPreferences
    private fun getHistoryList(): ArrayList<String> {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val historyJson = prefs.getString(HISTORY_KEY, null)

        return if (historyJson != null) {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<String>>() {}.type
            gson.fromJson(historyJson, type)
        } else {
            ArrayList()
        }
    }

    // Add a new item to history list and save to SharedPreferences
    private fun addItemToHistory(item: String) {
        // Add to beginning of list (most recent first)
        historyList.add(0, item)

        // Optional: Limit list size
        if (historyList.size > 10) {
            historyList.removeAt(historyList.size - 1)
        }

        // Save updated list
        saveHistoryList()
    }

    // Save history list to SharedPreferences
    private fun saveHistoryList() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = prefs.edit()

        val gson = Gson()
        val historyJson = gson.toJson(historyList)

        editor.putString(HISTORY_KEY, historyJson)
        editor.apply()
    }


}