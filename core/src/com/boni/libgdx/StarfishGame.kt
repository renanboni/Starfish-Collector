package com.boni.libgdx

class StarfishGame: BaseGame() {
    override fun create() {
        setActiveScreen(MenuScreen())
    }
}