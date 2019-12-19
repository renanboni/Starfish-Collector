package com.boni.libgdx

import com.badlogic.gdx.scenes.scene2d.Actor

class Scene(
        private val segmentList: ArrayList<SceneSegment> = arrayListOf(),
        private var index: Int = -1
) : Actor() {

    fun start() {
        index = 0
        segmentList[index].start()
    }

    override fun act(act: Float) {
        if (isSegmentFinished() && !isLastSegment()) {
            loadNextSegment()
        }
    }

    fun loadNextSegment() {
        if (isLastSegment()) {
            return
        }

        segmentList[index].finish()
        index++
        segmentList[index].start()
    }

    fun isSceneFinished() = isLastSegment() && isSegmentFinished()

    fun isLastSegment() = index >= segmentList.count() - 1

    fun isSegmentFinished(): Boolean {
        return segmentList[index].isFinished()
    }

    fun addSegment(segment: SceneSegment) {
        segmentList.add(segment)
    }

    fun clearSegments() {
        segmentList.clear()
    }
}