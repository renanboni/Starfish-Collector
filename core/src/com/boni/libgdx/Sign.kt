package com.boni.libgdx

import com.badlogic.gdx.scenes.scene2d.Stage

class Sign(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {

    private var text: String = ""
    private var viewing: Boolean = false

    init {
        loadTexture("sign.png")
    }

    fun setText(text: String) {
        this.text = text
    }

    fun getText() = text

    fun setViewing(viewing: Boolean) {
        this.viewing = viewing
    }

    fun isViewing() = viewing
}