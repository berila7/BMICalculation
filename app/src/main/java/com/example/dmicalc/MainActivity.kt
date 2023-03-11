package com.example.dmicalc

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dmicalc.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()

        binding.CalcBtn.setOnClickListener {
            val weight = binding.etWeight.text.toString()
            val height = binding.etHeight.text.toString()
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
                Snackbar.make(binding.root, "Weight is Empty", Snackbar.LENGTH_SHORT)
                    .setAnimationMode(ANIMATION_MODE_SLIDE)
                    .show()
                return false
            }
            height.isNullOrEmpty() -> {
                Snackbar.make(binding.root, "Height is Empty", Snackbar.LENGTH_SHORT)
                    .setAnimationMode(ANIMATION_MODE_SLIDE)
                    .show()
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

        binding.tvIndex.text = bmi.toString()
        binding.tvInfo.text = resultInfo

        val color: Int

        when {
            bmi < 16.0 -> {
                binding.tvRange.text = "Underweight(Severe thinness)"
                color = R.color.md_theme_light_error
            }
            bmi in 15.9..16.9 -> {
                binding.tvRange.text = "Underweight(Moderate thinness)"
                color = R.color.md_theme_light_error
            }
            bmi in 17.0..18.4 -> {
                binding.tvRange.text = "Underweight(Mild thinness)"
                color = R.color.md_theme_light_error
            }
            bmi in 18.5..24.9 -> {
                binding.tvRange.text = "Normal range"
                color = R.color.md_theme_light_primary
            }
            bmi in 25.0..29.9 -> {
                binding.tvRange.text = "Overweight (Pre-obese)"
                color = R.color.md_theme_light_error
            }
            bmi in 30.0..34.9 -> {
                binding.tvRange.text = "Obese (Class I)"
                color = R.color.md_theme_light_error
            }
            bmi in 35.0..39.9 -> {
                binding.tvRange.text = "Obese (Class II)"
                color = R.color.md_theme_light_error

            }
            bmi in 40.0..50.0 -> {
                binding.tvRange.text = "Obese (Class III)"
                color = R.color.md_theme_light_error

            }
            else -> {
                binding.tvRange.text = "Calm Down"
                color = R.color.md_theme_light_error
            }
        }
        binding.tvRange.setTextColor(ContextCompat.getColor(this, color))
    }
}