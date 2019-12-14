package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table

abstract class BaseScreen: Screen, InputProcessor {

    protected var mainStage: Stage = Stage()
    protected var uiStage: Stage = Stage()
    protected var uiTable: Table = Table()

    abstract fun initialize()
    abstract fun update(dt: Float)

    init {
        initialize()

        uiTable.setFillParent(true)
        uiStage.addActor(uiTable)
    }

    override fun render(delta: Float) {
        uiStage.act(delta)
        mainStage.act(delta)

        update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        mainStage.draw()
        uiStage.draw()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

    }

    override fun hide() {
        (Gdx.input.inputProcessor as InputMultiplexer).also {
            it.removeProcessor(this)
            it.removeProcessor(uiStage)
            it.removeProcessor(mainStage)
        }
    }

    override fun show() {
        (Gdx.input.inputProcessor as InputMultiplexer).also {
            it.addProcessor(this)
            it.addProcessor(uiStage)
            it.addProcessor(mainStage)
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }
}