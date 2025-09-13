package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvCalculation: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        initViews()
        displayResult()
        setupListeners()
    }

    private fun initViews() {
        tvResult = findViewById(R.id.tvResult)
        tvCalculation = findViewById(R.id.tvCalculation)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun displayResult() {
        val result = intent.getDoubleExtra("result", 0.0)
        val num1 = intent.getDoubleExtra("num1", 0.0)
        val num2 = intent.getDoubleExtra("num2", 0.0)
        val operationIndex = intent.getIntExtra("operation", 0)

        val operationSymbol = when (operationIndex) {
            0 -> "+"
            1 -> "-"
            2 -> "×"
            3 -> "÷"
            else -> "?"
        }

        tvResult.text = removeTrailingZeros(result.toString())
        tvCalculation.text = "${removeTrailingZeros(num1.toString())} $operationSymbol ${removeTrailingZeros(num2.toString())}"
    }

    private fun removeTrailingZeros(number: String): String {
        return if (number.contains(".")) {
            number.replace("\\.0+$", "").replace("(\\..*?)0+$", "$1")
        } else {
            number
        }
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}