package com.boni.libgdx

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor

open class ActorBeta() : Actor() {

    private var textureRegion: TextureRegion = TextureRegion()
    private var rectangle: Rectangle = Rectangle()

    fun setTexture(texture: Texture) {
        textureRegion.setRegion(texture)
        setSize(texture.width.toFloat(), texture.height.toFloat())
        rectangle.setSize(texture.width.toFloat(), texture.height.toFloat())
    }

    fun getRectangle(): Rectangle {
        rectangle.setPosition(x, y)
        return rectangle
    }

    fun overlaps(other: ActorBeta): Boolean {
        return this.getRectangle().overlaps(other.getRectangle())
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        batch.setColor(color.r, color.g, color.b, color.a)

        if (isVisible) {
            batch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        }
    }
}