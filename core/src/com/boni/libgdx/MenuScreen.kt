package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen : BaseScreen() {

    override fun initialize() {
        setupActors()
        setupStartButton()
        setupQuitButton()
    }

    private fun setupQuitButton() {
        val quitButton = TextButton("Quit", BaseGame.textButtonStyle).also {
            it.setPosition(500f, 150f)
        }
        uiStage.addActor(quitButton)

        quitButton.addListener {
            if (!(it is InputEvent) || it.type == InputEvent.Type.touchDown) {
                Gdx.app.exit()
            }
            false
        }
    }

    private fun setupActors() {
        BaseActor(0f, 0f, mainStage).apply {
            loadTexture("water.jpg")
            setSize(800f, 600f)
        }

        BaseActor(0f, 0f, mainStage).apply {
            loadTexture("starfish-collector.png")
            centerAtPosition(400f, 300f)
            moveBy(0f, 100f)
        }
    }

    private fun setupStartButton() {
        val startButton = TextButton("Start", BaseGame.textButtonStyle).also {
            it.setPosition(150f, 150f)
        }
        uiStage.addActor(startButton)

        startButton.addListener {
            if (!(it is InputEvent) || it.type == InputEvent.Type.touchDown) {
                BaseGame.setActiveScreen(LevelScreen())
            }
            false
        }
    }

    override fun update(dt: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            BaseGame.setActiveScreen(LevelScreen())
        }
    }
}