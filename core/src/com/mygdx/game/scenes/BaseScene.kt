package com.mygdx.game.scenes

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport

open class BaseScene(open val game: Game) : Screen {
    private var camera = OrthographicCamera()
    val stage = Stage()

    init {
        camera.setToOrtho(false, 1920f, 1080f)
        stage.viewport = ScreenViewport(camera)
        Gdx.input.inputProcessor = stage
    }

    override fun show() {
    }

    override fun render(dt: Float) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
        stage.batch.projectionMatrix = camera.combined
    }

    override fun hide() {
        stage.dispose()
    }

    override fun dispose() {
    }
}