package com.boni.libgdx

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class StoryScreen : BaseScreen() {

    lateinit var scene: Scene
    lateinit var continueKey: BaseActor

    override fun initialize() {
        val background = BaseActor(stage = mainStage).also {
            it.loadTexture("oceanside.png")
            it.setSize(800f, 600f)
            it.setOpacity(0f)
        }
        BaseActor.setWorldBounds(background)

        val turtle = BaseActor(stage = mainStage).also {
            it.loadTexture("turtle-big.png")
            it.setPosition(-it.width, 0f)
        }

        val dialogBox = DialogBox(0f, 0f, mainStage).also {
            it.setDialogSize(600f, 200f)
            it.setBackgroundColor(Color(0.6f, 0.6f, 0.8f, 1f))
            it.setFontScale(0.75f)
            it.isVisible = false
        }

        uiTable.add(dialogBox).expandX().expandY().bottom()

        continueKey = BaseActor(stage = mainStage).also {
            it.loadTexture("key-C.png")
            it.setSize(32f, 32f)
            it.isVisible = false
        }

        dialogBox.addActor(continueKey)
        continueKey.setPosition(dialogBox.width - continueKey.width, 0f)

        scene = Scene()
        mainStage.addActor(scene)

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(turtle, SceneActions.moveToScreenCenter(2f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        scene.addSegment(SceneSegment(dialogBox, SceneActions.setText("I want to be the very best . . . Starfish Collector!")))

        scene.addSegment(SceneSegment(continueKey,  Actions.show()))
        scene.addSegment(SceneSegment(background, SceneActions.pause()))
        scene.addSegment(SceneSegment(continueKey, Actions.hide()))

        scene.addSegment(SceneSegment(dialogBox, SceneActions.setText("I've got to collect them all!")))

        scene.addSegment(SceneSegment(continueKey,  Actions.show()))
        scene.addSegment(SceneSegment(background, SceneActions.pause()))
        scene.addSegment(SceneSegment(continueKey, Actions.hide()))

        scene.addSegment(SceneSegment(background, Actions.hide()))
        scene.addSegment(SceneSegment(turtle, SceneActions.moveToOutsideRight(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.fadeOut(1f)))

        scene.start()
    }

    override fun update(dt: Float) {
        if (scene.isSceneFinished()) {
            BaseGame.setActiveScreen(LevelScreen())
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.C && continueKey.isVisible) {
            scene.loadNextSegment()
        }
        return false
    }
}