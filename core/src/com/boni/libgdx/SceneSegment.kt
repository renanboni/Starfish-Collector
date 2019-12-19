package com.boni.libgdx

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor

class SceneSegment(private val actor: Actor, private val action: Action) {

    fun start() {
        actor.clearActions()
        actor.addAction(action)
    }

    fun finish() {
        if (actor.hasActions()) {
            actor.actions.first().act(100000f)
        }

        actor.clearActions()
    }

    fun isFinished() = actor.actions.isEmpty
}