package com.teamvanar.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var nicknameEdit: EditText
    private lateinit var nicknameText: TextView
    private lateinit var doneButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doneButton = findViewById(R.id.done_button)
        nicknameEdit = findViewById(R.id.nickname_edit)
        nicknameText = findViewById(R.id.nickname_text)

        doneButton.setOnClickListener {
            addNickname()
        }

        nicknameText.setOnClickListener {
            updateNickname()
        }
    }

    private fun addNickname() {
        nicknameText.text = nicknameEdit.text

        nicknameText.visibility = View.VISIBLE
        nicknameEdit.visibility = View.GONE
        doneButton.visibility = View.GONE
    }

    private fun updateNickname() {
        nicknameEdit.visibility = View.VISIBLE
        nicknameEdit.requestFocus()

        doneButton.visibility = View.VISIBLE
        nicknameText.visibility = View.GONE

        // Show the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(nicknameEdit, 0)
    }
}