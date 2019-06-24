package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.scenes.MainGame

public class AnotherGame : Game() {
    companion object {
        const val TILE_WIDTH = 110
        const val TILE_HEIGHT = 120
        var SW = 0
        var SH = 0
        var assetManager = AssetManager()
        lateinit var animal : Array<Array<TextureRegion>>

        fun loadAssets () {
            assetManager.load("ani.png", Texture::class.java)
            assetManager.load("tile.png", Texture::class.java)
        }

        inline operator fun <reified T> AssetManager.get(filename: String): T {
            return this.get (filename, T::class.java)
        }
    }

    override fun create() {
        loadAssets()
        while(!AnotherGame.assetManager.update());
        val ani : Texture = assetManager["ani.png"]
        animal = TextureRegion(ani).split(90,90)
        setScreen(MainGame(this))
        SW = Gdx.graphics.width
        SH = Gdx.graphics.height
    }
}