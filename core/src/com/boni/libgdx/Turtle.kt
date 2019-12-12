package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage

class Turtle(x: Float, y: Float, stage: Stage) : BaseActor(x, y, stage) {

    init {
        val filenames = arrayOf("turtle-1.png",
                "turtle-2.png",
                "turtle-3.png",
                "turtle-4.png",
                "turtle-5.png",
                "turtle-6.png")

        setAcceleration(400f)
        setMaxSpeed(100f)
        setDeceleration(400f)

        loadAnimationFromFiles(filenames, 0.1f, true)
        setBoundaryPolygon(8)
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) accelerateAtAngle(180f)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) accelerateAtAngle(0f)
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) accelerateAtAngle(90f)
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) accelerateAtAngle(270f)

        applyPhysics(delta)

        setAnimationPaused(!isMoving())

        if (getSpeed() > 0) {
            rotation = getMotionAngle()
        }

        boundToWorld()
    }
}