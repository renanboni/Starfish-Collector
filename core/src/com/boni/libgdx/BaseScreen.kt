package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage

abstract class BaseScreen: Screen {

    protected var mainStage: Stage = Stage()
    protected var uiStage: Stage = Stage()

    abstract fun initialize()
    abstract fun update(dt: Float)

    init {
        initialize()
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

    }

    override fun show() {

    }
}