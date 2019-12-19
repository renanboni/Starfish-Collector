package com.boni.libgdx

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color

class StoryScreen : BaseScreen() {

    lateinit var scene: Scene
    lateinit var continueKey: BaseActor

    override fun initialize() {
        setBackground()
        setTurtle()
        setDialogBoxAndKey()
    }

    private fun setDialogBoxAndKey() {
        val dialogBox = DialogBox(0f, 0f, mainStage).also {
            it.setDialogSize(600f, 200f)
            it.setBackgroundColor(Color(0.6f, 0.6f, 0.8f, 1f))
            it.setFontScale(0.75f)
            it.isVisible = false
        }

        uiTable.add(dialogBox).expandX().expandY().bottom()

        continueKey = BaseActor(stage = mainStage).also {
            it.loadTexture("key-C.png")
            it.setSize(32f, 32f)
            it.isVisible = false
        }

        dialogBox.addActor(continueKey)
        continueKey.setPosition(dialogBox.width - continueKey.width, 0f)
    }

    private fun setTurtle() {
        BaseActor(stage = mainStage).also {
            it.loadTexture("turtle-big.png")
            it.setPosition(-it.width, 0f)
        }
    }

    private fun setBackground() {
        val background = BaseActor(stage = mainStage).also {
            it.loadTexture("oceanside.png")
            it.setSize(800f, 600f)
            it.setOpacity(0f)
        }
        BaseActor.setWorldBounds(background)
    }

    override fun update(dt: Float) {

    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.C && continueKey.isVisible) {
            scene.loadNextSegment()
        }
        return false
    }
}