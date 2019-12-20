package com.boni.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class LevelScreen : BaseScreen() {

    private lateinit var turtle: Turtle
    private lateinit var ocean: BaseActor

    private lateinit var starfishLabel: Label

    private lateinit var restartButton: Button

    private lateinit var muteButton: Button

    private lateinit var dialogBox: DialogBox

    private var win: Boolean = false

    private var audioVolume: Float = 1f

    private lateinit var waterDrop: Sound
    private lateinit var instrumental: Music
    private lateinit var oceanSurf: Music

    override fun initialize() {
        addOcean()
        BaseActor.setWorldBounds(ocean)

        addStarFish()
        addRock()

        turtle = Turtle(20f, 20f, mainStage)

        starfishLabel = Label("Starfish left:", BaseGame.labelStyle).also {
            it.color = Color.CYAN
        }

        setupRestartButton()
        setMuteButton()

        uiTable.pad(10f)
        uiTable.add(starfishLabel).top()
        uiTable.add().expandX().expandY()
        uiTable.add(restartButton).top()

        uiTable.add(muteButton).top()

        addSign()
        addDialogBox()
        setSounds()
    }

    private fun addOcean() {
        ocean = BaseActor(0f, 0f, mainStage).apply {
            loadTexture("water-border.jpg")
            setSize(1200f, 900f)
        }
    }

    private fun addDialogBox() {
        dialogBox = DialogBox(0f, 0f, uiStage).also {
            it.setBackgroundColor(Color.CYAN)
            it.setFontColor(Color.BROWN)
            it.setDialogSize(600f, 100f)
            it.setFontScale(0.80f)
            it.alignCenter()
            it.isVisible = false
        }

        uiTable.row()
        uiTable.add(dialogBox).colspan(4)
    }

    private fun addSign() {
        Sign(20f, 400f, mainStage).apply {
            setText("West Starfish Bay")
        }

        Sign(600f, 300f, mainStage).apply {
            setText("East Starfish Bay")
        }
    }

    private fun setSounds() {
        waterDrop = Gdx.audio.newSound(Gdx.files.internal("Water_Drop.ogg"))
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("Master_of_the_Feast.ogg"))
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("Ocean_Waves.ogg"))

        instrumental.isLooping = true
        instrumental.volume = audioVolume
        instrumental.play()

        oceanSurf.isLooping = true
        oceanSurf.volume = audioVolume
        oceanSurf.play()

        waterDrop.play(audioVolume)
    }

    private fun addRock() {
        Rock(200f, 150f, mainStage)
        Rock(100f, 300f, mainStage)
        Rock(300f, 350f, mainStage)
        Rock(450f, 200f, mainStage)
    }

    private fun addStarFish() {
        Starfish(400f, 400f, mainStage)
        Starfish(500f, 100f, mainStage)
        Starfish(100f, 450f, mainStage)
        Starfish(200f, 250f, mainStage)
    }

    private fun setupRestartButton() {
        val buttonStyle = Button.ButtonStyle()

        val buttonTexture = Texture(Gdx.files.internal("undo.png"))
        val buttonRegion = TextureRegion(buttonTexture)

        buttonStyle.up = TextureRegionDrawable(buttonRegion)

        restartButton = Button(buttonStyle).also {
            it.color = Color.CYAN
        }

        restartButton.addListener {
            if (it !is InputEvent || it.type == InputEvent.Type.touchDown) {
                BaseGame.setActiveScreen(LevelScreen())
            }

            instrumental.dispose()
            oceanSurf.dispose()

            false
        }
    }

    private fun setMuteButton() {
        val buttonStyle2 = Button.ButtonStyle()

        val buttonTexture2 = Texture(Gdx.files.internal("audio.png"))
        val buttonRegion2 = TextureRegion(buttonTexture2)

        buttonStyle2.up = TextureRegionDrawable(buttonRegion2)

        muteButton = Button(buttonStyle2).also {
            it.color = Color.CYAN
        }

        muteButton.addListener {
            if (!isTouchEvent(it)) {
                return@addListener false
            }

            audioVolume = 1 - audioVolume
            instrumental.volume = audioVolume
            oceanSurf.volume = audioVolume

            true
        }
    }

    override fun update(dt: Float) {
        BaseActor.getList<Rock>(mainStage).forEach { turtle.preventOverlap(it) }

        BaseActor.getList<Starfish>(mainStage).forEach {
            val starfish = it as Starfish

            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect()
                waterDrop.play()

                Whirlpool(0f, 0f, mainStage).apply {
                    centerAtActor(starfish)
                    setOpacity(0.25f)
                }
            }
        }

        if (BaseActor.count<Starfish>(mainStage) == 0) {
            win = true
            BaseActor(0f, 0f, uiStage).apply {
                loadTexture("you-win.png")
                centerAtPosition(400f, 300f)
                setOpacity(0f)
                addAction(Actions.delay(1f))
                addAction(Actions.after(Actions.fadeIn(1f)))
            }
        }

        BaseActor.getList<Sign>(mainStage).forEach {
            val sign = it as Sign

            turtle.preventOverlap(sign)
            val nearby = turtle.isWithinDistance(4f, sign)

            if (nearby && !sign.isViewing()) {
                dialogBox.setText(sign.getText())
                dialogBox.isVisible = true
                sign.setViewing(true)
            }

            if (sign.isViewing() && !nearby) {
                dialogBox.setText("")
                dialogBox.isVisible = false
                sign.setViewing(false)
            }
        }

        starfishLabel.setText("Starfish Left: ${BaseActor.count<Starfish>(mainStage)}")
    }
}