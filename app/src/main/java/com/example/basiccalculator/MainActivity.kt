package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    private val result by lazy(LazyThreadSafetyMode.NONE) { findViewById<EditText>(R.id.result) }
    private val newNumber by lazy(LazyThreadSafetyMode.NONE) { findViewById<EditText>(R.id.newNumber) }
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    // variables to hold the operands and type of calculation
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // data input buttons
        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val buttonDot = findViewById<Button>(R.id.buttonDot)

        // operations buttons
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonPlus = findViewById<Button>(R.id.buttonPlus)

        val listener = View.OnClickListener {
            val clickedButton = it as Button
            newNumber.text.append(clickedButton.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opLlistener = View.OnClickListener {
            val clickedOperation = (it as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, clickedOperation)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = clickedOperation
            displayOperation.text = pendingOperation
        }

        buttonDivide.setOnClickListener(opLlistener)
        buttonEquals.setOnClickListener(opLlistener)
        buttonMinus.setOnClickListener(opLlistener)
        buttonMultiply.setOnClickListener(opLlistener)
        buttonPlus.setOnClickListener(opLlistener)

    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) { Double.NaN } else { operand1!! / value }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
            result.setText(operand1.toString())
            newNumber.setText("")
        }
    }
}