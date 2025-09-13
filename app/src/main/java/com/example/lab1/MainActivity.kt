package com.example.lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private lateinit var btnClear: Button
    private var currentInput = "0"
    private var currentOperation = ""
    private var firstOperand = 0.0
    private var shouldResetInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupNumberButtons()
        setupOperationButtons()
        setupClearButton()
        setupEqualsButton()
        setupAdditionalButtons()
    }

    private fun initViews() {
        tvDisplay = findViewById(R.id.tvDisplay)
        btnClear = findViewById(R.id.btnClear)
    }

    private fun setupNumberButtons() {
        // Находим все кнопки с цифрами по их ID
        val numberButtons = listOf(
            findViewById<Button>(R.id.btn0),
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4),
            findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6),
            findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8),
            findViewById<Button>(R.id.btn9),
            findViewById<Button>(R.id.btnDot)
        )

        // Устанавливаем обработчики для всех кнопок
        numberButtons.forEach { button ->
            button?.setOnClickListener {
                val number = when (button.id) {
                    R.id.btnDot -> "."
                    else -> button.text.toString()
                }
                onNumberClicked(number)
            }
        }
    }

    private fun setupOperationButtons() {
        val operationButtons = mapOf(
            R.id.btnAdd to "+",
            R.id.btnSubtract to "-",
            R.id.btnMultiply to "×",
            R.id.btnDivide to "÷"
        )

        operationButtons.forEach { (buttonId, operation) ->
            findViewById<Button>(buttonId)?.setOnClickListener {
                onOperationClicked(operation)
            }
        }
    }

    private fun setupAdditionalButtons() {
        // Percent button
        findViewById<Button>(R.id.btnPercent)?.setOnClickListener {
            if (currentInput != "0") {
                val number = currentInput.toDouble() / 100
                currentInput = removeTrailingZeros(number.toString())
                updateDisplay()
            }
        }

        // Sign change button
        findViewById<Button>(R.id.btnSign)?.setOnClickListener {
            if (currentInput != "0") {
                val number = currentInput.toDouble() * -1
                currentInput = removeTrailingZeros(number.toString())
                updateDisplay()
            }
        }
    }

    private fun setupClearButton() {
        btnClear.setOnClickListener {
            currentInput = "0"
            currentOperation = ""
            firstOperand = 0.0
            shouldResetInput = false
            updateDisplay()
            btnClear.text = "C"
        }
    }

    private fun setupEqualsButton() {
        findViewById<Button>(R.id.btnEquals)?.setOnClickListener {
            calculateResult()
        }
    }

    private fun onNumberClicked(number: String) {
        if (shouldResetInput) {
            currentInput = "0"
            shouldResetInput = false
        }

        if (number == ".") {
            if (!currentInput.contains(".")) {
                currentInput += "."
            }
        } else {
            if (currentInput == "0") {
                currentInput = number
            } else {
                currentInput += number
            }
        }

        updateDisplay()
        btnClear.text = "C"
    }

    private fun onOperationClicked(operation: String) {
        if (currentOperation.isNotEmpty()) {
            calculateResult()
        }

        firstOperand = currentInput.toDouble()
        currentOperation = operation
        shouldResetInput = true
    }

    private fun calculateResult() {
        if (currentOperation.isEmpty()) return

        val secondOperand = currentInput.toDouble()
        val result = when (currentOperation) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "×" -> firstOperand * secondOperand
            "÷" -> {
                if (secondOperand == 0.0) {
                    Toast.makeText(this, "Деление на ноль невозможно", Toast.LENGTH_SHORT).show()
                    return
                }
                firstOperand / secondOperand
            }
            else -> 0.0
        }

        // Navigate to result activity
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("result", result)
            putExtra("num1", firstOperand)
            putExtra("num2", secondOperand)
            putExtra("operation", when (currentOperation) {
                "+" -> 0
                "-" -> 1
                "×" -> 2
                "÷" -> 3
                else -> 0
            })
        }
        startActivity(intent)

        // Reset for next calculation
        currentInput = removeTrailingZeros(result.toString())
        currentOperation = ""
        shouldResetInput = true
        updateDisplay()
    }

    private fun removeTrailingZeros(number: String): String {
        return if (number.contains(".")) {
            number.replace("\\.0+$", "").replace("(\\..*?)0+$", "$1")
        } else {
            number
        }
    }

    private fun updateDisplay() {
        tvDisplay.text = currentInput
    }
}