package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen : BaseScreen() {

    private lateinit var startButton: TextButton
    private lateinit var quitButton: TextButton
    private lateinit var title: BaseActor

    override fun initialize() {
        setupActors()
        setupStartButton()
        setupQuitButton()
        setupLayout()
    }

    private fun setupLayout() {
        uiTable.add(title).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton)
    }

    private fun setupActors() {
        BaseActor(stage = mainStage).apply {
            loadTexture("water.jpg")
            setSize(800f, 600f)
        }

        title = BaseActor(stage = mainStage).apply {
            loadTexture("starfish-collector.png")
            moveBy(0f, 100f)
        }
    }

    private fun setupStartButton() {
        startButton = TextButton("Start", BaseGame.textButtonStyle)
        uiStage.addActor(startButton)

        startButton.addListener {
            if (!(it is InputEvent) || it.type == InputEvent.Type.touchDown) {
                BaseGame.setActiveScreen(LevelScreen())
            }
            false
        }
    }

    private fun setupQuitButton() {
        quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        uiStage.addActor(quitButton)

        quitButton.addListener {
            if (!(it is InputEvent) || it.type == InputEvent.Type.touchDown) {
                Gdx.app.exit()
            }
            false
        }
    }

    override fun update(dt: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            BaseGame.setActiveScreen(LevelScreen())
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            BaseGame.setActiveScreen(LevelScreen())
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }

        return false
    }
}