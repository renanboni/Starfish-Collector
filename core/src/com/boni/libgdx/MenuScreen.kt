package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class MenuScreen: BaseScreen() {

    override fun initialize() {
        BaseActor(0f, 0f, mainStage).apply {
            loadTexture("water.jpg")
            setSize(800f, 600f)
        }

        BaseActor(0f, 0f, mainStage).apply {
            loadTexture("starfish-collector.png")
            centerAtPosition(400f, 300f)
            moveBy(0f, 100f)
        }

        BaseActor(0f, 0f, mainStage).apply {
            loadTexture("message-start.png")
            centerAtPosition(400f, 300f)
            moveBy(0f, -100f)
        }
    }

    override fun update(dt: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            BaseGame.setActiveScreen(LevelScreen())
        }
    }
}