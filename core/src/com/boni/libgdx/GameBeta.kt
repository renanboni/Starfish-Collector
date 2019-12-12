package com.boni.libgdx

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage

abstract class GameBeta: Game() {

    protected lateinit var mainStage: Stage
    protected lateinit var uiStage: Stage

    override fun create() {
        mainStage = Stage()
        uiStage = Stage()
        initialize()
    }

    override fun render() {
        val dt = Gdx.graphics.deltaTime

        mainStage.act(dt)
        uiStage.act(dt)

        update(dt)

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        mainStage.draw()
        uiStage.draw()
    }

    abstract fun initialize()
    abstract fun update(dt: Float)
}