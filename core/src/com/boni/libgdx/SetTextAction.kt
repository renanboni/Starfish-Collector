package com.boni.libgdx

import com.badlogic.gdx.scenes.scene2d.Action

class SetTextAction (private val textToDisplay: String) : Action() {
    override fun act(delta: Float): Boolean {
        val dialogBox = target as DialogBox
        dialogBox.setText(textToDisplay)
        return true
    }
}