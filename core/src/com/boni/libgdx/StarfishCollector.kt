package com.boni.libgdx

import com.badlogic.gdx.scenes.scene2d.actions.Actions

class StarfishCollector : GameBeta() {

    private lateinit var turtle: Turtle
    private lateinit var ocean: BaseActor

    private var win: Boolean = false

    override fun initialize() {
        ocean = BaseActor(0f, 0f, mainStage).apply {
            loadTexture("water.jpg")
            setSize(800f, 600f)
        }

        BaseActor.setWorldBounds(ocean)

        Starfish(400f, 400f, mainStage)
        Starfish(500f, 100f, mainStage)
        Starfish(100f, 450f, mainStage)
        Starfish(200f, 250f, mainStage)
        Rock(200f, 150f, mainStage)
        Rock(100f, 300f, mainStage)
        Rock(300f, 350f, mainStage)
        Rock(450f, 200f, mainStage)

        turtle = Turtle(20f, 20f, mainStage)
    }

    override fun update(dt: Float) {

        BaseActor.getList<Rock>(mainStage).forEach { turtle.preventOverlap(it) }

        BaseActor.getList<Starfish>(mainStage).forEach {
            val starfish = it as Starfish

            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect()

                Whirlpool(0f, 0f, mainStage).apply {
                    centerAtActor(starfish)
                    setOpacity(0.25f)
                }
            }
        }

        if (BaseActor.count<Starfish>(mainStage) == 0) {
            win = true
            BaseActor(0f, 0f, mainStage).apply {
                loadTexture("you-win.png")
                centerAtPosition(400f, 300f)
                setOpacity(0f)
                addAction(Actions.delay(1f))
                addAction(Actions.after(Actions.fadeIn(1f)))
            }
        }
    }
}