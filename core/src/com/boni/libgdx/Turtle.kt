package com.boni.libgdx

import com.badlogic.gdx.scenes.scene2d.Stage

class Turtle (x: Float, y: Float, stage: Stage): BaseActor(x, y, stage) {

    init {
        val filenames = Array<String>(6) {
            "turtle-1.png"
            "turtle-2.png"
            "turtle-3.png"
            "turtle-4.png"
            "turtle-5.png"
            "turtle-6.png"}

        loadAnimationFromFiles(filenames, 0.1f, true)
    }
}