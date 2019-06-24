package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import java.util.*


class Picachu : ApplicationAdapter() {
    //private lateinit var batch : SpriteBatch
    private var camera = OrthographicCamera()
    //private lateinit var a:Anima_
    private lateinit var stage : Stage
    //private lateinit var board : Board

    override fun create() {
        //batch = SpriteBatch()
        //camera.setToOrtho(true, (Gdx.app.graphics.width).toFloat(), (Gdx.app.graphics.height).toFloat())
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        //Animal.loadAsset()
        Anima_.loadAsset()
        //a = Anima_(47, 100f, 100f)
        //stage.addActor(a)
        for (i in 0 until 100) {
            val a = Anima_(Random().nextInt(16*3-1), 0f,0f)
            stage.addActor(a)
            a.move(0f,0f)
        }
        //board = Board(3,16,0f,0f)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
        //batch.projectionMatrix = camera.combined
    }

    override fun render() {
        val dt = Gdx.graphics.deltaTime
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(dt)
        stage.draw()

        //batch.begin()
        //board.update(batch)
        //batch.end()
    }

    override fun dispose() {
        //batch.dispose()
        Animal.releaseAsset()
        Anima_.releaseAsset()
        stage.dispose()
    }
}
