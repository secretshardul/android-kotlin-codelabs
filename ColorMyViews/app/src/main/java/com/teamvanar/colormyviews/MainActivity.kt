package com.teamvanar.colormyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val boxOneText = findViewById<TextView>(R.id.box_one_text)
        val boxTwoText = findViewById<TextView>(R.id.box_two_text)
        val boxThreeText = findViewById<TextView>(R.id.box_three_text)
        val boxFourText = findViewById<TextView>(R.id.box_four_text)
        val boxFiveText = findViewById<TextView>(R.id.box_five_text)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_layout)
        val buttonRed = findViewById<Button>(R.id.red_button)
        val buttonYellow = findViewById<Button>(R.id.yellow_button)
        val buttonGreen = findViewById<Button>(R.id.green_button)

        val clickableViews = listOf<View>(boxOneText, boxTwoText, boxThreeText, boxFourText, boxFiveText, constraintLayout, buttonRed, buttonYellow, buttonGreen)

        // Use loop to assign listeners
        for (view in clickableViews) {
            view.setOnClickListener {
                makeColored(view)
            }
        }

    }

    private fun makeColored(view: View) {
        when (view.id) {
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)
            R.id.box_three_text -> view.setBackgroundColor(Color.BLUE)
            R.id.box_four_text -> view.setBackgroundColor(Color.MAGENTA)
            R.id.box_five_text -> view.setBackgroundColor(Color.BLUE)
            R.id.red_button -> view.setBackgroundColor(Color.RED)
            R.id.yellow_button -> view.setBackgroundColor(Color.YELLOW)
            R.id.green_button -> view.setBackgroundColor(Color.GREEN)
            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }
}