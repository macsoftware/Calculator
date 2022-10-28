package uk.co.macsoftware.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var textViewFirstNumber: TextView? = null
    private var textViewOperator: TextView? = null
    private var textViewSecondNumber: TextView? = null

    private var onSecond: Boolean = false
    private var calculated: Boolean = false

    private fun init() {
        textViewFirstNumber = findViewById(R.id.textViewFirstNumber)
        textViewFirstNumber?.text = ""
        textViewOperator = findViewById(R.id.textViewOperator)
        textViewOperator?.text = ""
        textViewSecondNumber = findViewById(R.id.textViewSecondNumber)
        textViewSecondNumber?.text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun onDigit(view: View) {

        val digit = (view as Button).text
        if (calculated && !onSecond) {
            clearText()
        }

        if (onSecond) {
            textViewSecondNumber?.append(digit)
        } else {
            calculated = false
            textViewFirstNumber?.append(digit)
        }
    }

    fun onClear(view: View) {
        clearText()
        onSecond = false
    }

    private fun clearText() {
        textViewFirstNumber?.text = ""
        textViewOperator?.text = ""
        textViewSecondNumber?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (calculated && !onSecond) {
            clearText()
        }

        if (onSecond) {
            textViewSecondNumber.let {
                if (it?.text?.contains(getString(R.string.decimal_point)) == false)
                    textViewSecondNumber?.append(getString(R.string.decimal_point))
                else
                    Toast.makeText(
                        this,
                        getString(R.string.toast_already_point),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        } else {
            textViewFirstNumber.let {
                if (it?.text?.contains(getString(R.string.decimal_point)) == false)
                    it.append(".")
                else
                    Toast.makeText(
                        this,
                        getString(R.string.toast_already_point),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    fun onOperator(view: View) {
        if (calculated && !onSecond) {
            clearText()
            calculated = false
        }

        val operator = (view as Button).text

        if (!onSecond && textViewFirstNumber?.text?.length!! > 0) {
            textViewOperator?.text = operator
            onSecond = true
        } else if (!onSecond && textViewFirstNumber?.text?.length!! == 0 && operator == "-") {
            textViewFirstNumber?.text = operator
            onSecond = false
        } else if (!onSecond && textViewFirstNumber?.text?.length!! == 0 && operator != "-") {
            Toast.makeText(
                this,
                getString(R.string.toast_number_before_operator),
                Toast.LENGTH_SHORT
            ).show()
        } else if (onSecond && textViewSecondNumber?.text?.length!! == 0 && operator == "-") {
            textViewSecondNumber?.text = operator
        } else if (onSecond && textViewSecondNumber?.text?.length!! == 0 && operator != "-") {
            Toast.makeText(
                this,
                getString(R.string.toast_number_before_next),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                getString(R.string.toast_clear_before_start_again),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun onEquals(view: View) {
        if (calculated && !onSecond) {
            clearText()
        }

        if (onSecond && textViewSecondNumber?.text?.length!! > 0) {
            if (textViewSecondNumber?.text?.length!! == 1 && textViewSecondNumber?.text?.startsWith(
                    "-"
                )!!
            ) {
                Toast.makeText(
                    this,
                    getString(R.string.toast_numbers_for_calculation_to_work),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                try {
                    val first = textViewFirstNumber?.text.toString().toDouble()
                    val operator = textViewOperator?.text.toString()
                    val second = textViewSecondNumber?.text.toString().toDouble()
                    val result = when (operator) {
                        getString(R.string.add) -> first + second
                        getString(R.string.subtract) -> first - second
                        getString(R.string.multiply) -> first * second
                        getString(R.string.divide) -> first / second
                        else -> {
                            throw ArithmeticException(getString(R.string.error_invalid_operator))
                        }
                    }

                    textViewFirstNumber?.text = ""
                    textViewOperator?.text = ""
                    textViewSecondNumber?.text = removeZeroAfterDot(String.format("%.10f", result))
                    onSecond = false
                    calculated = true

                } catch (e: ArithmeticException) {
                    e.printStackTrace()
                    onClear(view)
                    calculated = false
                    Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(
                this,
                getString(R.string.toast_numbers_for_calculation_to_work),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result.trimEnd('0')
        if (value.endsWith('.'))
            value = value.trimEnd('.')
        return value
    }

}