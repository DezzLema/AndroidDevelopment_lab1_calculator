package com.example.lab1

import android.content.Intent
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

        tvResult.text = getString(R.string.result_format, result)
        tvCalculation.text = "$num1 $operationSymbol $num2 = $result"
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}