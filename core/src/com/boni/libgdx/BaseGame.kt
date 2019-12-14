package com.boni.libgdx

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable

abstract class BaseGame : Game() {

    init {
        game = this
    }

    override fun create() {
        Gdx.input.inputProcessor = InputMultiplexer()
        setupFont()
        setupTextButton()
    }

    private fun setupTextButton() {
        textButtonStyle = TextButton.TextButtonStyle()
        val buttonTexture = Texture(Gdx.files.internal("button.png"))
        val buttonPatch = NinePatch(buttonTexture, 24, 24, 24, 24)

        textButtonStyle.up = NinePatchDrawable(buttonPatch)
        textButtonStyle.font = labelStyle.font
        textButtonStyle.fontColor = Color.GRAY
    }

    private fun setupFont() {
        labelStyle = Label.LabelStyle()

        val fontGenerator = FreeTypeFontGenerator(Gdx.files.internal("OpenSans.ttf"))
        val fontParameter = FreeTypeFontGenerator.FreeTypeFontParameter().also {
            it.size = 48
            it.color = Color.WHITE
            it.borderWidth = 2f
            it.borderColor = Color.BLACK
            it.borderStraight = true
            it.minFilter = Texture.TextureFilter.Linear
            it.magFilter = Texture.TextureFilter.Linear
        }

        labelStyle.font = fontGenerator.generateFont(fontParameter)
    }

    companion object {
        private lateinit var game: BaseGame

        lateinit var labelStyle: Label.LabelStyle
        lateinit var textButtonStyle: TextButton.TextButtonStyle

        fun setActiveScreen(s: BaseScreen) {
            game.setScreen(s)
        }
    }
}