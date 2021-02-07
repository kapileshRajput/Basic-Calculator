package com.example.basiccalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND_1 = "Operand1"
private const val STATE_OPERAND_1_STORED = "Operand1_Stored"

class MainActivity : AppCompatActivity() {
    // variables to hold the operands and type of calculation
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            operation.text = pendingOperation
        }

        buttonDivide.setOnClickListener(opLlistener)
        buttonEquals.setOnClickListener(opLlistener)
        buttonMinus.setOnClickListener(opLlistener)
        buttonMultiply.setOnClickListener(opLlistener)
        buttonPlus.setOnClickListener(opLlistener)

        buttonNeg.setOnClickListener {
            val value = newNumber.text.toString()
            if (value.isEmpty()) {
                newNumber.setText("-")
            } else {
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= -1
                    newNumber.setText(doubleValue.toString())
                } catch (e: java.lang.NumberFormatException) {
                    // newNumber was "-" or ".", so clear it
                    newNumber.setText("")
                }
            }
        }

        buttonclr.setOnClickListener {
            operand1 = null
            pendingOperation = "="
            operation.text = pendingOperation
            result.setText("")
            newNumber.setText("")
        }

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
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if (savedInstanceState.getBoolean(STATE_OPERAND_1_STORED, false)) {
            savedInstanceState.getDouble(STATE_OPERAND_1)
        } else {
            null
        }
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION, "=")
        operation.text = pendingOperation
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND_1, operand1!!)
            outState.putBoolean(STATE_OPERAND_1_STORED, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }
}