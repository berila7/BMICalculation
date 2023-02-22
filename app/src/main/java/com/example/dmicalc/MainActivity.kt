package com.example.dmicalc

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var btnCalc: Button
    private lateinit var resultRange: TextView
    private lateinit var info: TextView
    private lateinit var resultIndex: TextView
    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        etWeight = findViewById(R.id.etWeight)
        etHeight = findViewById(R.id.etHeight)
        btnCalc = findViewById(R.id.CalcBtn)
        resultRange = findViewById(R.id.tvRange)
        resultIndex = findViewById(R.id.tvIndex)
        info = findViewById(R.id.tvInfo)

        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()

        btnCalc.setOnClickListener {
            val weight = etWeight.text.toString()
            val height = etHeight.text.toString()
            if (emptyField(weight, height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                val formatedRange = String.format("%.2f", bmi).toFloat()
                showResult(formatedRange)
            }
        }
    }
    private fun emptyField(weight: String?, height: String?): Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this@MainActivity, "Weight is Empty", Toast.LENGTH_SHORT).show()
                return false
            }
            height.isNullOrEmpty() -> {
                Toast.makeText(this@MainActivity, "Height is Empty", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showResult(bmi: Float) {
        val resultInfo = "Normal range is 18.5 - 24.9"

        resultIndex.text = bmi.toString()
        info.text = resultInfo

        var color = 0

        when {
            bmi < 16.0 -> {
                resultRange.text = "Underweight(Severe thinness)"
                color = R.color.underweight
            }
            bmi in 15.9..16.9 -> {
                resultRange.text = "Underweight(Moderate thinness)"
                color = R.color.underweight
            }
            bmi in 17.0..18.4 -> {
                resultRange.text = "Underweight(Mild thinness)"
                color = R.color.underweight
            }
            bmi in 18.5..24.9 -> {
                resultRange.text = "Normal range"
                color = R.color.normal_weight
            }
            bmi in 25.0..29.9 -> {
                resultRange.text = "Overweight (Pre-obese)"
                color = R.color.over_weight
            }
            bmi in 30.0..34.9 -> {
                resultRange.text = "Obese (Class I)"
                color = R.color.obese
            }
            bmi in 35.0..39.9 -> {
                resultRange.text = "Obese (Class II)"
                color = R.color.obese

            }
            bmi in 40.0..50.0 -> {
                resultRange.text = "Obese (Class III)"
                color = R.color.obese

            }
            else -> {
                resultRange.text = "Calm Down"
                color = R.color.obese
            }
        }
        resultRange.setTextColor(ContextCompat.getColor(this, color))
    }
}