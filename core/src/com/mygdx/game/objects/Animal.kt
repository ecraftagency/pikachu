package com.mygdx.game.objects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.mygdx.game.AnotherGame as G

class Animal(var id : Int, private val x : Int, private val y : Int, val r : Int, val c : Int, val board: Board) : Actor() {
    companion object {
        const val HIGHLIGHT = -1
        const val ALIVE = 1
    }

    private var ani = G.animal[id/16][id%16]
    private var tile : Texture = G.assetManager["tile.png"]

    var state = ALIVE

    init {
        setBounds(x.toFloat(), y.toFloat(), G.TILE_WIDTH.toFloat(), G.TILE_HEIGHT.toFloat())
        addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                board.animalSelect(this@Animal)
                return super.touchDown(event, x, y, pointer, button)
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (state == HIGHLIGHT){
            color.a = 0.5f
        }
        else{
            color.a = 1f
        }
        batch?.color = color
        batch?.draw(tile, x.toFloat(), y.toFloat())
        batch?.draw(ani, x.toFloat() + 10, y.toFloat() + 15)
    }

    fun setTexture(id: Int) {
        this.id = id
        ani = G.animal[id/16][id%16]
    }

    fun isSameValue(a : Animal) : Boolean {
        return this.id == a.id
    }
}