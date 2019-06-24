package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.sun.org.apache.xpath.internal.operations.Bool

class TestGame : ApplicationAdapter() {
    companion object {
        var assetManager = AssetManager()

        fun loadAssets () {
            assetManager.load("bob.png", Texture::class.java)
        }

        inline operator fun <reified T> AssetManager.get(filename: String): T {
            return this.get (filename, T::class.java)
        }
    }

    private var camera = OrthographicCamera()
    private lateinit var stage : Stage

    override fun create() {
        loadAssets()
        while(!assetManager.update());

        camera.setToOrtho(false, Gdx.graphics.height.toFloat(), Gdx.graphics.width.toFloat())
        stage = Stage()
        stage.viewport = ScreenViewport(camera)
        Gdx.input.inputProcessor = stage

        val bob = Bob(100f,100f)
        val fred = Bob(200f,200f)
        stage.addActor(bob)
        stage.addActor(fred)
        stage.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                bob.moveTo(x,y)
                fred.moveTo(x+100, y+100)
                return super.touchDown(event, x, y, pointer, button)
            }
        })
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render() {
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        val dt = Gdx.graphics.deltaTime
        if ((stage.actors[0] as Bob).isOverlap(stage.actors[1] as Bob)){
            (stage.actors[0] as Bob).isCollide = true
            (stage.actors[1] as Bob).isCollide = true
        }
        stage.act(dt)
        stage.draw()
    }

    override fun dispose() {

    }
}

class Bob(x: Float, y: Float) : Actor() {
    companion object {
        const val SPEED = 1000f
        const val ACCEL = 3000f
        const val MOVING = true
        const val GRAVITY_Y = -12f
        const val GRAVITY_X = -23f
    }
    private var p = Vector2()
    private var v = Vector2()
    private var m = Vector2()
    private var t = Vector2()
    private var d = Vector2()
    private var g = Vector2(GRAVITY_X, GRAVITY_Y)
    private var a = Vector2()
    private var b = Rectangle(0f,0f,Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private var state = !MOVING
    private val texture: Texture = TestGame.assetManager["bob.png"]
    public var isCollide = false

    init {
        this.x = x
        this.y = y
        b = Rectangle(x,y,texture.width.toFloat(), texture.height.toFloat())
    }

    override fun act(dt : Float) {
        if (state == MOVING){
            if (x < 0 || x + texture.width > Gdx.graphics.width){
                v.scl(-1f,1f)
            }
            if (y < 0 || y + texture.height > Gdx.graphics.height){
                v.scl(1f,-1f)
            }

            if (isCollide){
                v.scl(-1f,-1f)
                isCollide = false
            }

            p.set(x,y)
            d.set(t).sub(p).nor()
            a.set(d).scl(ACCEL*dt)
            v.add(a).add(g) //v.add(a) //v.set(d).scl(SPEED)
            m.set(v).scl(dt)

            /*if (p.dst2(t) > m.len2()){
                p.add(m)
            }
            else {
                p.set(t)
                state = !MOVING
            }*/

            p.add(m)
            x = p.x
            y = p.y
            b.x = p.x
            b.y = p.y
        }
    }

    fun moveTo(x: Float, y: Float){
        t.set(x,y)
        v.set(0f, 0f)
        state = MOVING
    }

    fun isOverlap(bob: Bob) : Boolean {
        return b.overlaps(bob.b)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.draw(texture, x, y)
    }
}