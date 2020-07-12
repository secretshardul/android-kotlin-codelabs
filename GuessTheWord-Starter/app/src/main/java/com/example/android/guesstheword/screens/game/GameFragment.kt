/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    // ViewModel
    private lateinit var viewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.i("GameFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }

        /** viewLifecycleOwner
         * Pass reference to the fragment view and not fragment itself. The both have different
         * lifecycles. Fragment views are destroyed when user navigates away, but not fragment itself.
         */
        viewModel.word.observe(viewLifecycleOwner, Observer {
            newWord -> binding.wordText.text = newWord
        })
        /**
         * Observers help in decoupling. We don't need to update UI manually when correct or skip
         * button is pressed. UI is automatically updated when word or score are updated.
         */
        viewModel.score.observe(viewLifecycleOwner, Observer {
            newScore -> binding.scoreText.text = newScore.toString()
        })
        return binding.root

    }

    /** Methods for buttons presses **/

    private fun onSkip() {
        viewModel.onSkip() // Reduces score, moves to next word
    }

    private fun onCorrect() {
        viewModel.onCorrect() // Increases score, moves to next word
    }

    private fun onEndGame() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val navigationAction = GameFragmentDirections.actionGameToScore()
        navigationAction.score = viewModel.score.value ?: 0
        NavHostFragment.findNavController(this).navigate(navigationAction)
    }
}
