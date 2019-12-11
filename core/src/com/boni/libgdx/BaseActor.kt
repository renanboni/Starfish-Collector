package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage

/**
 * This class should be used in place of ActorBeta
 * It is considered as its improved version
 */
open class BaseActor(x: Float, y: Float, stage: Stage) : Actor() {

    // Animations
    private var animation: Animation<TextureRegion>? = null
    private var elapsedTime: Float = 0F
    private var animationPaused: Boolean = false

    // Velocity
    private var velocityVec = Vector2(0f, 0f)

    // Acceleration
    private var accelerationVec = Vector2(0f, 0f)
    private var acceleration = 0f
    private var maxSpeed = 1000f
    private var deceleration = 0f

    // For collision
    private lateinit var boundaryPolygon: Polygon

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

    fun centerAtPosition(x: Float, y: Float) {
        setPosition(x - width * .5f, y - height * .5f)
    }

    fun centerAtActor(other: BaseActor) {
        centerAtPosition(other.x + other.width * .5f, other.y + other.height * .5f)
    }

    fun setOpacity(opacity: Float) {
        color.a = opacity
    }

    fun overlaps(other: BaseActor): Boolean {
        val poly1 = getBoundaryPolygon()
        val poly2 = other.getBoundaryPolygon()

        if (!poly1.boundingRectangle.overlaps(poly2.boundingRectangle)) {
            return false
        }

        return Intersector.overlapConvexPolygons(poly1, poly2)
    }

    fun setBoundaryRectangle() {
        val w = width
        val h = height

        val vertices = floatArrayOf(0f, 0f, w, 0f, w, h, 0f, h)
        boundaryPolygon = Polygon(vertices)
    }

    fun setBoundaryPolygon(numSides: Int) {
        val vertices = floatArrayOf()

        val w = width
        val h = height

        for (i in 0..numSides) {
            val angle = i * 6.28f / numSides

            vertices[2 * i] = w / 2 * MathUtils.cos(angle) + w / 2
            vertices[2 * i + 1] = w / 2 * MathUtils.sin(angle) + h / 2
        }
        boundaryPolygon = Polygon(vertices)
    }

    fun getBoundaryPolygon(): Polygon {
        return boundaryPolygon.apply {
            setPosition(x, y)
            setOrigin(originX, originY)
            rotation = rotation
            setScale(scaleX, scaleY)
        }
    }

    fun setSpeed(speed: Float) {
        if (velocityVec.len() == 0f) {
            velocityVec.set(speed, 0f)
        } else {
            velocityVec.setLength(speed)
        }
    }

    fun getSpeed() = velocityVec.len()

    fun setMotionAngle(angle: Float) = velocityVec.setAngle(angle)

    fun getMotionAngle() = velocityVec.angle()

    fun isMoving() = getSpeed() > 0

    fun setAcceleration(ac: Float) {
        acceleration = ac
    }

    fun accelerateAtAngle(angle: Float) {
        accelerationVec.add(Vector2(acceleration, 0f).setAngle(angle))
    }

    fun accelerateForward() {
        accelerateAtAngle(rotation)
    }

    fun setAnimation(animation: Animation<TextureRegion>) {
        this.animation = animation
        val textureRegion = animation.getKeyFrame(0f)

        val width = textureRegion.regionWidth.toFloat()
        val height = textureRegion.regionHeight.toFloat()

        setSize(width, height)
        setOrigin(width * .5f, height * .5f)

        if (boundaryPolygon == null) {
            setBoundaryRectangle()
        }
    }

    fun setMaxSpeed(ms: Float) {
        maxSpeed = ms
    }

    fun setDeceleration(dec: Float) {
        deceleration = dec
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

    fun applyPhysics(dt: Float) {
        // apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt)

        var speed = getSpeed()

        // decrease speed (decelerate) when not accelerating
        if (accelerationVec.len() == 0f) {
            speed -= deceleration * dt
        }

        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0f, maxSpeed)

        // update velocity
        setSpeed(speed)

        // apply velocity
        moveBy(velocityVec.x * dt, velocityVec.y * dt)

        // reset acceleration
        accelerationVec.set(0f, 0f)
    }
}












