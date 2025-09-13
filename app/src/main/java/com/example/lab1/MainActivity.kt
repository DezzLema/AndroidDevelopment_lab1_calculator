package com.example.lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var etNumber1: EditText
    private lateinit var etNumber2: EditText
    private lateinit var spinnerOperation: Spinner
    private lateinit var btnCalculate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupSpinner()
        setupListeners()
    }

    private fun initViews() {
        etNumber1 = findViewById(R.id.etNumber1)
        etNumber2 = findViewById(R.id.etNumber2)
        spinnerOperation = findViewById(R.id.spinnerOperation)
        btnCalculate = findViewById(R.id.btnCalculate)
    }

    private fun setupSpinner() {
        val operations = arrayOf(
            getString(R.string.operation_add),
            getString(R.string.operation_subtract),
            getString(R.string.operation_multiply),
            getString(R.string.operation_divide)
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            operations
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOperation.adapter = adapter
    }

    private fun setupListeners() {
        // Валидация ввода
        etNumber1.addTextChangedListener { validateInputs() }
        etNumber2.addTextChangedListener { validateInputs() }

        btnCalculate.setOnClickListener {
            if (validateInputs()) {
                calculateAndNavigate()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val num1 = etNumber1.text.toString()
        val num2 = etNumber2.text.toString()

        val isValid = num1.isNotEmpty() && num2.isNotEmpty() &&
                num1.toDoubleOrNull() != null && num2.toDoubleOrNull() != null

        btnCalculate.isEnabled = isValid
        return isValid
    }

    private fun calculateAndNavigate() {
        try {
            val num1 = etNumber1.text.toString().toDouble()
            val num2 = etNumber2.text.toString().toDouble()
            val operation = spinnerOperation.selectedItemPosition

            // Проверка деления на ноль
            if (operation == 3 && num2 == 0.0) {
                Toast.makeText(this, R.string.error_division_by_zero, Toast.LENGTH_SHORT).show()
                return
            }

            val result = when (operation) {
                0 -> num1 + num2
                1 -> num1 - num2
                2 -> num1 * num2
                3 -> num1 / num2
                else -> 0.0
            }

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("result", result)
                putExtra("num1", num1)
                putExtra("num2", num2)
                putExtra("operation", operation)
            }
            startActivity(intent)

        } catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.error_invalid_input, Toast.LENGTH_SHORT).show()
        }
    }
}