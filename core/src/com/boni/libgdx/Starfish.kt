package com.boni.libgdx

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Starfish(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {

    init {
        loadTexture("starfish.png")

        val spin = Actions.rotateBy(30f, 1f)
        addAction(Actions.forever(spin))
    }
}