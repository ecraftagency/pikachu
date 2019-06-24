package com.mygdx.game.scenes

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.mygdx.game.objects.Board

class MainGame(override val game: Game) : BaseScene(game) {
    private var board = Board(stage, 8, 16, 0, 0)

    override fun render(dt: Float) {
        board.update()
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(dt)
        stage.draw()
    }
}