package com.teamvanar.helloworld

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    /** lateinit
     * Tells compiler that variable will be initialized. It removes need of null checks.
     */
    private lateinit var diceImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Inflates layout

        // To improve performance call findViewById() only once and save value globally.
        diceImage = findViewById(R.id.dice_image)
        val rollButton = findViewById<Button>(R.id.roll_button)

        // Generate and display random number between 1-6
        rollButton.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {

        val drawableImage = when ((1..6).random()) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(drawableImage)
    }
}