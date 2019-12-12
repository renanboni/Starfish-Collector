package com.boni.libgdx

import com.badlogic.gdx.Game

abstract class BaseGame : Game() {

    init {
        game = this
    }

    companion object {
        private lateinit var game: BaseGame

        fun setActiveScreen(s: BaseScreen) {
            game.setScreen(s)
        }
    }
}