package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class Turtle: ActorBeta() {

    override fun act(delta: Float) {
        super.act(delta)

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveBy(-1f, 0f)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveBy(1f, 0f)
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveBy(0f, 1f)
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveBy(0f, -1f)
    }
}