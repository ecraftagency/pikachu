package com.mygdx.game.objects

import com.badlogic.gdx.scenes.scene2d.Stage

class Board(private val stage : Stage, private val row: Int, private val col : Int, private val x : Int, private val y : Int) {
    private var dup = 8
    private var maxItems = 0
    private var s = Point(-1,-1)
    private var m = Point(-1,-1)

    private var animals = Array(row) { i ->
        Array<Animal?>(col){ j ->
            Animal((i*16 + j)%48,x + j*110,y + i*120, i, j, this)
        }
    }

    init {
        shuffle(true)
    }

    private fun shuffle(initial: Boolean) {
        var ids = ArrayList<Int>()
        if (initial) {
            val pairs = ArrayList<Int>()
            Utils.rcs(row*col, dup, pairs)
            for (i in 0 until pairs.size)
                this.maxItems += pairs[i]
            ids = Utils.shuffle(Utils.arr(pairs, dup))
        }
        else {
            this.animals.forEach { row ->
                row.forEach {
                    if (it != null)
                    ids.add(it.id)
                }
            }
            ids = Utils.shuffle(ids)
        }

        var index = 0
        animals.forEach { row ->
            row.forEach { it ->
                it?.setTexture(0)
                if (initial)
                    stage.addActor(it)
            }
        }
    }

    fun animalSelect(a : Animal){
        if (s.r == -1){
            a.state = Animal.HIGHLIGHT
            s.r = a.r ; s.c = a.c
        } else {
            if (s.r == a.r && s.c == a.c) {
                return
            } else {
                if (animals[s.r][s.c]?.isSameValue(a) == true){
                    m.r = a.r;m.c = a.c
                } else {
                    animals[s.r][s.c]?.state = Animal.ALIVE
                    a.state = Animal.HIGHLIGHT
                    s.r = a.r;s.c = a.c
                }
            }
        }
    }

    fun update() {
        if (s.r >= 0 && m.r >= 0){
            val pf = PathFinder(animals, animals.size + 2, animals[0].size + 2)
            if (pf.findPath(Point(s.r, s.c), Point(m.r, m.c))){
                stage.actors.removeValue(animals[m.r][m.c], true)
                stage.actors.removeValue(animals[s.r][s.c], true)
                animals[s.r][s.c] = null
                animals[m.r][m.c] = null
                m.r = -1;m.c = -1
                s.r = -1;s.c = -1
            }
            else {
                animals[s.r][s.c]?.state = Animal.ALIVE
                animals[m.r][m.c]?.state = Animal.HIGHLIGHT
                s.r = m.r;s.c = m.c
                m.r = -1;m.c = -1
            }
        }
    }
}