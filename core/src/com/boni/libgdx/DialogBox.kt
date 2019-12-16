package com.boni.libgdx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align

class DialogBox(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {

    private lateinit var dialogLabel: Label
    private var padding: Float = 16f

    init {
        loadTexture("dialog-translucent.png")

        dialogLabel = Label(" ", BaseGame.labelStyle)
        dialogLabel.setWrap(true)
        dialogLabel.setAlignment(Align.topLeft)
        dialogLabel.setPosition(padding, padding)
        this.setDialogSize(width, height)
        this.addActor(dialogLabel)
    }

    fun setDialogSize(width: Float, height: Float) {
        this.setSize(width, height)
        dialogLabel.width = width - 2 * padding
        dialogLabel.height = height - 2 * padding
    }

    fun setText(text: String) {
        dialogLabel.setText(text)
    }

    fun setFontScale(scale: Float) {
        dialogLabel.setFontScale(scale)
    }

    fun setFontColor(color: Color) {
        dialogLabel.setColor(color)
    }

    fun setBackgroundColor(color: Color) {
        this.color = color
    }

    fun alignTopLeft() {
        dialogLabel.setAlignment(Align.topLeft)
    }

    fun alignCenter() {
        dialogLabel.setAlignment(Align.center)
    }
}