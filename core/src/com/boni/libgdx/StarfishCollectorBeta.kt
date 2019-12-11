package com.boni.libgdx

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage

class StarfishCollectorBeta : Game() {

    private lateinit var turtle: Turtle

    private lateinit var starfish: ActorBeta
    private lateinit var ocean: ActorBeta
    private lateinit var winMessage: ActorBeta

    private lateinit var mainStage: Stage

    private var win: Boolean = false

    override fun create() {
        mainStage = Stage()

        ocean = ActorBeta().apply { setTexture(Texture(getTextureByFile("water.jpg"))) }
        mainStage.addActor(ocean)

        starfish = ActorBeta().apply {
            setTexture(Texture(getTextureByFile("starfish.png")))
            setPosition(380f, 380f)
        }
        mainStage.addActor(starfish)

        turtle = Turtle().apply {
            setTexture(Texture(getTextureByFile("turtle-1.png")))
            setPosition(20f, 20f)
        }
        mainStage.addActor(turtle)

        winMessage = ActorBeta().apply {
            setTexture(Texture(getTextureByFile("you-win.png")))
            setPosition(180f, 180f)
            isVisible = false
        }
        mainStage.addActor(winMessage)
    }

    override fun render() {
        mainStage.act(1/60f)

        if (turtle.overlaps(starfish)) {
            starfish.remove()
            winMessage.isVisible = true
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        mainStage.draw()
    }

    private fun getTextureByFile(path: String): FileHandle {
        return Gdx.files.internal(path)
    }
}