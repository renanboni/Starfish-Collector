package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.sun.org.apache.xpath.internal.operations.Bool

/**
 * This class should be used in place of ActorBeta
 * It is considered as its improved version
 */
class BaseActor(x: Float, y: Float, stage: Stage) : Actor() {

    // Animations
    private var animation: Animation<TextureRegion>? = null
    private var elapsedTime: Float = 0F
    private var animationPaused: Boolean = false

    init {
        setPosition(x, y)
        stage.addActor(this)
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (!animationPaused) {
            elapsedTime += delta
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        color.let {
            batch.setColor(it.r, it.g, it.b, it.a)
        }

        animation?.let {
            if (isVisible) {
                batch.draw(
                        it.getKeyFrame(elapsedTime),
                        x,
                        y,
                        originX,
                        originY,
                        width,
                        height,
                        scaleX,
                        scaleY,
                        rotation
                )
            }
        }
    }

    fun setAnimation(animation: Animation<TextureRegion>) {
        this.animation = animation
        val textureRegion = animation.keyFrames[0]

        val width = textureRegion.regionWidth.toFloat()
        val height = textureRegion.regionHeight.toFloat()

        setSize(width, height)
        setOrigin(width * .5f, height * .5f)
    }

    fun setAnimationPaused(paused: Boolean) {
        animationPaused = paused
    }

    fun loadTexture(fileName: String): Animation<TextureRegion> {
        val fileNames = Array(1) { fileName }
        return loadAnimationFromFiles(fileNames, 1f, true)
    }

    fun isAnimationFinished(): Boolean {
        return animation?.isAnimationFinished(elapsedTime) ?: false
    }

    fun loadAnimationFromFiles(fileNames: Array<String>, frameDuration: Float, loop: Boolean): Animation<TextureRegion> {
        val textureArray = com.badlogic.gdx.utils.Array<TextureRegion>()

        fileNames.forEach {
            val texture = Texture(Gdx.files.internal(it)).apply {
                setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            }
            textureArray.add(TextureRegion(texture))
        }

        val anim = Animation<TextureRegion>(frameDuration, textureArray)

        if (loop) {
            anim.playMode = Animation.PlayMode.LOOP
        } else {
            anim.playMode = Animation.PlayMode.NORMAL
        }

        if (animation == null) {
            setAnimation(anim)
        }

        return anim
    }

    fun loadAnimationFromSheet(
            fileName: String,
            rows: Int,
            cols: Int,
            frameDuration: Float,
            loop: Boolean
    ): Animation<TextureRegion> {
        val texture = Texture(Gdx.files.internal(fileName), true).apply {
            setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }
        val frameWidth = texture.width / cols
        val frameHeight = texture.height / rows

        val temp = TextureRegion.split(texture, frameWidth, frameHeight)

        val textureArray = com.badlogic.gdx.utils.Array<TextureRegion>()

        for (r in 0..rows) {
            for (c in 0..cols) {
                textureArray.add(temp[r][c])
            }
        }

        val anim = Animation<TextureRegion>(frameDuration, textureArray)

        if (loop) {
            anim.playMode = Animation.PlayMode.LOOP
        } else {
            anim.playMode = Animation.PlayMode.NORMAL
        }

        if (animation == null) {
            setAnimation(anim)
        }

        return anim
    }
}