package com.boni.libgdx

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle

class StarfishCollectorAlpha: Game() {

    private lateinit var batch: SpriteBatch

    private lateinit var turtleRectangle: Rectangle
    private lateinit var turtleTexture: Texture

    private lateinit var starfishTexture: Texture
    private lateinit var starfishRectangle: Rectangle

    private lateinit var oceanTexture: Texture
    private lateinit var winMessageTexture: Texture

    private var starfishX: Float = 380F
    private var starfishY: Float = 380F

    private var turtleX: Float = 20F
    private var turtleY: Float = 20F

    private var win: Boolean = false

    override fun create() {
        batch = SpriteBatch()

        turtleTexture = Texture(getTextureByFile("turtle-1.png"))
        starfishTexture = Texture(getTextureByFile("starfish.png"))
        oceanTexture = Texture(getTextureByFile("water.jpg"))
        winMessageTexture = Texture(getTextureByFile("you-win.png"))

        turtleRectangle = Rectangle(turtleX, turtleY, turtleTexture.width.toFloat(), turtleTexture.height.toFloat())
        starfishRectangle = Rectangle(starfishX, starfishY, starfishTexture.width.toFloat(), starfishTexture.height.toFloat())
    }

    override fun render() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) turtleX--
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) turtleX++
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) turtleY++
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) turtleY--

        turtleRectangle.setPosition(turtleX, turtleY)

        if (turtleRectangle.overlaps(starfishRectangle)) {
            win = true
        }

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        batch.draw(oceanTexture, 0f, 0f)

        if (!win) {
            batch.draw(starfishTexture, starfishX, starfishY)
        }

        batch.draw(turtleTexture, turtleX, turtleY)

        if (win) {
            batch.draw(winMessageTexture, 180f, 180f)
        }

        batch.end()
    }

    private fun getTextureByFile(path: String): FileHandle {
        return Gdx.files.internal(path)
    }
}