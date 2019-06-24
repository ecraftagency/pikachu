package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.math.Interpolation.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*

class Anima_ : Actor {
    companion object {
        lateinit var texture: Texture
        lateinit var anis: Array<Array<TextureRegion>>
        lateinit var  tile : TextureRegion

        fun loadAsset(){
            texture = Texture(Gdx.files.internal("ani.png"))
            anis = TextureRegion(texture).split(90,90)
            tile = TextureRegion(Texture(Gdx.files.internal("tile.png"))).split(110,120)[0][0]
        }

        fun releaseAsset(){
            texture.dispose()
        }
    }

    lateinit var ani : TextureRegion
    lateinit var tile : TextureRegion
    var id = 0

    constructor(id: Int, x: Float, y: Float){
        this.ani =  anis[id/16][id%16]
        this.ani.flip(false, true)
        this.id = id
        this.tile = Anima_.tile
        this.x = x
        this.y = y
        this.setBounds(x, y, 110f, 120f)

        this.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return super.touchDown(event, x, y, pointer, button)
            }
        })
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = color
        batch.draw(tile, x, y)
        batch.draw(ani, x + 10, y + 15)
    }

    fun move(x: Float, y: Float){
        addAction(sequence(moveTo(x,y, 0.5f, smooth), run(object : Runnable{
            override fun run() {
                this@Anima_.move(100f,100f)
            }
        })))
    }
}