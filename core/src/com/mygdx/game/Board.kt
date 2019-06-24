package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Board  {
    var col = 0
    var row = 0
    var x = 0f
    var y = 0f
    var anis: Array<Array<Animal>>

    constructor(row: Int, col: Int, x: Float, y: Float) {
        this.row = row
        this.col = col
        this.x = x
        this.y = y
        anis = Array(row) {Array(col){ Animal(0) }}
        create()
    }

    private fun create() {
        for (i in 0 until row){
            for (j in 0 until col){
                anis[i][j] = Animal(i*16 + j)
                anis[i][j].texture.flip(false, true)

            }
        }
    }

    private fun processInput(){
        if (Gdx.input.justTouched()){
            val x = Gdx.input.x
            val y = Gdx.input.y

            Gdx.app.log("height: ","$x   -     $y")

            val row = ((y - this.y)/120).toInt()
            val col = ((x - this.x)/110).toInt()

            if (row < 0 || row >= 3 || col < 0 || col > 15)
                return

            anis[row][col].state =Animal.DEAD
        }
    }

    fun update(batch: SpriteBatch) {
        processInput()
        anis.forEachIndexed {i,e ->
            e.forEachIndexed { j, animal ->
                if (animal.state == Animal.ALIVE) {
                    batch.draw(animal.Frame, x + j * 110f, y +i * 120f)
                    batch.draw(animal.texture, x +j * 110f + 10, y +i * 120f + 15)
                }
            }
        }
    }
}