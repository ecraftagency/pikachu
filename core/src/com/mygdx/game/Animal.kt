package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animal(id: Int) {
    companion object {
        lateinit var texture: Texture
        lateinit var ani: Array<Array<TextureRegion>>
        lateinit var  frame : Texture

        fun loadAsset(){
            texture = Texture(Gdx.files.internal("ani.png"))
            ani = TextureRegion(texture).split(90,90)
            frame = Texture(Gdx.files.internal("tile.png"))
        }

        fun releaseAsset(){
            texture.dispose()
            frame.dispose()
        }

        const val DEAD = -1
        const val ALIVE = 1
    }

    val id = id
    var state = ALIVE
    val texture = ani[id/16][id%16]
    val Frame  = frame
}