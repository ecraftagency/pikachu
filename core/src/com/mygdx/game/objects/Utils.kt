package com.mygdx.game.objects

data class Point (var r:Int, var c:Int){
    override fun toString(): String {
        return "r: $r - c: $c"
    }
}
data class Path (var sp: Point, var delta: Point){
    override fun toString(): String {
        return "sp[${sp.toString()}] - delta[${delta.toString()}]"
    }
}

class Utils {
    companion object {
        fun rcs(b: Int, c: Int, a: ArrayList<Int>) {
            if (b == 0 || c == 0)
                return
            a.add(Math.floor((b/c).toDouble()).toInt())
            this.rcs(b - c*a[a.size - 1], c/2, a)
        }

        fun arr(a: ArrayList<Int>, d: Int) : ArrayList<Int> {
            var _d = d
            val r = ArrayList<Int>()
            var v = 0
            for (i in 0 until a.size) {
                for (j in 0 until a[i]) {
                    for (k in 0 until _d)
                        r.add(v)
                    v++
                }
                _d /= 2
            }

            return r
        }

        fun shuffle(a: ArrayList<Int>) : ArrayList<Int> {
            for (i in a.size - 1 downTo 0) {
                var j = Math.floor(Math.random()*(i + 1)).toInt()
                var x = a[i]
                a[i] = a[j]
                a[j] = x
            }

            return a
        }
    }
}

class PathFinder(private val animals : Array<Array<Animal?>>, private val rows : Int, private val cols : Int) {
    var path = ArrayList<Path?>()
    private var arr = Array(rows) { r ->
        IntArray(cols){ c ->
            if (r >= 1 && r < this.rows - 1 && c >= 1 && c < cols - 1)
                if (animals[r-1][c-1] == null) 0
                else 1
            else 0
        }
    }

    fun findPath(p1: Point, p2 : Point) : Boolean {
        val tp1 = Point(p1.r + 1, p1.c + 1)
        val tp2 = Point(p2.r + 1, p2.c + 1)

        arr[tp1.r][tp1.c] = 0
        arr[tp2.r][tp2.c] = 0

        if (tp1.r == tp2.r)
            if (checkLineRow(tp1.c, tp2.c, tp1.r))
                return true
        if (tp1.c == tp2.c)
            if (checkLineCol(tp1.r, tp2.r, tp1.c))
                return true
        if (checkRectRow(tp1, tp2))
            return true
        if (checkRectCol(tp1,tp2))
            return true
        if (checkMoreLineRow(tp1, tp2, 1))
            return true
        if (checkMoreLineRow(tp1, tp2, -1))
            return true
        if (checkMoreLineCol(tp1, tp2, 1))
            return true
        if (checkMoreLineCol(tp1, tp2, -1))
            return true

        return false
    }

    private fun checkLineRow(c1:Int, c2:Int, r:Int) : Boolean {
        val cmin = if (c1 > c2) c2 else c1
        val cmax = if (c1 > c2) c1 else c2
        for (i in cmin..cmax)
            if (arr[r][i] == 1)
                return false
        path.add(Path(Point(r-1, cmin-1 ), Point(0, Math.abs(cmax-cmin))))
        return true
    }

    private fun checkLineCol(r1 : Int, r2 : Int, c : Int) : Boolean {
        val rmin = if (r1 > r2) r2 else r1
        val rmax = if (r1 > r2) r1 else r2
        for (i in rmin..rmax)
            if (arr[i][c] == 1)
                return false
        path.add(Path(Point(rmin-1,c-1), Point(Math.abs(rmax-rmin), 0)))
        return true
    }

    private fun checkRectRow(p1 : Point, p2: Point) : Boolean {
        var pmin = p1; var pmax = p2
        if (p1.r > p2.r) {pmin = p2;pmax = p1}
        for (r in pmin.r + 1 until pmax.r)
            if (checkLineCol(pmin.r, r, pmin.c) && checkLineRow(pmin.c, pmax.c, r) && checkLineCol(r, pmax.r, pmax.c))
                return true
            else
                path.clear()
        return false
    }

    private fun checkRectCol(p1 : Point, p2: Point) : Boolean {
        var pmin = p1; var pmax = p2
        if (p1.c > p2.c) {pmin = p2; pmax = p1}
        for (c in pmin.c + 1 until pmax.c)
            if (checkLineRow(pmin.c, c, pmin.r) && checkLineCol(pmin.r, pmax.r, c) && checkLineRow(c, pmax.c, pmax.r))
                return true
            else
                path.clear()
        return false
    }

    private fun checkMoreLineRow(p1: Point, p2: Point, direction: Int) : Boolean {
        var pmin = p1; var pmax = p2
        if (p1.c > p2.c) {pmin = p2; pmax = p1}

        var c = pmax.c; var r = pmin.r
        if (direction == -1) {c = pmin.c; r = pmax.r}

        if (checkLineRow(pmin.c, pmax.c, r))
            while (c < arr[0].size && arr[pmin.r][c] != 1 && arr[pmax.r][c] != 1){
                if (this.checkLineCol(pmin.r, pmax.r, c)) {
                    val d:Path ?= path.last()
                    if (d != null){
                        val pt1 = Path(Point(p1.r-1,p1.c-1), Point(0,direction*Math.abs(p1.c-1 - d.sp.c)))
                        val pt2 = Path(Point(p2.r - 1, p2.c-1), Point(0,direction*Math.abs(p2.c-1 - d.sp.c)))
                        path.add(pt1); path.add(pt2); path.add(d)
                        return true
                    }
                }
                c += direction
            }
        return false
    }

    private fun checkMoreLineCol(p1: Point, p2: Point, direction: Int) : Boolean {
        var pmin = p1; var pmax = p2
        if (p1.r > p2.r) {pmin = p2; pmax = p1}

        var r = pmax.r; var c = pmin.c
        if (direction == -1) { r = pmin.r; c = pmax.c }

        if (checkLineCol(pmin.r, pmax.r, c))
            while (r < arr.size && arr[r][pmin.c] != 1 && arr[r][pmax.c] != 1) {
                if (checkLineRow(pmin.c, pmax.c, r)) {
                    val d:Path ?= path.last()
                    if (d != null) {
                        val pt1 = Path(Point(p1.r-1,p1.c-1), Point(direction*Math.abs(p1.r-1 - d.sp.r), 0))
                        val pt2 = Path(Point(p2.r-1,p2.c-1), Point(direction*Math.abs(p2.r-1 - d.sp.r), 0))
                        path.add(pt1); path.add(pt2); path.add(d)
                    }
                    return true
                }
                r += direction
            }
        return false
    }
}