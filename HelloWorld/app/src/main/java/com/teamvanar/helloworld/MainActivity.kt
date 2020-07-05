package com.teamvanar.helloworld

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    /**Lifecycle methods
     * Android doesn't have a main() method. Instead, lifecycle functions like onCreate() and
     * onDelete() are run one after the other.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**View objects
         * Android displays items using View objects created by the view class. Button, TextView,
         * ListView etc. are subclasses of View class. Many views form a hierarchy similar to HTML
         * DOM. Android runtime inflates XML files- strings, layouts etc. into view objects.
         */
        /**setContentView()
         * Allows activity to inflate layout
         */
        /**R file
         * Generated during build. It contains integer references to resources. It's used to access
         * assets under `/res`
         */
        setContentView(R.layout.activity_main) // Inflates layout
        val rollButton = findViewById<Button>(R.id.roll_button)
        // Generate and display random number between 1-6
        rollButton.setOnClickListener {
            rollDice()
        }

        val countUpButton = findViewById<Button>(R.id.count_up_button)
        // Increment value if it's numeric and less than 6
        countUpButton.setOnClickListener {
            countUp()
        }

        val resetButton = findViewById<Button>(R.id.reset_button)
        // Reset value to 0
        resetButton.setOnClickListener {
            val resultText = findViewById<TextView>(R.id.result_text)
            resultText.text = "0"
        }

    }

    private fun rollDice() {
        // Context object allows us to communicate with and update state of android OS
        // 'this' is passed because Activity and AppCompatActivity are subclasses of Context
        Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
        val resultText = findViewById<TextView>(R.id.result_text)
        resultText.text = (1..6).random().toString()
    }

    fun String.intOrString() = toIntOrNull() ?: this

    private fun countUp() {

        val resultText = findViewById<TextView>(R.id.result_text)
        var resultNumber = resultText.text.toString().toIntOrNull()
        if (resultNumber != null) {
            if (resultNumber < 6) {
                resultText.text = (++resultNumber).toString()
            }
        }
    }
}