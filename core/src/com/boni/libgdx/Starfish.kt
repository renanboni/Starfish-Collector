package com.boni.libgdx

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Starfish(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {

    private var collected: Boolean = false

    init {
        loadTexture("starfish.png")

        val spin = Actions.rotateBy(30f, 1f)
        addAction(Actions.forever(spin))

        setBoundaryPolygon(8)
    }

    fun isCollected() = collected

    fun collect() {
        collected = true
        clearActions()
        addAction(Actions.fadeOut(1f))
        addAction(Actions.after(Actions.removeActor()))
    }
}